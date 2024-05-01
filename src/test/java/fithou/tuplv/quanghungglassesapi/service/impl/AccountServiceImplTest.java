package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.*;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.service.AccountService;
import fithou.tuplv.quanghungglassesapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AccountServiceImplTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CustomerService customerService;

    @Test
    void registerSuccess() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFullname("Việt Tú");
        customerRequest.setPhone("0555555555");
        customerRequest.setGender("Nam");
        customerRequest.setAddress("Hanoi");
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setEmail("viettu@gmail.com");
        accountRequest.setPassword("Viettu@123");
        accountRequest.setStatus(true);
        customerRequest.setAccount(accountRequest);

        assertNotNull(customerService.create(customerRequest));
    }

    @Test
    void registerWithPhoneExists() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFullname("Việt Tú");
        customerRequest.setPhone("0555555555");
        customerRequest.setGender("Nam");
        customerRequest.setAddress("Hanoi");
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setEmail("viettu@gmail.com");
        accountRequest.setPassword("Viettu@123");
        accountRequest.setStatus(true);
        customerRequest.setAccount(accountRequest);

        assertThatThrownBy(() -> customerService.create(customerRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void registerWithEmailExists() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setFullname("Việt Tú");
        customerRequest.setPhone("044444444");
        customerRequest.setGender("Nam");
        customerRequest.setAddress("Hanoi");
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setEmail("viettu@gmail.com");
        accountRequest.setPassword("Viettu@123");
        accountRequest.setStatus(true);
        customerRequest.setAccount(accountRequest);

        assertThatThrownBy(() -> customerService.create(customerRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void loginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("adminquanghung@gmail.com");
        loginRequest.setPassword("123");
        assertNotNull(accountService.login(loginRequest));
    }

    @Test
    void loginWithEmailNotExists() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("admin@gmail.com");
        loginRequest.setPassword("123");
        assertThatThrownBy(() -> accountService.login(loginRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void loginWithPasswordInvalid() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("adminquanhung@gmail.com");
        loginRequest.setPassword("12345678");
        assertThatThrownBy(() -> accountService.login(loginRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void forgotPasswordSuccess() {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail("nguyenthile050801@gmail.com");
        forgotPasswordRequest.setVerificationCode("099057");
        forgotPasswordRequest.setNewPassword("123123");
        assertThatNoException().isThrownBy(() -> accountService.forgotPassword(forgotPasswordRequest));
    }

    @Test
    void forgotPasswordWithVerificationCodeInvalid() {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail("nguyenthile050801@gmail.com");
        forgotPasswordRequest.setVerificationCode("abcxyz");
        forgotPasswordRequest.setNewPassword("123123");
        assertThatThrownBy(() -> accountService.forgotPassword(forgotPasswordRequest));
    }

    @Test
    void forgotPasswordWithVerificationCodeExpired() {
        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setEmail("nguyenthile050801@gmail.com");
        forgotPasswordRequest.setVerificationCode("099057");
        forgotPasswordRequest.setNewPassword("123123");
        assertThatThrownBy(() -> accountService.forgotPassword(forgotPasswordRequest));
    }

    @Test
    void changePasswordSuccess() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
//        changePasswordRequest.setEmail("adminquanghung@gmail.com");
        changePasswordRequest.setOldPassword("123");
        changePasswordRequest.setNewPassword("1234");
        changePasswordRequest.setConfirmPassword("1234");
        assertThatNoException().isThrownBy(() -> accountService.changePassword(changePasswordRequest));
    }

    @Test
    void changePasswordWithOldPasswordInvalid() {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
//        changePasswordRequest.setEmail("adminquanghung@gmail.com");
        changePasswordRequest.setOldPassword("123");
        changePasswordRequest.setNewPassword("1234");
        changePasswordRequest.setConfirmPassword("1234");
        assertThatThrownBy(() -> accountService.changePassword(changePasswordRequest));
    }

    @Test
    void logoutSuccess() {
        assertThatNoException().isThrownBy(() -> accountService.logout());
    }
}