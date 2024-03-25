package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.OrderRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderResponse;
import fithou.tuplv.quanghungglassesapi.entity.Order;
import fithou.tuplv.quanghungglassesapi.entity.OrderDetails;
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

import static fithou.tuplv.quanghungglassesapi.utils.Constants.DIR_FILE_ORDER;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_ORDER_NOT_FOUND;

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
    public PaginationDTO<OrderResponse> findByCustomerFullname(String fullname, Pageable pageable) {
        return paginationMapper
                .mapToPaginationDTO(orderRepository.findByCustomerFullnameContaining(fullname, pageable)
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
    public OrderResponse update(OrderRequest orderRequest) {
        Order order = orderRepository.findById(orderRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_ORDER_NOT_FOUND));

        return null;
    }
}
