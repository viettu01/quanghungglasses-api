package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ChangePasswordRequest;
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

import java.util.Date;

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
    public AccountResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        Account account = accountRepository.findByEmail(changePasswordRequest.getEmail())
                .orElseThrow(() -> new RuntimeException(ERROR_EMAIL_NOT_FOUND));
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword()))
            throw new RuntimeException(ERROR_PASSWORD_CONFIRM_MUST_MATCH_NEW);
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), account.getPassword()))
            throw new RuntimeException(ERROR_PASSWORD_OLD_INVALID);
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), account.getPassword()))
            throw new RuntimeException(ERROR_PASSWORD_NEW_MUST_DIFFERENT_OLD);
        account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        return userMapper.convertToResponse(accountRepository.save(account));
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

        if (account.getVerificationCode() == null || !account.getVerificationCode().equals(forgotPasswordRequest.getVerificationCode()))
            throw new RuntimeException(ERROR_VERIFICATION_CODE_INVALID);

        if (account.getVerificationCodeExpiredAt().before(new Date()))
            throw new RuntimeException(ERROR_VERIFICATION_CODE_EXPIRED);

        account.setPassword(passwordEncoder.encode(forgotPasswordRequest.getNewPassword()));
        account.setVerificationCode(null);
        account.setVerificationCodeExpiredAt(null);
        accountRepository.save(account);
    }
}
