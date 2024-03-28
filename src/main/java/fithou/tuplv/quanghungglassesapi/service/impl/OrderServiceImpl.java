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
import fithou.tuplv.quanghungglassesapi.service.OrderService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Override
    public PaginationDTO<OrderResponse> findByOrderCustomerFullname(String fullname, Pageable pageable) {
        return paginationMapper
                .mapToPaginationDTO(orderRepository.findByFullnameContaining(fullname, pageable)
                        .map(orderMapper::convertToResponse));
    }

    @Override
    public PaginationDTO<OrderResponse> findByCustomerAccountEmail(Pageable pageable) {
        return paginationMapper
                .mapToPaginationDTO(orderRepository
                        .findByCustomerAccountEmail(SecurityContextHolder.getContext().getAuthentication().getName(), pageable)
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

        // Cap nhat lai so luong san pham
        order.getOrderDetails().forEach(orderDetails -> {
            orderDetails.getProductDetails().setQuantity(orderDetails.getProductDetails().getQuantity() - orderDetails.getQuantity());
            productDetailsRepository.save(orderDetails.getProductDetails());
        });
        return orderMapper.convertToResponse(order);
    }

    @Override
    public OrderResponse update(Long id, Integer orderStatus) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_ORDER_NOT_FOUND));
        if (Objects.nonNull(order.getCompletedDate()))
            throw new RuntimeException("Đơn hàng đã hoàn thành không thể cập nhật");
        if (order.getOrderStatus() == 4)
            throw new RuntimeException("Đơn hàng đã hủy không thể cập nhật");

        order.setOrderStatus(orderStatus);
        if (order.getOrderStatus() == 3) {
            Date now = new Date();
            order.setCompletedDate(now);
            order.setPaymentStatus(true);
            if (order.getPaymentDate() == null)
                order.setPaymentDate(now);
        }

        return orderMapper.convertToResponse(orderRepository.save(order));
    }
}
