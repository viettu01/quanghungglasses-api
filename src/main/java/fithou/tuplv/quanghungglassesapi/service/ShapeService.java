package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ShapeRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ShapeResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShapeService {
    List<ShapeResponse> findAll();

    PaginationDTO<ShapeResponse> findAll(Pageable pageable);

    PaginationDTO<ShapeResponse> findByNameContaining(String name, Pageable pageable);

    ShapeResponse findById(Long id);

    ShapeResponse create(ShapeRequest shapeRequest);

    ShapeResponse update(ShapeRequest shapeRequest);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);
}
