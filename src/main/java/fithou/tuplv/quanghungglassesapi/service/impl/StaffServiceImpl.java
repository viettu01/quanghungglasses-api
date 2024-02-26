package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.StaffRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.StaffResponse;
import fithou.tuplv.quanghungglassesapi.entity.Account;
import fithou.tuplv.quanghungglassesapi.entity.Staff;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.repository.StaffRepository;
import fithou.tuplv.quanghungglassesapi.service.StaffService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Service
@Transactional
@AllArgsConstructor
public class StaffServiceImpl implements StaffService {
    final StaffRepository staffRepository;
    final AccountRepository accountRepository;
    final PaginationMapper paginationMapper;
    final UserMapper userMapper;
    final StorageService storageService;

    @Override
    public PaginationDTO<StaffResponse> findAll(Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(staffRepository.findAll(pageable).map(userMapper::convertToResponse));
    }

    @Override
    public PaginationDTO<StaffResponse> findByFullnameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(
                staffRepository.findByFullnameContaining(name, pageable).map(userMapper::convertToResponse)
        );
    }

    @Override
    public StaffResponse findById(Long id) {
        return userMapper.convertToResponse(staffRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(ERROR_USER_NOT_FOUND))
        );
    }

    @Override
    public StaffResponse create(StaffRequest staffRequest) {
        if (staffRepository.existsByPhone(staffRequest.getPhone()))
            throw new RuntimeException(ERROR_PHONE_ALREADY_EXISTS);
        Staff staff = userMapper.convertToEntity(staffRequest);
        if (staffRequest.getAccount() != null) {
            if (accountRepository.existsByEmail(staffRequest.getAccount().getEmail()))
                throw new RuntimeException(ERROR_EMAIL_ALREADY_EXISTS);
            Account account = userMapper.convertToEntity(staffRequest.getAccount());
            account.setIsVerifiedEmail(true);
            if (staffRequest.getAccount().getAvatarFile() != null && !staffRequest.getAccount().getAvatarFile().isEmpty())
                account.setAvatar(storageService.saveImageFile(DIR_FILE_STAFF, staffRequest.getAccount().getAvatarFile()));
            try {
                accountRepository.save(account);
            } catch (Exception e) {
                if (account.getAvatar() != null)
                    storageService.deleteFile(account.getAvatar());
            }
            staff.setAccount(account);
        }
        try {
            staffRepository.save(staff);
        } catch (Exception e) {
            if (staff.getAccount() != null && staff.getAccount().getAvatar() != null)
                storageService.deleteFile(staff.getAccount().getAvatar());
        }

        return userMapper.convertToResponse(staff);
    }

    @Override
    public StaffResponse update(StaffRequest staffRequest) {
        Staff staffExists = staffRepository.findById(staffRequest.getId())
                .orElseThrow(() -> new RuntimeException(ERROR_USER_NOT_FOUND));

        if (!staffExists.getPhone().equals(staffRequest.getPhone())
                && staffRepository.existsByPhone(staffRequest.getPhone()))
            throw new RuntimeException(ERROR_PHONE_ALREADY_EXISTS);

        Staff staff = userMapper.convertToEntity(staffRequest);
        if (staffRequest.getAccount() != null) {
            if (staff.getAccount() == null) {
                if (accountRepository.existsByEmail(staffRequest.getAccount().getEmail()))
                    throw new RuntimeException(ERROR_EMAIL_ALREADY_EXISTS);
            } else if (!staff.getAccount().getEmail().equals(staffRequest.getAccount().getEmail())
                    && accountRepository.existsByEmail(staffRequest.getAccount().getEmail()))
                throw new RuntimeException(ERROR_EMAIL_ALREADY_EXISTS);
            Account account = userMapper.convertToEntity(staffRequest.getAccount());
            account.setIsVerifiedEmail(true);
            String oldFileName = null;
            if (staffRequest.getAccount().getAvatarFile().isEmpty()) {
                account.setAvatar(staffExists.getAccount().getAvatar());
            } else {
                if (staffExists.getAccount().getAvatar() != null)
                    oldFileName = staffExists.getAccount().getAvatar();
                account.setAvatar(storageService.saveImageFile(DIR_FILE_CUSTOMER, staffRequest.getAccount().getAvatarFile()));
            }

            if (StringUtils.isEmpty(staffRequest.getAccount().getPassword()))
                account.setPassword(staffExists.getAccount().getPassword());
            try {
                accountRepository.save(account);
            } catch (Exception e) {
                if (account.getAvatar() != null)
                    storageService.deleteFile(account.getAvatar());
            }
            if (oldFileName != null)
                storageService.deleteFile(oldFileName);
            staff.setAccount(account);
        }

        try {
            staffRepository.save(staff);
        } catch (Exception e) {
            if (staff.getAccount() != null && staff.getAccount().getAvatar() != null)
                storageService.deleteFile(staff.getAccount().getAvatar());
        }

        return userMapper.convertToResponse(staff);
    }

    @Override
    public void deleteById(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_USER_NOT_FOUND));
        if (!staff.getReceipts().isEmpty())
            throw new RuntimeException(ERROR_STAFF_HAS_RECEIPTS);
        if (!staff.getOrders().isEmpty())
            throw new RuntimeException(ERROR_STAFF_HAS_ORDERS);
        if (!staff.getWarranty().isEmpty())
            throw new RuntimeException(ERROR_STAFF_HAS_WARRANTY);

        if (staff.getAccount() != null && staff.getAccount().getAvatar() != null) {
            storageService.deleteFile(staff.getAccount().getAvatar());
        }
        staffRepository.deleteById(id);
    }
}
