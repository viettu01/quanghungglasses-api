package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductDetailsInvoiceResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    PaginationDTO<ProductResponse> findByNameContaining(String name, Pageable pageable);

    PaginationDTO<ProductResponse> findByNameContainingAndStatus(String name, Boolean status, Pageable pageable);

    PaginationDTO<ProductResponse> findByCategorySlug(String slug,
                                                      List<String> originNames,
                                                      List<String> brandNames,
                                                      List<String> materialNames,
                                                      List<String> shapeNames,
                                                      List<Integer> timeWarranties,
                                                      Double priceMin,
                                                      Double priceMax,
                                                      Pageable pageable);

    ProductDetailsInvoiceResponse findProductDetailsById(Long id);

    ProductDetailsResponse findProductDetailsByIdInCart(Long id);

    ProductResponse findBySlug(String slug);

    ProductResponse findById(Long id);

    ProductResponse create(ProductRequest productRequest);

    ProductResponse update(ProductRequest productRequest);

    ProductResponse updateStatus(Long id);

    Long countByStatus(Boolean status);

    Long countAll();

    void deleteById(Long id);

    void deleteProductDetailsById(Long id);

    void deleteImageById(Long id, String imageName);
}
