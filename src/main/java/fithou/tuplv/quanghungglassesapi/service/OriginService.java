package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.OriginRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OriginResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OriginService {
    List<OriginResponse> findAll();

    PaginationDTO<OriginResponse> findAll(Pageable pageable);

    PaginationDTO<OriginResponse> findByNameContaining(String name, Pageable pageable);

    OriginResponse findById(Long id);

    OriginResponse create(OriginRequest originRequest);

    OriginResponse update(OriginRequest originRequest);

    void deleteById(Long id);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);
}
