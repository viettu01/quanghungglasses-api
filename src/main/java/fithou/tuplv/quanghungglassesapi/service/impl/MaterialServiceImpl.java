package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.MaterialRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.MaterialResponse;
import fithou.tuplv.quanghungglassesapi.entity.Material;
import fithou.tuplv.quanghungglassesapi.mapper.MaterialMapper;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.repository.MaterialRepository;
import fithou.tuplv.quanghungglassesapi.service.MaterialService;
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
public class MaterialServiceImpl implements MaterialService {
    final MaterialRepository materialRepository;
    final PaginationMapper paginationMapper;
    final MaterialMapper materialMapper;

    @Override
    public List<MaterialResponse> findAll() {
        return materialRepository.findAll().stream().map(materialMapper::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public PaginationDTO<MaterialResponse> findByNameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(materialRepository.findByNameContaining(name, pageable).map(materialMapper::convertToResponse));
    }

    @Override
    public MaterialResponse findById(Long id) {
        return materialMapper.convertToResponse(materialRepository.findById(id).orElse(null));
    }

    @Override
    public MaterialResponse create(MaterialRequest materialRequest) {
        if (materialRepository.existsByName(materialRequest.getName()))
            throw new RuntimeException(ERROR_MATERIAL_ALREADY_EXISTS);
        return materialMapper.convertToResponse(materialRepository.save(materialMapper.convertToEntity(materialRequest)));
    }

    @Override
    public MaterialResponse update(MaterialRequest materialRequest) {
        Material material = materialRepository.findById(materialRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_MATERIAL_NOT_FOUND));
        if (!material.getName().equalsIgnoreCase(materialRequest.getName()) && materialRepository.existsByName(materialRequest.getName()))
            throw new RuntimeException(ERROR_MATERIAL_ALREADY_EXISTS);
        return materialMapper.convertToResponse(materialRepository.save(materialMapper.convertToEntity(materialRequest)));
    }

    @Override
    public void deleteById(Long id) {
        Material material = materialRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_MATERIAL_NOT_FOUND));
        if (!material.getProducts().isEmpty())
            throw new RuntimeException(ERROR_MATERIAL_HAS_PRODUCTS);
        materialRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return materialRepository.existsByName(name);
    }
}
