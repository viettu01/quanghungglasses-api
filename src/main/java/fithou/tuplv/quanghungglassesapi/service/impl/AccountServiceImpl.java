package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AccountResponse;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    final AccountRepository accountRepository;
    final UserMapper userMapper;

    @Override
    public boolean existsByUsername(String username) {
        return accountRepository.existsByEmail(username);
    }

    @Override
    public AccountResponse create(AccountRequest accountRequest) {
        return userMapper.convertToResponse(accountRepository.save(userMapper.convertToEntity(accountRequest)));
    }

    @Override
    public AccountResponse update(AccountRequest accountRequest) {
        return null;
    }

    @Override
    public void delete(Long[] ids) {

    }
}
