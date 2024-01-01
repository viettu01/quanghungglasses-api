package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.ShapeRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ShapeResponse;

import java.util.List;

public interface ShapeService {
    List<ShapeResponse> findAll();

    ShapeResponse findById(Long id);

    ShapeResponse create(ShapeRequest shapeRequest);

    ShapeResponse update(ShapeRequest shapeRequest);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);
}
