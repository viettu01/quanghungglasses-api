package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.ChangePasswordRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ForgotPasswordRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.LoginRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.LoginResponse;

public interface AccountService {
    LoginResponse login(LoginRequest loginRequest);

    boolean existsByEmail(String email);

    void changePassword(ChangePasswordRequest changePasswordRequest);

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

    void logout();
}
