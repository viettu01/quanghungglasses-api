package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.OrderRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderResponse;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    PaginationDTO<OrderResponse> findByOrderCustomerFullname(String fullname, Pageable pageable);

    PaginationDTO<OrderResponse> findByCustomerAccountEmail(Long id, Pageable pageable);

    OrderResponse findById(Long id);

    OrderResponse create(OrderRequest orderRequest);

    OrderResponse update(Long id, Integer orderStatus, String cancelReason);
}
