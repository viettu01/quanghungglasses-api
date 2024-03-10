package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.CustomerRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void testFindAll() {
        long actualSuppliers = customerService.findByFullnameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedSuppliers = customerRepository.count(); // Lay tong so phan tu trong database
        assertEquals(expectedSuppliers, actualSuppliers);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("fullname").ascending());

        // Act
        PaginationDTO<CustomerResponse> actualSupplier = customerService.findByFullnameContaining("", pageable);

        // Assert
        assertEquals(5, actualSupplier.getPageSize()); // Kiểm tra số lượng phần tử trên mỗi trang
        assertEquals(1, actualSupplier.getPageNumber()); // Kiểm tra trang hiện tại
        assertEquals("fullname", actualSupplier.getSortBy()); // Kiểm tra sắp xếp theo tên
    }

    @Test
    void findByIdWithIdExists() {
        // Arrange
        long id = 1;

        // Act
        CustomerResponse actualSupplier = customerService.findById(id);

        // Assert
        assertEquals(id, actualSupplier.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        // Arrange
        long id = 1000;

        // Act
        assertThatThrownBy(() -> customerService.findById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByFullnameContainingWithNotRecord() {
        // Arrange
        String name = "z";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<CustomerResponse> actualSupplier = customerService.findByFullnameContaining(name, pageable);

        // Assert
        assertEquals(0, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về = 0
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "t";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<CustomerResponse> actualSupplier = customerService.findByFullnameContaining(name, pageable);

        // Assert
        assertEquals(2, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về = 2
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "Nguyễn Thị Lê";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<CustomerResponse> actualSupplier = customerService.findByFullnameContaining(name, pageable);

        // Assert
        assertEquals(1, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testCreateWithPhoneExists() {
        // Arrange
        String phone = "0328845817";
        CustomerRequest customer = new CustomerRequest();
        customer.setFullname("Nguyễn Thị Lê Lê");
        customer.setPhone(phone);
        customer.setGender("Nữ");

        // Act
        assertThatThrownBy(() -> customerService.create(customer)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testCreateWithEmailExists() {
        // Arrange
        CustomerRequest customer = new CustomerRequest();
        customer.setFullname("Nguyễn Thị Lê Lê");
        customer.setPhone("0966871026");
        customer.setGender("Nữ");
        AccountRequest account = new AccountRequest();
        account.setEmail("trangtran@gmail.com");
        customer.setAccount(account);

        // Assert
        assertThatThrownBy(() -> customerService.create(customer)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createSuccessWithOnlyInfoCustomer() {
        // Arrange
        CustomerRequest customer = new CustomerRequest();
        customer.setFullname("Nguyễn Thị Lê Lê");
        customer.setPhone("0966871026");
        customer.setGender("Nữ");

        // Act
        CustomerResponse actualSupplier = customerService.create(customer);

        // Assert
        assertNotNull(actualSupplier.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createSuccessWithInfoCustomerAndAccount() {
        // Arrange
        CustomerRequest customer = new CustomerRequest();
        customer.setFullname("Nguyễn Thị Lê Lê");
        customer.setPhone("0966666666");
        customer.setGender("Nữ");
        AccountRequest account = new AccountRequest();
        account.setEmail("nguyenle050801@gmail.com");
        account.setPassword("12345678");
        account.setStatus(true);
        customer.setAccount(account);

        // Act
        CustomerResponse actualSupplier = customerService.create(customer);

        // Assert
        assertNotNull(actualSupplier.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void updateWithPhoneExists() {
        // Arrange
        CustomerRequest customer = new CustomerRequest();
        customer.setId(4L);
        customer.setFullname("Nguyễn Thị Lê Lê");
        customer.setPhone("0328845817");
        customer.setGender("Nữ");

        // Act
        assertThatThrownBy(() -> customerService.update(customer)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateWithEmailExists() {
        // Arrange
        CustomerRequest customer = new CustomerRequest();
        customer.setId(4L);
        customer.setFullname("Nguyễn Thị Lê Lê");
        customer.setPhone("0966871026");
        customer.setGender("Nữ");
        AccountRequest account = new AccountRequest();
        account.setId(3L);
        account.setEmail("trangtran@gmail.com");
        account.setStatus(true);
        customer.setAccount(account);

        // Act
        assertThatThrownBy(() -> customerService.update(customer)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSuccessWithOnlyInfoCustomer() {
        // Arrange
        CustomerRequest customer = new CustomerRequest();
        customer.setId(4L);
        customer.setFullname("Nguyễn Lê");
        customer.setPhone("0966666666");
        customer.setGender("Nữ");

        // Act
        CustomerResponse actualSupplier = customerService.update(customer);

        // Assert
        assertEquals("Nguyễn Lê", actualSupplier.getFullname());
    }

    @Test
    void updateSuccessWithInfoCustomerAndAccount() {
        // Arrange
        CustomerRequest customer = new CustomerRequest();
        customer.setId(4L);
        customer.setFullname("Nguyễn Lê");
        customer.setPhone("0966666666");
        customer.setGender("Nữ");
        AccountRequest account = new AccountRequest();
        account.setId(3L);
        account.setEmail("nguyenle01@gmail.com");
        account.setPassword("12345678");
        account.setStatus(true);
        customer.setAccount(account);

        // Act
        CustomerResponse actualSupplier = customerService.update(customer);

        // Assert
        assertEquals("Nguyễn Lê", actualSupplier.getFullname());
        assertEquals("nguyenle01@gmail.com", actualSupplier.getAccount().getEmail());
    }
}