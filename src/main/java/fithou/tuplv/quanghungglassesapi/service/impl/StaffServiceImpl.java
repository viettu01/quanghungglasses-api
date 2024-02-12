package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.StaffRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.StaffResponse;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.StaffRepository;
import fithou.tuplv.quanghungglassesapi.service.StaffService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        return userMapper.convertToResponse(staffRepository.save(userMapper.convertToEntity(staffRequest)));
    }

    @Override
    public StaffResponse update(StaffRequest staffRequest) {
        return null;
    }

    @Override
    public void deleteByIds(Long[] ids) {

    }
}
