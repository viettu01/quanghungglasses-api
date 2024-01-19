package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    PaginationDTO<CategoryResponse> findAll(Pageable pageable);

    PaginationDTO<CategoryResponse> findByNameContainingAndStatus(String name, Boolean status, Pageable pageable);

    PaginationDTO<CategoryResponse> findByNameContaining(String name, Pageable pageable);

    List<CategoryResponse> findByStatus(Boolean status);

    CategoryResponse findBySlug(String slug);

    CategoryResponse create(CategoryRequest categoryRequest);

    CategoryResponse update(CategoryRequest categoryRequest);

    void deleteByIds(Long[] ids);

    void deleteById(Long id);

    boolean existsByName(String name);

    Long countByStatus(Boolean status);

    Long countAll();
}