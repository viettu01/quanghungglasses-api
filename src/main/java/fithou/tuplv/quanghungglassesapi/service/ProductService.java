package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    PaginationDTO<ProductResponse> findAll(Pageable pageable);

    ProductResponse findBySlug(String slug);

    PaginationDTO<ProductResponse> filter(Long categoryId, Long materialId, Long originId, Long shapeId, Long brandId, Double priceMin, Double priceMax, Pageable pageable);

    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(ProductRequest productRequest);
}
