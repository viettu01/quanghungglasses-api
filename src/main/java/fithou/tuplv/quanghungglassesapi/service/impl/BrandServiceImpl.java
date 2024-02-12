package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.BrandRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BrandResponse;
import fithou.tuplv.quanghungglassesapi.entity.Brand;
import fithou.tuplv.quanghungglassesapi.mapper.BrandMapper;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.repository.BrandRepository;
import fithou.tuplv.quanghungglassesapi.service.BrandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    final BrandRepository brandRepository;
    final PaginationMapper paginationMapper;
    final BrandMapper brandMapper;

    @Override
    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream().map(brandMapper::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public PaginationDTO<BrandResponse> findAll(Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(brandRepository.findAll(pageable).map(brandMapper::convertToResponse));
    }

    @Override
    public PaginationDTO<BrandResponse> findByNameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(brandRepository.findByNameContaining(name, pageable).map(brandMapper::convertToResponse));
    }

    @Override
    public BrandResponse findById(Long id) {
        return brandMapper.convertToResponse(brandRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_BRAND_NOT_FOUND)));
    }

    @Override
    public BrandResponse create(BrandRequest brandRequest) {
        if (brandRepository.existsByName(brandRequest.getName()))
            throw new RuntimeException(ERROR_BRAND_ALREADY_EXISTS);

        return brandMapper.convertToResponse(brandRepository.save(brandMapper.convertToEntity(brandRequest)));
    }

    @Override
    public BrandResponse update(BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(brandRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_BRAND_NOT_FOUND));
        if (!brand.getName().equalsIgnoreCase(brandRequest.getName()) && brandRepository.existsByName(brandRequest.getName()))
            throw new RuntimeException(ERROR_BRAND_ALREADY_EXISTS);

        return brandMapper.convertToResponse(brandRepository.save(brandMapper.convertToEntity(brandRequest)));
    }

    @Override
    public void deleteById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_BRAND_NOT_FOUND));
        if (!brand.getProducts().isEmpty())
            throw new RuntimeException(ERROR_BRAND_HAS_PRODUCTS);
        brandRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            try {
                brandRepository.deleteById(id);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public boolean existsByName(String name) {
        return brandRepository.existsByName(name);
    }
}
