package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ShapeRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ShapeResponse;
import fithou.tuplv.quanghungglassesapi.repository.ShapeRepository;
import fithou.tuplv.quanghungglassesapi.service.ShapeService;
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
class ShapeServiceImplTest {
    @Autowired
    ShapeRepository shapeRepository;
    @Autowired
    ShapeService shapeService;

    @Test
    void testFindAll() {
        long actualMaterials = shapeService.findByNameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedMaterials = shapeRepository.count(); // Lay tong so phan tu trong database
        assertEquals(actualMaterials, expectedMaterials);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        // Act
        PaginationDTO<ShapeResponse> actualSupplier = shapeService.findByNameContaining("", pageable);

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
        ShapeResponse actualMaterial = shapeService.findById(id);

        // Assert
        assertEquals(id, actualMaterial.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> shapeService.findById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByNameContainingWithNotRecord() {
        // Arrange
        String name = "z";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<ShapeResponse> actualMaterial = shapeService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(0, actualMaterial.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "Vuông";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<ShapeResponse> actualMaterial = shapeService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(3, actualMaterial.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "Mắt mèo";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<ShapeResponse> actualMaterial = shapeService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(1, actualMaterial.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createSuccess() {
        // Arrange
        ShapeRequest shapeRequest = new ShapeRequest();
        shapeRequest.setName("Đa giác đều");

        //Kiểm tra id trả về khác null hay không
        // Act
        //Sau khi Thêm dl trả về actualMaterial
        ShapeResponse actualMaterial = shapeService.create(shapeRequest);
        assertNotNull(actualMaterial.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createWhileNameExists() {
        // Arrange
        ShapeRequest shapeRequest = new ShapeRequest();
        shapeRequest.setName("Mắt mèo");
        //kiểm tra có trả ra ngoại lệ hay không , nếu trả ra ngoại lệ pass
        // Act
        assertThatThrownBy(() -> shapeService.create(shapeRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSuccess() {
        ShapeRequest shapeRequest = new ShapeRequest();
        shapeRequest.setId(10L);
        shapeRequest.setName("Đa giác vuông");

        // Act
        ShapeResponse actualMaterial = shapeService.create(shapeRequest);
        assertEquals(shapeRequest.getName(), actualMaterial.getName()); // Kiem tra ten moi duoc cap nhat co dung khong
    }

    @Test
    void updateWhileNameExists() {
        // Arrange
        ShapeRequest shapeRequest = new ShapeRequest();
        shapeRequest.setId(10L);
        shapeRequest.setName("Tròn tròn");

        // Act
        assertThatThrownBy(() -> shapeService.update(shapeRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithIdNotExists() {
        long id = 100;
        //id này tìm kh tồn tại trả ngoại lệ
        assertThatThrownBy(() -> shapeService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithReceiptExists() {
        long id = 2;
        assertThatThrownBy(() -> shapeService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdSuccess() {
        long id = 10;
        shapeService.deleteById(id);
        assertFalse(shapeRepository.existsById(id));
    }
}