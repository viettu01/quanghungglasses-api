package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.OriginRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OriginResponse;

import java.util.List;

public interface OriginService {
    List<OriginResponse> findAll();

    OriginResponse findById(Long id);

    OriginResponse create(OriginRequest originRequest);

    OriginResponse update(OriginRequest originRequest);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);
}
