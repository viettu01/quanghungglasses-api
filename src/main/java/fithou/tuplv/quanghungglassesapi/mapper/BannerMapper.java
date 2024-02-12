package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.BannerRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BannerResponse;
import fithou.tuplv.quanghungglassesapi.entity.Banner;
import fithou.tuplv.quanghungglassesapi.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BannerMapper {
    final ModelMapper modelMapper;
    final StaffRepository staffRepository;

    public BannerResponse convertToResponse(Banner banner) {
        BannerResponse bannerResponse = modelMapper.map(banner, BannerResponse.class);
        bannerResponse.setUserId(banner.getStaff().getId());
        return bannerResponse;
    }

    public Banner convertToEntity(BannerRequest bannerRequest) {
        Banner banner = modelMapper.map(bannerRequest, Banner.class);
        banner.setStaff(staffRepository.findByAccountEmail(bannerRequest.getStaffEmail()).orElse(null));
        return banner;
    }
}
