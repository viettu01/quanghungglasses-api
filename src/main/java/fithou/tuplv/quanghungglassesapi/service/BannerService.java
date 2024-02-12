package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.BannerRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BannerResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BannerService {
    List<BannerResponse> findAll();

    PaginationDTO<BannerResponse> findAll(Pageable pageable);

    PaginationDTO<BannerResponse> findByNameContaining(String name, Pageable pageable);

    BannerResponse create(BannerRequest bannerRequest);

    BannerResponse update(BannerRequest bannerRequest);

    void deleteById(Long id);

    void deleteByIds(Long[] ids);
}