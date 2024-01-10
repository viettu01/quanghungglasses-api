package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.CustomerRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.RegisterRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.StaffRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AccountResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.StaffResponse;
import fithou.tuplv.quanghungglassesapi.entity.Account;
import fithou.tuplv.quanghungglassesapi.entity.Customer;
import fithou.tuplv.quanghungglassesapi.entity.Staff;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {
    final ModelMapper modelMapper;
    final RoleRepository roleRepository;
    final AccountRepository accountRepository;
    final PasswordEncoder passwordEncoder;

    public Account convertToEntity(AccountRequest accountRequest) {
        Account account = modelMapper.map(accountRequest, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRoles(accountRequest.getRoleName().stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toList())
        );
        return account;
    }

    public Account convertToEntity(RegisterRequest registerRequest) {
        Account account = modelMapper.map(registerRequest, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        account.setStatus(true);
        return account;
    }

    public AccountResponse convertToResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }

    public Staff convertToEntity(StaffRequest staffRequest) {
        Staff staff = modelMapper.map(staffRequest, Staff.class);
        staff.setAccount(accountRepository.findById(staffRequest.getAccountId()).orElse(null));
        return staff;
    }

    public StaffResponse convertToResponse(Staff staff) {
        return modelMapper.map(staff, StaffResponse.class);
    }

    public Customer convertToEntity(CustomerRequest customerRequest) {
        return modelMapper.map(customerRequest, Customer.class);
    }

    public CustomerResponse convertToResponse(Customer customer) {
        return modelMapper.map(customer, CustomerResponse.class);
    }
}