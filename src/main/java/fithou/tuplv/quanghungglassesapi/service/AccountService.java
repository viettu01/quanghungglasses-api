package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ChangePasswordRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ForgotPasswordRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AccountResponse;

public interface AccountService {
    boolean existsByUsername(String username);

    AccountResponse create(AccountRequest accountRequest);

    AccountResponse changePassword(ChangePasswordRequest changePasswordRequest);

    void delete(Long[] ids);

    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);

}
