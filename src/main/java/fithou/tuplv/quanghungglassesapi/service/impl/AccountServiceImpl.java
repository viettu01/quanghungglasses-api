package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ForgotPasswordRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AccountResponse;
import fithou.tuplv.quanghungglassesapi.entity.Account;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.service.AccountService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    final AccountRepository accountRepository;
    final PasswordEncoder passwordEncoder;
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

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        Account account = accountRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new RuntimeException(ERROR_EMAIL_NOT_FOUND));

        if (!account.getStatus())
            throw new RuntimeException(ERROR_ACCOUNT_IS_LOCKED);

        if (!account.getVerificationCode().equals(forgotPasswordRequest.getVerificationCode()))
            throw new RuntimeException(ERROR_VERIFICATION_CODE_INVALID);

        account.setPassword(passwordEncoder.encode(forgotPasswordRequest.getNewPassword()));
        account.setVerificationCode(null);
        account.setVerificationCodeExpiredAt(null);
        accountRepository.save(account);
    }
}
