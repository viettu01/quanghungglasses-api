package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.WarrantyRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.WarrantyResponse;
import org.springframework.data.domain.Pageable;

public interface WarrantyService {
    PaginationDTO<WarrantyResponse> findByCustomerFullname(String fullname, Pageable pageable);

    WarrantyResponse findById(Long id);

    WarrantyResponse create(WarrantyRequest warrantyRequest);

    void update(Long id, Boolean status);
}
