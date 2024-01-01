package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.MaterialRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.MaterialResponse;
import fithou.tuplv.quanghungglassesapi.entity.Material;
import fithou.tuplv.quanghungglassesapi.mapper.MaterialMapper;
import fithou.tuplv.quanghungglassesapi.repository.MaterialRepository;
import fithou.tuplv.quanghungglassesapi.service.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_MATERIAL_ALREADY_EXISTS;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_MATERIAL_NOT_FOUND;

@Service
@Transactional
@AllArgsConstructor
public class MaterialServiceImpl implements MaterialService {
    final MaterialRepository materialRepository;
    final MaterialMapper materialMapper;

    @Override
    public List<MaterialResponse> findAll() {
        return materialRepository.findAll().stream().map(materialMapper::convertToResponse).collect(Collectors.toList());
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
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            try {
                materialRepository.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean existsByName(String name) {
        return materialRepository.existsByName(name);
    }
}
