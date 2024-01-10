package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.StaffRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.StaffResponse;
import fithou.tuplv.quanghungglassesapi.entity.Staff;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.StaffRepository;
import fithou.tuplv.quanghungglassesapi.service.StaffService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.DIR_FILE_STAFF;

@Service
@Transactional
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {
    final StaffRepository staffRepository;
    final UserMapper userMapper;
    final StorageService storageService;

    @Override
    public PaginationDTO<StaffResponse> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public StaffResponse create(StaffRequest staffRequest) {
        Staff staff = userMapper.convertToEntity(staffRequest);
        if (!staffRequest.getAvatarFile().isEmpty()) {
            String avatar = storageService.saveImageFile(DIR_FILE_STAFF, staffRequest.getAvatarFile());
            staff.setAvatar(avatar);
        }
        return userMapper.convertToResponse(staffRepository.save(staff));
    }

    @Override
    public StaffResponse update(StaffRequest staffRequest) {
        return null;
    }

    @Override
    public void deleteByIds(Long[] ids) {

    }
}
