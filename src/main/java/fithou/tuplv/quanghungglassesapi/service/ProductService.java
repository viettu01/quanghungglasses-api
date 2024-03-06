package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductResponse;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    PaginationDTO<ProductResponse> findAll(Pageable pageable);

    PaginationDTO<ProductResponse> findByNameContaining(String name, Pageable pageable);

    PaginationDTO<ProductResponse> findByNameContainingAndStatus(String name, Boolean status, Pageable pageable);

    PaginationDTO<ProductResponse> findByCategorySlug(String slug, Pageable pageable);

    ProductResponse findBySlug(String slug);

    ProductResponse findById(Long id);

    PaginationDTO<ProductResponse> filter(Long categoryId, Long materialId, Long originId, Long shapeId, Long brandId, Double priceMin, Double priceMax, Pageable pageable);

    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(ProductRequest productRequest);

    ProductResponse updateStatus(Long id);

    Long countByStatus(Boolean status);

    Long countAll();

    void deleteById(Long id);

    void deleteProductDetailsById(Long id);

    void deleteImageById(Long id, String imageName);
}
