package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ShapeRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ShapeResponse;
import fithou.tuplv.quanghungglassesapi.entity.Shape;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.mapper.ShapeMapper;
import fithou.tuplv.quanghungglassesapi.repository.ShapeRepository;
import fithou.tuplv.quanghungglassesapi.service.ShapeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_SHAPE_ALREADY_EXISTS;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_SHAPE_NOT_FOUND;

@Service
@Transactional
@AllArgsConstructor
public class ShapeServiceImpl implements ShapeService {
    final ShapeRepository shapeRepository;
    final PaginationMapper paginationMapper;
    final ShapeMapper shapeMapper;

    @Override
    public List<ShapeResponse> findAll() {
        return shapeRepository.findAll().stream().map(shapeMapper::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public PaginationDTO<ShapeResponse> findAll(Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(shapeRepository.findAll(pageable).map(shapeMapper::convertToResponse));
    }

    @Override
    public PaginationDTO<ShapeResponse> findByNameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(shapeRepository.findByNameContaining(name, pageable).map(shapeMapper::convertToResponse));
    }

    @Override
    public ShapeResponse findById(Long id) {
        return shapeMapper.convertToResponse(shapeRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_SHAPE_NOT_FOUND)));
    }

    @Override
    public ShapeResponse create(ShapeRequest shapeRequest) {
        if (shapeRepository.existsByName(shapeRequest.getName()))
            throw new RuntimeException(ERROR_SHAPE_ALREADY_EXISTS);
        return shapeMapper.convertToResponse(shapeRepository.save(shapeMapper.convertToEntity(shapeRequest)));
    }

    @Override
    public ShapeResponse update(ShapeRequest shapeRequest) {
        Shape shape = shapeRepository.findById(shapeRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_SHAPE_NOT_FOUND));
        if (!shape.getName().equalsIgnoreCase(shapeRequest.getName()) && shapeRepository.existsByName(shapeRequest.getName()))
            throw new RuntimeException(ERROR_SHAPE_ALREADY_EXISTS);
        return shapeMapper.convertToResponse(shapeRepository.save(shapeMapper.convertToEntity(shapeRequest)));
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            try {
                shapeRepository.deleteById(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean existsByName(String name) {
        return shapeRepository.existsByName(name);
    }
}
