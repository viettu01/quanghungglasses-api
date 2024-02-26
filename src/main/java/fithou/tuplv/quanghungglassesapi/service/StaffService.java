package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.StaffRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.StaffResponse;
import org.springframework.data.domain.Pageable;

public interface StaffService {
    PaginationDTO<StaffResponse> findAll(Pageable pageable);

    PaginationDTO<StaffResponse> findByFullnameContaining(String name, Pageable pageable);

    StaffResponse findById(Long id);

    StaffResponse create(StaffRequest staffRequest);

    StaffResponse update(StaffRequest staffRequest);

    void deleteById(Long id);
}