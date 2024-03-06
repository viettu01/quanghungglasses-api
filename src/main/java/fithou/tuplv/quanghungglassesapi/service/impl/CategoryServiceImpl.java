package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryProductResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryResponse;
import fithou.tuplv.quanghungglassesapi.entity.Category;
import fithou.tuplv.quanghungglassesapi.mapper.CategoryMapper;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.repository.CategoryRepository;
import fithou.tuplv.quanghungglassesapi.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    final CategoryRepository categoryRepository;
    final PaginationMapper paginationMapper;
    final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CategoryProductResponse> findAllCategoryAndProduct(Boolean categoryStatus) {
        return categoryRepository.findByStatus(categoryStatus).stream().map(categoryMapper::convertToCategoryProductResponse).collect(Collectors.toList());
    }

    @Override
    public PaginationDTO<CategoryResponse> findAll(Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(categoryRepository.findAll(pageable).map(categoryMapper::convertToResponse));
    }

    @Override
    public PaginationDTO<CategoryResponse> findByNameContainingAndStatus(String name, Boolean status, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(
                categoryRepository.findByNameContainingAndStatus(name, status, pageable).map(categoryMapper::convertToResponse)
        );
    }

    @Override
    public PaginationDTO<CategoryResponse> findByNameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(
                categoryRepository.findByNameContaining(name, pageable).map(categoryMapper::convertToResponse)
        );
    }

    @Override
    public List<CategoryResponse> findByStatus(Boolean status) {
        return categoryRepository.findByStatus(status)
                .stream()
                .map(categoryMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse findBySlug(String slug) {
        return categoryMapper.convertToResponse(categoryRepository.findBySlug(slug).orElseThrow(() -> new RuntimeException(ERROR_CATEGORY_NOT_FOUND)));
    }

    @Override
    public CategoryResponse create(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.getName()))
            throw new RuntimeException(ERROR_CATEGORY_NAME_ALREADY_EXISTS);
        if (categoryRepository.existsBySlug(categoryRequest.getSlug()))
            throw new RuntimeException(ERROR_SLUG_ALREADY_EXISTS);
        return categoryMapper.convertToResponse(categoryRepository.save(categoryMapper.convertToEntity(categoryRequest)));
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(categoryRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_CATEGORY_NOT_FOUND));
        if (!category.getName().equalsIgnoreCase(categoryRequest.getName()) && categoryRepository.existsByName(categoryRequest.getName()))
            throw new RuntimeException(ERROR_CATEGORY_NAME_ALREADY_EXISTS);
        if (!category.getSlug().equalsIgnoreCase(categoryRequest.getSlug()) && categoryRepository.existsBySlug(categoryRequest.getSlug()))
            throw new RuntimeException(ERROR_SLUG_ALREADY_EXISTS);
        return categoryMapper.convertToResponse(categoryRepository.save(categoryMapper.convertToEntity(categoryRequest)));
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            try {
                categoryRepository.deleteById(id);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_CATEGORY_NOT_FOUND));
        if (!category.getProducts().isEmpty())
            throw new RuntimeException(ERROR_CATEGORY_HAS_PRODUCTS);

        categoryRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Override
    public Long countByStatus(Boolean status) {
        return categoryRepository.countByStatus(status);
    }

    @Override
    public Long countAll() {
        return categoryRepository.count();
    }
}
