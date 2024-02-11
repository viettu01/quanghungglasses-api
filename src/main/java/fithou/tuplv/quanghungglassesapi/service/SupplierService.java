package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.SupplierRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.SupplierResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {
    List<SupplierResponse> findAll();

    PaginationDTO<SupplierResponse> findAll(Pageable pageable);

    PaginationDTO<SupplierResponse> findByNameContaining(String name, Pageable pageable);

    SupplierResponse findById(Long id);

    SupplierResponse create(SupplierRequest supplierRequest);

    SupplierResponse update(SupplierRequest supplierRequest);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);
}
