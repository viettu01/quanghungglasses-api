package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    Page<CategoryResponse> findAll(Pageable pageable);

    Page<CategoryResponse> findByNameContainingAndStatus(String name, Boolean status, Pageable pageable);

    Page<CategoryResponse> findByNameContaining(String name, Pageable pageable);

    List<CategoryResponse> findByStatus(Boolean status);

    CategoryResponse findBySlug(String slug);

    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse update(CategoryRequest categoryRequest);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);

    Long countByStatus(Boolean status);
}