package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.MaterialRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.MaterialResponse;

import java.util.List;

public interface MaterialService {
    List<MaterialResponse> findAll();

    MaterialResponse findById(Long id);

    MaterialResponse create(MaterialRequest materialRequest);

    MaterialResponse update(MaterialRequest materialRequest);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);
}
