package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.BrandRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BrandResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {
    List<BrandResponse> findAll();

    PaginationDTO<BrandResponse> findByNameContaining(String name, Pageable pageable);

    BrandResponse findById(Long id);

    BrandResponse create(BrandRequest brandRequest);

    BrandResponse update(BrandRequest brandRequest);

    void deleteById(Long id);

    boolean existsByName(String name);
}