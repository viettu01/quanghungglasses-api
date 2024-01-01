package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.BannerRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BannerResponse;
import fithou.tuplv.quanghungglassesapi.entity.Banner;
import fithou.tuplv.quanghungglassesapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BannerMapper {
    final ModelMapper modelMapper;
    final UserRepository userRepository;

    public BannerResponse convertToResponse(Banner banner) {
        BannerResponse bannerResponse = modelMapper.map(banner, BannerResponse.class);
        bannerResponse.setUserId(banner.getUser().getId());
        return bannerResponse;
    }

    public Banner convertToEntity(BannerRequest bannerRequest) {
        Banner banner = modelMapper.map(bannerRequest, Banner.class);
        banner.setUser(userRepository.findById(bannerRequest.getUserId()).orElse(null));
        return banner;
    }
}
