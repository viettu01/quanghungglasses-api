package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.BannerRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BannerResponse;
import fithou.tuplv.quanghungglassesapi.entity.Banner;
import fithou.tuplv.quanghungglassesapi.mapper.BannerMapper;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.repository.BannerRepository;
import fithou.tuplv.quanghungglassesapi.service.BannerService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.DIR_FILE_BANNER;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_BANNER_NOT_FOUND;

@Service
@Transactional
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {
    final BannerRepository bannerRepository;
    final PaginationMapper paginationMapper;
    final BannerMapper bannerMapper;
    final StorageService storageService;

    @Override
    public List<BannerResponse> findAll() {
        return bannerRepository.findAll()
                .stream()
                .map(bannerMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PaginationDTO<BannerResponse> findAll(Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(bannerRepository.findAll(pageable).map(bannerMapper::convertToResponse));
    }

    @Override
    public PaginationDTO<BannerResponse> findByNameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(bannerRepository.findByNameContaining(name, pageable).map(bannerMapper::convertToResponse));
    }

    @Override
    public BannerResponse create(BannerRequest bannerRequest) {
        Banner banner = bannerMapper.convertToEntity(bannerRequest);
        if (!bannerRequest.getMultipartFileImage().isEmpty())
            banner.setImage(storageService.saveImageFile(DIR_FILE_BANNER, bannerRequest.getMultipartFileImage()));

        return bannerMapper.convertToResponse(bannerRepository.save(banner));
    }

    @Override
    public BannerResponse update(BannerRequest bannerRequest) {
        Optional<Banner> bannerOptional = bannerRepository.findById(bannerRequest.getId());
        if (bannerOptional.isEmpty())
            throw new RuntimeException(ERROR_BANNER_NOT_FOUND);

        Banner banner = bannerMapper.convertToEntity(bannerRequest);

        if (bannerRequest.getMultipartFileImage().isEmpty()) {
            banner.setImage(bannerOptional.get().getImage());
        } else {
            storageService.deleteFile(bannerOptional.get().getImage());
            banner.setImage(storageService.saveImageFile(DIR_FILE_BANNER, bannerRequest.getMultipartFileImage()));
        }

        return bannerMapper.convertToResponse(bannerRepository.save(banner));
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            try {
                Optional<Banner> banner = bannerRepository.findById(id);
                if (banner.isPresent()) {
                    storageService.deleteFile(banner.get().getImage());
                    bannerRepository.deleteById(id);
                } else
                    throw new RuntimeException(ERROR_BANNER_NOT_FOUND);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
