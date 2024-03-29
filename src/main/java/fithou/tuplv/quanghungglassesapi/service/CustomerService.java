package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.CustomerRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.VerifyEmailRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    PaginationDTO<CustomerResponse> findByFullnameContaining(String name, Pageable pageable);

    CustomerResponse findById(Long id);

    CustomerResponse findByAccountEmail(String email);

    CustomerResponse create(CustomerRequest customerRequest);

    CustomerResponse register(CustomerRequest customerRequest);

    CustomerResponse update(CustomerRequest customerRequest);

    void deleteById(Long id);

    void verifyEmail(VerifyEmailRequest verifyEmailRequest);

    CustomerResponse resendVerificationCode(String email);
}
