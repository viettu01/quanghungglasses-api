package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.CustomerRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.RegisterRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.VerifyEmailRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import fithou.tuplv.quanghungglassesapi.entity.Account;
import fithou.tuplv.quanghungglassesapi.entity.Customer;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.service.CustomerService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    final AccountRepository accountRepository;
    final CustomerRepository customerRepository;
    final UserMapper userMapper;
    final ModelMapper modelMapper;

    @Override
    public PaginationDTO<CustomerResponse> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(customerRequest.getId())
                .orElseThrow(() -> new RuntimeException(ERROR_EMAIL_ALREADY_EXISTS));

        return userMapper.convertToResponse(customerRepository.save(customer));
    }

    @Override
    public void deleteByIds(Long[] ids) {

    }

    @Override
    public CustomerResponse register(RegisterRequest registerRequest) {
        if (accountRepository.existsByEmail(registerRequest.getEmail()))
            throw new RuntimeException(ERROR_EMAIL_ALREADY_EXISTS);

        Account account = userMapper.convertToEntity(registerRequest);
        accountRepository.save(account);
        Customer customer = modelMapper.map(registerRequest, Customer.class);
        customer.setAccount(account);
        return userMapper.convertToResponse(customerRepository.save(customer));
    }


    @Override
    public void verifyEmail(VerifyEmailRequest verifyEmailRequest) {
        Account account = accountRepository.findByEmailAndVerificationCode(verifyEmailRequest.getEmail(), verifyEmailRequest.getVerificationCode())
                .orElseThrow(() -> new RuntimeException(ERROR_VERIFICATION_CODE_INVALID));

        if (account.getVerificationCodeExpiredAt().before(new Date()))
            throw new RuntimeException(ERROR_VERIFICATION_CODE_EXPIRED);

        account.setIsVerifiedEmail(true);
        account.setVerificationCodeExpiredAt(null);
        account.setVerificationCode(null);
        accountRepository.save(account);
    }

    @Override
    public CustomerResponse resendVerificationCode(String email) {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(ERROR_EMAIL_NOT_FOUND));

        if (!account.getStatus())
            throw new RuntimeException(ERROR_ACCOUNT_IS_LOCKED);

        account.setVerificationCode(RandomStringUtils.randomNumeric(6));
        account.setVerificationCodeExpiredAt(new Date(new Date().getTime() + 5 * 60 * 1000));
        accountRepository.save(account);

        return userMapper.convertToResponse(customerRepository.findByAccountEmail(email)
                .orElseThrow(() -> new RuntimeException(ERROR_EMAIL_NOT_FOUND)));
    }
}
