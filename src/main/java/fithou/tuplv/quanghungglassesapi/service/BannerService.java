package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.BannerRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BannerResponse;

import java.util.List;

public interface BannerService {
    List<BannerResponse> findAll();

    BannerResponse create(BannerRequest bannerRequest);

    BannerResponse update(BannerRequest bannerRequest);

    void deleteByIds(Long[] ids);
}