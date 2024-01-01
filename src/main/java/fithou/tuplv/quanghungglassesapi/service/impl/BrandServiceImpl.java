package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.BrandRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BrandResponse;
import fithou.tuplv.quanghungglassesapi.entity.Brand;
import fithou.tuplv.quanghungglassesapi.mapper.BrandMapper;
import fithou.tuplv.quanghungglassesapi.repository.BrandRepository;
import fithou.tuplv.quanghungglassesapi.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_BRAND_ALREADY_EXISTS;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_BRAND_NOT_FOUND;

@Service
@Transactional
@AllArgsConstructor
public class BrandServiceImpl implements BrandService {
    final BrandRepository brandRepository;
    final BrandMapper brandMapper;

    @Override
    public List<BrandResponse> findAll() {
        return brandRepository.findAll().stream().map(brandMapper::convertToResponse).collect(Collectors.toList());
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
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            try {
                brandRepository.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean existsByName(String name) {
        return brandRepository.existsByName(name);
    }
}
