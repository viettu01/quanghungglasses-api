package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryProductResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAll();

    List<CategoryProductResponse> findAllCategoryAndProduct(Boolean categoryStatus);

    PaginationDTO<CategoryResponse> findByNameContaining(String name, Pageable pageable);

    CategoryResponse findBySlug(String slug);

    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse update(CategoryRequest categoryRequest);

    void deleteById(Long id);

    boolean existsByName(String name);
}