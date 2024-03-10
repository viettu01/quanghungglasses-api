package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.MaterialRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.MaterialResponse;
import fithou.tuplv.quanghungglassesapi.repository.MaterialRepository;
import fithou.tuplv.quanghungglassesapi.service.MaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MaterialServiceImplTest {
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    MaterialService materialService;

    @Test
    void testFindAll() {
        long actualMaterials = materialService.findByNameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedMaterials = materialRepository.count(); // Lay tong so phan tu trong database
        assertEquals(actualMaterials, expectedMaterials);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        // Act
        PaginationDTO<MaterialResponse> actualSupplier = materialService.findByNameContaining("", pageable);

        // Assert
//        assertNotNull(actualSupplier); // Kiểm tra kết quả trả về khác null
        assertEquals(5, actualSupplier.getPageSize()); // Kiểm tra số lượng phần tử trên mỗi trang
        assertEquals(1, actualSupplier.getPageNumber()); // Kiểm tra trang hiện tại
        assertEquals("name", actualSupplier.getSortBy()); // Kiểm tra sắp xếp theo tên
    }

    @Test
    void findByIdWithIdExists() {
        // Arrange
        long id = 1;

        // Act
        MaterialResponse actualMaterial = materialService.findById(id);

        // Assert
        assertEquals(id, actualMaterial.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> materialService.findById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByNameContainingWithNotRecord() {
        // Arrange
        String name = "z";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<MaterialResponse> actualMaterial = materialService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(0, actualMaterial.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "Nhựa";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<MaterialResponse> actualMaterial = materialService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(6, actualMaterial.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "G – Shop 2";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<MaterialResponse> actualMaterial = materialService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(1, actualMaterial.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createSuccess() {
        // Arrange
        MaterialRequest materialRequest = new MaterialRequest();
        materialRequest.setName("G – Shop 2");

        //Kiểm tra id trả về khác null hay không
        // Act
        //Sau khi Thêm dl trả về actualMaterial
        MaterialResponse actualMaterial = materialService.create(materialRequest);
        assertNotNull(actualMaterial.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createWhileNameExists() {
        // Arrange
        MaterialRequest materialRequest = new MaterialRequest();
        materialRequest.setName("Kim loại");
        //kiểm tra có trả ra ngoại lệ hay không , nếu trả ra ngoại lệ pass
        // Act
        assertThatThrownBy(() -> materialService.create(materialRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSuccess() {
        MaterialRequest materialRequest = new MaterialRequest();
        materialRequest.setId(1L);
        materialRequest.setName("Hợp kim");

        // Act
        MaterialResponse actualMaterial = materialService.create(materialRequest);
        assertEquals(materialRequest.getName(), actualMaterial.getName()); // Kiem tra ten moi duoc cap nhat co dung khong
    }

    @Test
    void updateWhileNameExists() {
        // Arrange
        MaterialRequest materialRequest = new MaterialRequest();
        materialRequest.setId(10L);
        materialRequest.setName("Kim loại Titan");

        // Act
        assertThatThrownBy(() -> materialService.update(materialRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithIdNotExists() {
        long id = 100;
        //id này tìm kh tồn tại trả ngoại lệ
        assertThatThrownBy(() -> materialService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithReceiptExists() {
        long id = 1;
        assertThatThrownBy(() -> materialService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdSuccess() {
        long id = 6;
        materialService.deleteById(id);
        assertFalse(materialRepository.existsById(id));
    }
}