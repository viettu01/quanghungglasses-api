package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.OrderRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderResponse;
import fithou.tuplv.quanghungglassesapi.entity.Order;
import fithou.tuplv.quanghungglassesapi.entity.OrderDetails;
import fithou.tuplv.quanghungglassesapi.entity.ProductDetails;
import fithou.tuplv.quanghungglassesapi.mapper.OrderMapper;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.repository.OrderDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.OrderRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.service.EmailService;
import fithou.tuplv.quanghungglassesapi.service.OrderService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Service
@Transactional
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    final OrderRepository orderRepository;
    final OrderDetailsRepository orderDetailsRepository;
    final ProductDetailsRepository productDetailsRepository;
    final OrderMapper orderMapper;
    final PaginationMapper paginationMapper;
    final StorageService storageService;
    final VNPayService vnPayService;
    final EmailService emailService;

    @Override
    public PaginationDTO<OrderResponse> findByOrderCustomerFullname(String fullname, Pageable pageable) {
        return paginationMapper
                .mapToPaginationDTO(orderRepository.findByFullnameContaining(fullname, pageable)
                        .map(orderMapper::convertToResponse));
    }

    @Override
    public PaginationDTO<OrderResponse> findByCustomerAccountEmailAndOrderStatus(String productName, Integer orderStatus, Pageable pageable) {
        if (ObjectUtils.isNotEmpty(orderStatus)) {
            if (orderStatus >= 3 && orderStatus <= 5) {
                return paginationMapper
                        .mapToPaginationDTO(orderRepository
                                .findDistinctByCustomerAccountEmailAndOrderStatusGreaterThanEqualAndOrderStatusIsLessThanEqualAndOrderDetails_ProductDetails_ProductNameContaining(
                                        SecurityContextHolder.getContext().getAuthentication().getName(), orderStatus, 5, productName, pageable)
                                .map(orderMapper::convertToResponse)
                        );
            }
            return paginationMapper
                    .mapToPaginationDTO(orderRepository
                            .findDistinctByCustomerAccountEmailAndOrderStatusAndOrderDetails_ProductDetails_ProductNameContaining(
                                    SecurityContextHolder.getContext().getAuthentication().getName(), orderStatus, productName, pageable)
                            .map(orderMapper::convertToResponse)
                    );
        }
        return paginationMapper
                .mapToPaginationDTO(orderRepository
                        .findDistinctByCustomerAccountEmailAndOrderDetails_ProductDetails_ProductNameContaining(
                                SecurityContextHolder.getContext().getAuthentication().getName(), productName, pageable)
                        .map(orderMapper::convertToResponse)
                );
    }

    @Override
    public OrderResponse findById(Long id) {
        // kiem tra xem nguoi dung co quyen xem order nay khong
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_ORDER_NOT_FOUND));
        for (GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")
                    || authority.getAuthority().equals("ROLE_STAFF")
                    || order.getCustomer().getAccount().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName()))
                return orderMapper.convertToResponse(order);
            else
                throw new RuntimeException(ERROR_ORDER_NOT_FOUND);
        }
        return null;
    }

    @Override
    public OrderResponse create(OrderRequest orderRequest) {
        orderRequest.getOrderDetails().forEach(orderDetailsRequest -> {
            ProductDetails productDetails = productDetailsRepository
                    .findById(orderDetailsRequest.getProductDetailsId())
                    .orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND));
            if (productDetails.getQuantity() < orderDetailsRequest.getQuantity())
                throw new RuntimeException(ERROR_ORDER_CUSTOMER_PRODUCT_QUANTITY_NOT_ENOUGH);
        });
        Order order = orderMapper.convertToEntity(orderRequest);
        if (order.getPaymentMethod() == 0
                && SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities()
                .stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(ROLE_ADMIN) || grantedAuthority.getAuthority().equals(ROLE_STAFF))) {
            order.setOrderStatus(5);
            Date now = new Date();
            order.setCompletedDate(now);
            if (!order.getPaymentStatus()) order.setPaymentStatus(true);
            if (order.getPaymentDate() == null) order.setPaymentDate(now);
            if (order.getConfirmDate() == null) order.setConfirmDate(now);
            if (order.getDeliveryToShipperDate() == null) order.setDeliveryToShipperDate(now);
            if (order.getDeliveryDate() == null) order.setDeliveryDate(now);
            if (order.getReceiveDate() == null) order.setReceiveDate(now);
        }
        if (orderRequest.getEyeglassPrescriptionImage() != null && !orderRequest.getEyeglassPrescriptionImage().isEmpty())
            order.setEyeglassPrescription(storageService.saveImageFile(DIR_FILE_ORDER, orderRequest.getEyeglassPrescriptionImage()));
        orderRepository.save(order);
        order.getOrderDetails().clear();
        orderRequest.getOrderDetails().forEach(orderDetailsRequest -> {
            OrderDetails orderDetails = orderMapper.convertToEntity(orderDetailsRequest);
            orderDetails.setOrder(order);
            orderDetailsRepository.save(orderDetails);
            order.getOrderDetails().add(orderDetails);
        });

        // Gui email xac nhan don hang
        try {
            if (order.getPaymentMethod() == 0) // thanh toan khi nhan hang
                emailService.sendEmailOrderSuccess(orderMapper.convertToResponse(order));
        } catch (Exception e) {
            throw new RuntimeException("Không thể gửi email xác nhận đơn hàng");
        }

        // Cap nhat lai so luong san pham
        order.getOrderDetails().forEach(orderDetails -> {
            orderDetails.getProductDetails().setQuantity(orderDetails.getProductDetails().getQuantity() - orderDetails.getQuantity());
            productDetailsRepository.save(orderDetails.getProductDetails());
        });
        return orderMapper.convertToResponse(order);
    }

    @Override
    public OrderResponse update(Long id, Integer orderStatus, String cancelReason) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_ORDER_NOT_FOUND));
        if (order.getOrderStatus() > 0 && orderStatus == 6)
            throw new RuntimeException("Đơn hàng đã được xác nhận không thể cập nhật");
        if (Objects.nonNull(order.getCompletedDate()))
            throw new RuntimeException("Đơn hàng đã hoàn thành không thể cập nhật");
        if (order.getOrderStatus() == 6)
            throw new RuntimeException("Đơn hàng đã hủy không thể cập nhật");

        order.setOrderStatus(orderStatus);
        Date now = new Date();

        if (order.getOrderStatus() == 1) {
            // don hang da duoc xac nhan
            order.setConfirmDate(now);
        }

        if (order.getOrderStatus() == 2) {
            // don hang da duoc giao cho shipper
            order.setDeliveryToShipperDate(now); // ngay giao cho shipper
            if (order.getConfirmDate() == null) order.setConfirmDate(now);
        }

        if (order.getOrderStatus() == 3) {
            // don hang da giao cho khach hang (xac nhan giao hang)
            order.setDeliveryDate(now); // ngay giao hang
            if (order.getConfirmDate() == null) order.setConfirmDate(now);
            if (order.getDeliveryToShipperDate() == null) order.setDeliveryToShipperDate(now);
        }

        if (order.getOrderStatus() == 4) {
            order.setReceiveDate(now);
            if (!order.getPaymentStatus()) order.setPaymentStatus(true);
            if (order.getPaymentDate() == null) order.setPaymentDate(now);
            if (order.getConfirmDate() == null) order.setConfirmDate(now);
            if (order.getDeliveryToShipperDate() == null) order.setDeliveryToShipperDate(now);
            if (order.getDeliveryDate() == null) order.setDeliveryDate(now);
        }

        if (order.getOrderStatus() == 5) {
            order.setCompletedDate(now);
            if (!order.getPaymentStatus()) order.setPaymentStatus(true);
            if (order.getPaymentDate() == null) order.setPaymentDate(now);
            if (order.getConfirmDate() == null) order.setConfirmDate(now);
            if (order.getDeliveryToShipperDate() == null) order.setDeliveryToShipperDate(now);
            if (order.getDeliveryDate() == null) order.setDeliveryDate(now);
            if (order.getReceiveDate() == null) order.setReceiveDate(now);
        }

        if (order.getOrderStatus() == 6) {
            order.setCancelDate(now);
            order.setCancelReason(cancelReason);
            if (order.getPaymentStatus()) {
                order.setPaymentStatus(false);
                order.setPaymentDate(null);
            }
            order.setCompletedDate(null);
            order.setReceiveDate(null);
            order.setDeliveryDate(null);
            order.setDeliveryToShipperDate(null);
            order.setConfirmDate(null);

            // Cap nhat lai so luong san pham
            order.getOrderDetails().forEach(orderDetails -> {
                orderDetails.getProductDetails().setQuantity(orderDetails.getProductDetails().getQuantity() + orderDetails.getQuantity());
                productDetailsRepository.save(orderDetails.getProductDetails());
            });
        }

        return orderMapper.convertToResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updatePaymentStatus(Long id, Boolean paymentStatus, Date paymentTime) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_ORDER_NOT_FOUND));
        order.setPaymentStatus(paymentStatus);
        if (paymentStatus) {
            order.setPaymentDate(paymentTime);
        }
        return orderMapper.convertToResponse(orderRepository.save(order));
    }
}
