package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.BrandRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {
    List<BrandResponse> findAll();

    BrandResponse findById(Long id);

    BrandResponse create(BrandRequest brandRequest);

    BrandResponse update(BrandRequest brandRequest);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);
}