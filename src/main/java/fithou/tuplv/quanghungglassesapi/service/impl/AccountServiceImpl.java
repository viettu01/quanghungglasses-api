package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.ChangePasswordRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ForgotPasswordRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.LoginRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.LoginResponse;
import fithou.tuplv.quanghungglassesapi.entity.Account;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.security.CustomUserDetails;
import fithou.tuplv.quanghungglassesapi.security.jwt.JwtTokenProvider;
import fithou.tuplv.quanghungglassesapi.service.AccountService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    final AuthenticationManager authenticationManager;
    final AccountRepository accountRepository;
    final PasswordEncoder passwordEncoder;
    final JwtTokenProvider tokenProvider;
    final StorageService storageService;
    final UserMapper userMapper;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        try {
            if (!accountRepository.existsByEmail(loginRequest.getEmail()))
                throw new RuntimeException(ERROR_USER_NOT_FOUND);

            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Nếu thông tin hợp lệ -> Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            return new LoginResponse(jwt);
        } catch (LockedException ex) {
            // Người dùng bị khóa
            throw new LockedException(ERROR_ACCOUNT_IS_LOCKED);
        } catch (BadCredentialsException ex) {
            // Người dùng nhập sai mật khẩu
            throw new BadCredentialsException(ERROR_PASSWORD_INVALID);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        Account account = accountRepository.findByEmail(changePasswordRequest.getEmail())
                .orElseThrow(() -> new RuntimeException(ERROR_EMAIL_NOT_FOUND));
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword()))
            throw new RuntimeException(ERROR_PASSWORD_CONFIRM_MUST_MATCH_NEW);
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), account.getPassword()))
            throw new RuntimeException(ERROR_PASSWORD_OLD_INVALID);
        if (passwordEncoder.matches(changePasswordRequest.getNewPassword(), account.getPassword()))
            throw new RuntimeException(ERROR_PASSWORD_NEW_MUST_DIFFERENT_OLD);
        account.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        accountRepository.save(account);
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
