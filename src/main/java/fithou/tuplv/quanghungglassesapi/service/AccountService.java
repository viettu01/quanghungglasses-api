package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AccountResponse;

public interface AccountService {
    boolean existsByUsername(String username);

    AccountResponse create(AccountRequest accountRequest);

    AccountResponse update(AccountRequest accountRequest);

    void delete(Long[] ids);
}
