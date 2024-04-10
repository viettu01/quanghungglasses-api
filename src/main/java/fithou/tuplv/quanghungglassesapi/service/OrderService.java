package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.OrderRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface OrderService {
    PaginationDTO<OrderResponse> findByOrderCustomerFullname(String fullname, Pageable pageable);

    PaginationDTO<OrderResponse> findByCustomerAccountEmail(String productName, Pageable pageable);

    OrderResponse findById(Long id);

    OrderResponse create(OrderRequest orderRequest);

    OrderResponse update(Long id, Integer orderStatus, String cancelReason);

    OrderResponse updatePaymentStatus(Long id, Boolean paymentStatus, Date paymentTime);
}
