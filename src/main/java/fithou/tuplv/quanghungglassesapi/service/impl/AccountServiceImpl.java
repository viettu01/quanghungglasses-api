package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AccountResponse;
import fithou.tuplv.quanghungglassesapi.entity.Account;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.service.AccountService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.DIR_FILE_STAFF;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    final AccountRepository accountRepository;
    final StorageService storageService;
    final UserMapper userMapper;

    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.existsByEmail(username);
    }

    @Override
    public AccountResponse create(AccountRequest accountRequest) {
        Account account = userMapper.convertToEntity(accountRequest);
        if (accountRequest.getAvatarFile() != null && !accountRequest.getAvatarFile().isEmpty())
            account.setAvatar(storageService.saveImageFile(DIR_FILE_STAFF, accountRequest.getAvatarFile()));

        return userMapper.convertToResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse update(AccountRequest accountRequest) {
        return null;
    }

    @Override
    public void delete(Long[] ids) {

    }
}
