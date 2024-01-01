package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.SupplierRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.SupplierResponse;

import java.util.List;

public interface SupplierService {
    List<SupplierResponse> findAll();

    SupplierResponse findById(Long id);

    SupplierResponse create(SupplierRequest supplierRequest);

    SupplierResponse update(SupplierRequest supplierRequest);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);
}
