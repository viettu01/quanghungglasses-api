package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.CustomerRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.RegisterRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.VerifyEmailRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    PaginationDTO<CustomerResponse> findAll(Pageable pageable);

    CustomerResponse create(CustomerRequest customerRequest);

    CustomerResponse update(CustomerRequest customerRequest);

    void deleteByIds(Long[] ids);

    CustomerResponse register(RegisterRequest registerRequest);

    void verifyEmail(VerifyEmailRequest verifyEmailRequest);

    CustomerResponse resendVerificationCode(String email);
}
