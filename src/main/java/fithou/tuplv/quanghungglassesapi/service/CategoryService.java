package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryResponse> findAll(Pageable pageable);

    Page<CategoryResponse> findByNameContaining(String name, Pageable pageable);

    CategoryResponse findBySlug(String slug);

    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse update(CategoryRequest categoryRequest);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);

    Long countByStatus(Boolean status);
}