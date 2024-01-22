package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.CustomerRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.RegisterRequest;
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
    public CustomerResponse create(CustomerRequest staffRequest) {
        return null;
    }

    @Override
    public CustomerResponse update(CustomerRequest staffRequest) {
        return null;
    }

    @Override
    public void deleteByIds(Long[] ids) {

    }

    @Override
    public CustomerResponse register(RegisterRequest registerRequest) {
        Account account = userMapper.convertToEntity(registerRequest);
        account.setIsVerifiedEmail(false);
        account.setVerificationCode(RandomStringUtils.randomNumeric(6));
        account.setVerificationCodeExpiredAt(new Date(new Date().getTime() + 5 * 60 * 1000));
        accountRepository.save(account);
        Customer customer = modelMapper.map(registerRequest, Customer.class);
        customer.setAccount(account);
        return userMapper.convertToResponse(customerRepository.save(customer));
    }
}
