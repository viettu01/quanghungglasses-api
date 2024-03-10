package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.OriginRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OriginResponse;
import fithou.tuplv.quanghungglassesapi.repository.OriginRepository;
import fithou.tuplv.quanghungglassesapi.service.OriginService;
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
class OriginServiceImplTest {
    @Autowired
    OriginRepository originRepository;
    @Autowired
    OriginService originService;

    @Test
    void testFindAll() {
        long actualMaterials = originService.findByNameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedMaterials = originRepository.count(); // Lay tong so phan tu trong database
        assertEquals(actualMaterials, expectedMaterials);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        // Act
        PaginationDTO<OriginResponse> actualOrigin = originService.findByNameContaining("", pageable);

        // Assert
//        assertNotNull(actualSupplier); // Kiểm tra kết quả trả về khác null
        assertEquals(5, actualOrigin.getPageSize()); // Kiểm tra số lượng phần tử trên mỗi trang
        assertEquals(1, actualOrigin.getPageNumber()); // Kiểm tra trang hiện tại
        assertEquals("name", actualOrigin.getSortBy()); // Kiểm tra sắp xếp theo tên
    }

    @Test
    void findByIdWithIdExists() {
        // Arrange
        long id = 1;

        // Act
        OriginResponse actualOrigin = originService.findById(id);

        // Assert
        assertEquals(id, actualOrigin.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> originService.findById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByNameContainingWithNotRecord() {
        // Arrange
        String name = "w";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<OriginResponse> actualOrigin = originService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(0, actualOrigin.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "A";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<OriginResponse> actualOrigin = originService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(4, actualOrigin.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "Đức";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<OriginResponse> actualOrigin = originService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(1, actualOrigin.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createSuccess() {
        // Arrange
        OriginRequest originRequest = new OriginRequest();
        originRequest.setName("Italy");

        //Kiểm tra id trả về khác null hay không
        // Act
        //Sau khi Thêm dl trả về actualMaterial
        OriginResponse actualOrigin = originService.create(originRequest);
        assertNotNull(actualOrigin.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createWhileNameExists() {
        // Arrange
        OriginRequest originRequest = new OriginRequest();
        originRequest.setName("Việt Nam");
        //kiểm tra có trả ra ngoại lệ hay không , nếu trả ra ngoại lệ pass
        // Act
        assertThatThrownBy(() -> originService.create(originRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSuccess() {
        OriginRequest originRequest = new OriginRequest();
        originRequest.setId(9L);
        originRequest.setName("HongKong");

        // Act
        OriginResponse actualOrigin = originService.create(originRequest);
        assertEquals(originRequest.getName(), actualOrigin.getName()); // Kiem tra ten moi duoc cap nhat co dung khong
    }

    @Test
    void updateWhileNameExists() {
        // Arrange
        OriginRequest originRequest = new OriginRequest();
        originRequest.setId(9L);
        originRequest.setName("Nhật Bản");

        // Act
        assertThatThrownBy(() -> originService.update(originRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithIdNotExists() {
        long id = 100;
        //id này tìm kh tồn tại trả ngoại lệ
        assertThatThrownBy(() -> originService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithReceiptExists() {
        long id = 4;
        assertThatThrownBy(() -> originService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdSuccess() {
        long id = 9;
        originService.deleteById(id);
        assertFalse(originRepository.existsById(id));
    }
}