package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.MaterialRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.MaterialResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MaterialService {
    List<MaterialResponse> findAll();

    PaginationDTO<MaterialResponse> findAll(Pageable pageable);

    PaginationDTO<MaterialResponse> findByNameContaining(String name, Pageable pageable);

    MaterialResponse findById(Long id);

    MaterialResponse create(MaterialRequest materialRequest);

    MaterialResponse update(MaterialRequest materialRequest);

    void deleteById(Long id);

    void deleteByIds(Long[] ids);

    boolean existsByName(String name);
}
