package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.CustomerRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.RegisterRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.StaffRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AccountResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.StaffResponse;
import fithou.tuplv.quanghungglassesapi.entity.*;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;
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
        account.setRoles(accountRequest.getRoleIds().stream()
                .map(roleId -> roleRepository.findById(roleId).orElse(null))
                .collect(Collectors.toList()));
        return account;
    }

    public Account convertToEntity(RegisterRequest registerRequest) {
        Account account = modelMapper.map(registerRequest, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setRoles(Collections.singletonList(roleRepository.findById(3L).orElse(null)));
        account.setStatus(true);
        account.setIsVerifiedEmail(false);
        account.setVerificationCode(RandomStringUtils.randomNumeric(6));
        account.setVerificationCodeExpiredAt(new Date(new Date().getTime() + 5 * 60 * 1000));
        return account;
    }

    public AccountResponse convertToResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }

    public Staff convertToEntity(StaffRequest staffRequest) {
        return modelMapper.map(staffRequest, Staff.class);
    }

    public StaffResponse convertToResponse(Staff staff) {
        StaffResponse staffResponse = modelMapper.map(staff, StaffResponse.class);
        staffResponse.setTotalOrder(0);
        staffResponse.setTotalMoney(0D);
        if (staff.getOrders() != null && !staff.getOrders().isEmpty()) {
            double totalMoney = 0.0;
            staffResponse.setTotalOrder(staff.getOrders().size());
            for (Order order : staff.getOrders()) {
                if (order.getPaymentStatus()) {
                    for (OrderDetails orderDetails : order.getOrderDetails()) {
                        totalMoney += orderDetails.getPrice() * orderDetails.getQuantity();
                    }
                }
            }
            staffResponse.setTotalMoney(totalMoney);
        }
        return staffResponse;
    }


    public Customer convertToEntity(CustomerRequest customerRequest) {
        return modelMapper.map(customerRequest, Customer.class);
    }

    public CustomerResponse convertToResponse(Customer customer) {
        CustomerResponse customerResponse = modelMapper.map(customer, CustomerResponse.class);
        customerResponse.setTotalOrder(0);
        customerResponse.setTotalMoney(0D);
        if (customer.getOrders() != null && !customer.getOrders().isEmpty()) {
            double totalMoney = 0.0;
            customerResponse.setTotalOrder(customer.getOrders().size());
            for (Order order : customer.getOrders()) {
                if (order.getPaymentStatus()) {
                    for (OrderDetails orderDetails : order.getOrderDetails()) {
                        totalMoney += orderDetails.getPrice() * orderDetails.getQuantity();
                    }
                }
            }
            customerResponse.setTotalMoney(totalMoney);
        }

        return customerResponse;
    }
}