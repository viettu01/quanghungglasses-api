package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.SupplierRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.SupplierResponse;
import fithou.tuplv.quanghungglassesapi.repository.SupplierRepository;
import fithou.tuplv.quanghungglassesapi.service.SupplierService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class SupplierServiceTest {

    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    SupplierService supplierService;

    @Test
    void testFindAll() {
        long actualSuppliers = supplierService.findByNameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedSuppliers = supplierRepository.count(); // Lay tong so phan tu trong database
        assertEquals(expectedSuppliers, actualSuppliers);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        // Act
        PaginationDTO<SupplierResponse> actualSupplier = supplierService.findByNameContaining("", pageable);

        // Assert
//        assertNotNull(actualSupplier); // Kiểm tra kết quả trả về khác null
        assertEquals(5, actualSupplier.getPageSize()); // Kiểm tra số lượng phần tử trên mỗi trang
        assertEquals(1, actualSupplier.getPageNumber()); // Kiểm tra trang hiện tại
        assertEquals("name", actualSupplier.getSortBy()); // Kiểm tra sắp xếp theo tên
    }

    @Test
    void findByIdWithIdExists() {
        // Arrange
        long id = 3;

        // Act
        SupplierResponse actualSupplier = supplierService.findById(id);

        // Assert
        assertEquals(id, actualSupplier.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> supplierService.findById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByNameContainingWithNotRecord() {
        // Arrange
        String name = "z";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<SupplierResponse> actualSupplier = supplierService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(0, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "Công ty";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<SupplierResponse> actualSupplier = supplierService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(2, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "G – Shop 4";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<SupplierResponse> actualSupplier = supplierService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(1, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createWhileNameExists() {
        // Arrange
        SupplierRequest supplierRequest = new SupplierRequest();
        supplierRequest.setName("G – Shop");
        supplierRequest.setPhone("0123456789");

        // Act
        assertThatThrownBy(() -> supplierService.create(supplierRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createWhilePhoneExists() {
        // Arrange
        SupplierRequest supplierRequest = new SupplierRequest();
        supplierRequest.setName("G – Shop 2");
        supplierRequest.setPhone("0998765544");

        // Act
        assertThatThrownBy(() -> supplierService.create(supplierRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createSuccess() {
        // Arrange
        SupplierRequest supplierRequest = new SupplierRequest();
        supplierRequest.setName("G – Shop 2");
        supplierRequest.setPhone("0966871026");

        // Act
        SupplierResponse actualSupplier = supplierService.create(supplierRequest);
        assertNotNull(actualSupplier.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void updateWhileNameExists() {
        // Arrange
        SupplierRequest supplierRequest = new SupplierRequest();
        supplierRequest.setId(2L);
        supplierRequest.setName("G – Shop");
        supplierRequest.setPhone("0998765544");

        // Act
        assertThatThrownBy(() -> supplierService.update(supplierRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateWhilePhoneExists() {
        // Arrange
        SupplierRequest supplierRequest = new SupplierRequest();
        supplierRequest.setId(2L);
        supplierRequest.setName("G – Shop 3");
        supplierRequest.setPhone("0998765544");

        // Act
        assertThatThrownBy(() -> supplierService.update(supplierRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSuccess() {
        // Arrange
        SupplierRequest supplierRequest = new SupplierRequest();
        supplierRequest.setId(7L);
        supplierRequest.setName("G – Shop 4");
        supplierRequest.setPhone("0998765543");

        // Act
        SupplierResponse actualSupplier = supplierService.create(supplierRequest);
        assertEquals(supplierRequest.getName(), actualSupplier.getName()); // Kiem tra ten moi duoc cap nhat co dung khong
        assertEquals(supplierRequest.getPhone(), actualSupplier.getPhone()); // Kiem tra so dien thoai moi duoc cap nhat co dung khong
    }

    @Test
    void deleteByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> supplierService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithReceiptExists() {
        long id = 2;
        assertThatThrownBy(() -> supplierService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdSuccess() {
        long id = 8;
        supplierService.deleteById(id);
        assertFalse(supplierRepository.existsById(id));
    }
}