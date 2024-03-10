package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.AccountRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.StaffRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.StaffResponse;
import fithou.tuplv.quanghungglassesapi.repository.StaffRepository;
import fithou.tuplv.quanghungglassesapi.service.StaffService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class StaffServiceTest {

    @Autowired
    StaffService staffService;

    @Autowired
    StaffRepository staffRepository;

    @Test
    void testFindAll() {
        long actualSuppliers = staffService.findByFullnameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedSuppliers = staffRepository.count(); // Lay tong so phan tu trong database
        assertEquals(expectedSuppliers, actualSuppliers);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("fullname").ascending());

        // Act
        PaginationDTO<StaffResponse> actualSupplier = staffService.findByFullnameContaining("", pageable);

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
        StaffResponse actualSupplier = staffService.findById(id);

        // Assert
        assertEquals(id, actualSupplier.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        // Arrange
        long id = 1000;

        // Act
        assertThatThrownBy(() -> staffService.findById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByFullnameContainingWithNotRecord() {
        // Arrange
        String name = "z";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<StaffResponse> actualSupplier = staffService.findByFullnameContaining(name, pageable);

        // Assert
        assertEquals(0, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về = 0
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "u";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<StaffResponse> actualStaff = staffService.findByFullnameContaining(name, pageable);

        // Assert
        assertEquals(3, actualStaff.getNumberOfElements()); // Kiểm tra kết quả trả về = 3
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "Nguyễn Thị Lê";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<StaffResponse> actualStaff = staffService.findByFullnameContaining(name, pageable);

        // Assert
        assertEquals(1, actualStaff.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testCreateWithPhoneExists() {
        // Arrange
        String phone = "0328845817";
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setFullname("Nguyễn Thị Lê Lê");
        staffRequest.setPhone(phone);
        staffRequest.setGender("Nữ");
        staffRequest.setStatus(true);

        // Act
        assertThatThrownBy(() -> staffService.create(staffRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testCreateWithEmailExists() {
        // Arrange
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setFullname("Nguyễn Thị Lê Lê");
        staffRequest.setPhone("0966871026");
        staffRequest.setGender("Nữ");
        staffRequest.setStatus(true);
        AccountRequest account = new AccountRequest();
        account.setPassword("12345678");
        account.setEmail("trangtran@gmail.com");
        account.setStatus(true);
        staffRequest.setAccount(account);

        // Assert
        assertThatThrownBy(() -> staffService.create(staffRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createSuccessWithOnlyInfo() {
        // Arrange
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setFullname("Nguyễn Thị Lê Lê");
        staffRequest.setPhone("0966871026");
        staffRequest.setGender("Nữ");
        staffRequest.setStatus(true);

        // Act
        StaffResponse actualStaff = staffService.create(staffRequest);

        // Assert
        assertNotNull(actualStaff.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createSuccessWithInfoAndAccount() {
        // Arrange
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setFullname("Lê Nguyễn Thị Lê");
        staffRequest.setPhone("0966666666");
        staffRequest.setGender("Nữ");
        staffRequest.setStatus(true);
        AccountRequest account = new AccountRequest();
        account.setEmail("nguyenle050801@gmail.com");
        account.setPassword("12345678");
        account.setStatus(true);
        account.setRoleIds(Collections.singletonList(2L));
        staffRequest.setAccount(account);

        // Act
        StaffResponse actualSupplier = staffService.create(staffRequest);

        // Assert
        assertNotNull(actualSupplier.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void updateWithPhoneExists() {
        // Arrange
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setId(4L);
        staffRequest.setFullname("Nguyễn Thị Lê Lê");
        staffRequest.setPhone("0328845817");
        staffRequest.setGender("Nữ");
        staffRequest.setStatus(true);

        // Act
        assertThatThrownBy(() -> staffService.update(staffRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateWithEmailExists() {
        // Arrange
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setId(5L);
        staffRequest.setFullname("Nguyễn Thị Lê Lê");
        staffRequest.setPhone("0966666666");
        staffRequest.setGender("Nữ");
        staffRequest.setStatus(true);
        AccountRequest account = new AccountRequest();
        account.setId(3L);
        account.setEmail("trangtran@gmail.com");
        account.setStatus(true);
        account.setRoleIds(Collections.singletonList(2L));
        staffRequest.setAccount(account);

        // Act
        assertThatThrownBy(() -> staffService.update(staffRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSuccessWithOnlyInfo() {
        // Arrange
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setId(4L);
        staffRequest.setFullname("Nguyễn Lê 1234");
        staffRequest.setPhone("0977777777");
        staffRequest.setGender("Nam");
        staffRequest.setStatus(true);

        // Act
        StaffResponse actualStaff = staffService.update(staffRequest);

        // Assert
        assertEquals("Nguyễn Lê 1234", actualStaff.getFullname());
        assertEquals("Nam", actualStaff.getGender());
        assertEquals("0977777777", actualStaff.getPhone());
        assertEquals(true, actualStaff.getStatus());
    }

    @Test
    void updateSuccessWithInfoCustomerAndAccount() {
        // Arrange
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setId(5L);
        staffRequest.setFullname("Nguyễn Lê");
        staffRequest.setPhone("0988888888");
        staffRequest.setGender("Nam");
        staffRequest.setStatus(false);
        AccountRequest account = new AccountRequest();
        account.setId(5L);
        account.setEmail("nguyenle0102@gmail.com");
        account.setPassword("");
        account.setStatus(true);
        account.setRoleIds(Collections.singletonList(2L));
        staffRequest.setAccount(account);

        // Act
        StaffResponse actualStaff = staffService.update(staffRequest);

        // Assert
        assertEquals("Nguyễn Lê", actualStaff.getFullname());
        assertEquals("0988888888", actualStaff.getPhone());
        assertEquals("Nam", actualStaff.getGender());
        assertEquals(false, actualStaff.getStatus());
        assertEquals("nguyenle0102@gmail.com", actualStaff.getAccount().getEmail());
        assertEquals(true, actualStaff.getAccount().getStatus());
    }
}