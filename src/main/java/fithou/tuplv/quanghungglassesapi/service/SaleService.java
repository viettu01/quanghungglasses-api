package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.SaleRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.SaleResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SaleService {
    List<SaleResponse> findAll();

    PaginationDTO<SaleResponse> findAll(Pageable pageable);

    PaginationDTO<SaleResponse> findByNameContaining(String name, Pageable pageable);

    SaleResponse findById(Long id);

    SaleResponse create(SaleRequest saleRequest);

    SaleResponse update(SaleRequest saleRequest);

    void deleteById(Long id);
}
