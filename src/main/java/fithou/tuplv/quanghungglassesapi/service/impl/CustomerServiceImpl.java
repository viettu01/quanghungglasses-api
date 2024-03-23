package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.CustomerRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.VerifyEmailRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import fithou.tuplv.quanghungglassesapi.entity.Account;
import fithou.tuplv.quanghungglassesapi.entity.Cart;
import fithou.tuplv.quanghungglassesapi.entity.Customer;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.AccountRepository;
import fithou.tuplv.quanghungglassesapi.repository.CartRepository;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.service.CustomerService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    final StorageService storageService;
    final CartRepository cartRepository;
    final AccountRepository accountRepository;
    final CustomerRepository customerRepository;
    final PaginationMapper paginationMapper;
    final UserMapper userMapper;
    final ModelMapper modelMapper;


    @Override
    public PaginationDTO<CustomerResponse> findByFullnameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(
                customerRepository.findByFullnameContaining(name, pageable).map(userMapper::convertToResponse)
        );
    }

    @Override
    public CustomerResponse findById(Long id) {
        return userMapper.convertToResponse(
                customerRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException(ERROR_USER_NOT_FOUND))
        );
    }

    @Override
    public CustomerResponse findByAccountEmail(String email) {
        return userMapper.convertToResponse(
                customerRepository.findByAccountEmail(email)
                        .orElseThrow(() -> new RuntimeException(ERROR_USER_NOT_FOUND))
        );
    }

    @Override
    public CustomerResponse create(CustomerRequest customerRequest) {
        if (customerRepository.existsByPhone(customerRequest.getPhone()))
            throw new RuntimeException(ERROR_PHONE_ALREADY_EXISTS);
        Customer customer = userMapper.convertToEntity(customerRequest);
        customer.setAccount(null);
        customerRepository.save(customer);
        if (customerRequest.getAccount() != null) {
            saveAccount(customerRequest, customer);
        }

//        try {
//            customerRepository.save(customer);
//        } catch (Exception e) {
////            if (customer.getAccount() != null && customer.getAccount().getAvatar() != null)
////                storageService.deleteFile(customer.getAccount().getAvatar());
//        }

        return userMapper.convertToResponse(customer);
    }

    @Override
    public CustomerResponse update(CustomerRequest customerRequest) {
        Customer customerExists = customerRepository.findById(customerRequest.getId())
                .orElseThrow(() -> new RuntimeException(ERROR_USER_NOT_FOUND));

        if (!customerExists.getPhone().equals(customerRequest.getPhone())
                && customerRepository.existsByPhone(customerRequest.getPhone()))
            throw new RuntimeException(ERROR_PHONE_ALREADY_EXISTS);

        Customer customer = userMapper.convertToEntity(customerRequest);
        if (customerRequest.getAccount() != null) {
            if (customerExists.getAccount() != null) {
                if (customer.getAccount() == null) {
                    if (accountRepository.existsByEmail(customerRequest.getAccount().getEmail()))
                        throw new RuntimeException(ERROR_EMAIL_ALREADY_EXISTS);
                } else if (!customer.getAccount().getEmail().equals(customerRequest.getAccount().getEmail())
                        && accountRepository.existsByEmail(customerRequest.getAccount().getEmail()))
                    throw new RuntimeException(ERROR_EMAIL_ALREADY_EXISTS);
                customerRequest.getAccount().setRoleIds(Collections.singletonList(3L));
                Account account = userMapper.convertToEntity(customerRequest.getAccount());
                account.setIsVerifiedEmail(true);
//                String oldFileName = null;
//                if (customerRequest.getAccount().getAvatarFile() != null && customerRequest.getAccount().getAvatarFile().isEmpty()) {
//                    account.setAvatar(storageService.saveImageFile(DIR_FILE_CUSTOMER, customerRequest.getAccount().getAvatarFile()));
//                    oldFileName = customerExists.getAccount().getAvatar();
//                } else {
//                    if (customerExists.getAccount().getAvatar() != null) {
//                        account.setAvatar(customerExists.getAccount().getAvatar());
//                    }
//                }

                if (StringUtils.isEmpty(customerRequest.getAccount().getPassword()))
                    account.setPassword(customerExists.getAccount().getPassword());
                try {
                    accountRepository.save(account);
                } catch (Exception e) {
                    if (account.getAvatar() != null)
                        storageService.deleteFile(account.getAvatar());
                }
//                if (oldFileName != null)
//                    storageService.deleteFile(oldFileName);
                customer.setAccount(account);
            } else {
                saveAccount(customerRequest, customer);
            }
        }
        try {
            customerRepository.save(customer);
        } catch (Exception e) {
//            if (customer.getAccount() != null && customer.getAccount().getAvatar() != null)
//                storageService.deleteFile(customer.getAccount().getAvatar());
        }

        return userMapper.convertToResponse(customer);
    }

    @Override
    public void deleteById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ERROR_USER_NOT_FOUND));
        if (!customer.getOrders().isEmpty())
            throw new RuntimeException("Không thể xóa khách hàng đã có đơn hàng");
//        if (customer.getAccount() != null && customer.getAccount().getAvatar() != null)
//            storageService.deleteFile(customer.getAccount().getAvatar());
        customerRepository.deleteById(id);
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

    private void saveAccount(CustomerRequest customerRequest, Customer customer) {
        if (accountRepository.existsByEmail(customerRequest.getAccount().getEmail()))
            throw new RuntimeException(ERROR_EMAIL_ALREADY_EXISTS);
        customerRequest.getAccount().setRoleIds(Collections.singletonList(3L));
        Account account = userMapper.convertToEntity(customerRequest.getAccount());
        account.setIsVerifiedEmail(true);
//        if (customerRequest.getAccount().getAvatarFile() != null && !customerRequest.getAccount().getAvatarFile().isEmpty())
//            account.setAvatar(storageService.saveImageFile(DIR_FILE_CUSTOMER, customerRequest.getAccount().getAvatarFile()));
        try {
            accountRepository.save(account);
        } catch (Exception e) {
//            if (account.getAvatar() != null)
//                storageService.deleteFile(account.getAvatar());
        }
        customer.setAccount(account);
        if (customer.getCart() == null)
            cartRepository.save(new Cart(null, customer, null));
        customerRepository.save(customer);
    }
}
