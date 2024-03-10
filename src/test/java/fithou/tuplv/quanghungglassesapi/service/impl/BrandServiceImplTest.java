package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.BrandRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BrandResponse;
import fithou.tuplv.quanghungglassesapi.repository.BrandRepository;
import fithou.tuplv.quanghungglassesapi.service.BrandService;
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
class BrandServiceImplTest {
    @Autowired
    BrandRepository brandRepository;
    @Autowired
    BrandService brandService;
    @Test
    void testFindAll() {
        long actualMaterials = brandService.findByNameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedMaterials = brandRepository.count(); // Lay tong so phan tu trong database
        assertEquals(actualMaterials, expectedMaterials);
    }
    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        // Act
        PaginationDTO<BrandResponse> actualSupplier = brandService.findByNameContaining("", pageable);

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
        BrandResponse actualBrand = brandService.findById(id);

        // Assert
        assertEquals(id, actualBrand.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> brandService.findById(id)).isInstanceOf(RuntimeException.class);
    }
    @Test
    void testFindByNameContainingWithNotRecord() {
        // Arrange
        String name = "w";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<BrandResponse> actualBrand= brandService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(0, actualBrand.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "A";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<BrandResponse> actualBrand = brandService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(5, actualBrand.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "Dior";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<BrandResponse> actualBrand = brandService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(1, actualBrand.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }
    @Test
    void createSuccess() {
        // Arrange
        BrandRequest brandRequest= new BrandRequest();
        brandRequest.setName("Lily");

        //Kiểm tra id trả về khác null hay không
        // Act
        //Sau khi Thêm dl trả về actualMaterial
        BrandResponse actualBrand = brandService.create(brandRequest);
        assertNotNull(actualBrand.getId()); // Kiểm tra kết quả trả về khác null
    }
    @Test
    void createWhileNameExists() {
        // Arrange
        BrandRequest brandRequest= new BrandRequest();
        brandRequest.setName("Dior");
        //kiểm tra có trả ra ngoại lệ hay không , nếu trả ra ngoại lệ pass
        // Act
        assertThatThrownBy(() -> brandService.create(brandRequest)).isInstanceOf(RuntimeException.class);
    }
    @Test
    void updateSuccess() {
        BrandRequest brandRequest= new BrandRequest();
        brandRequest.setId(9L);
        brandRequest.setName("Anna");

        // Act
        BrandResponse actualBrand = brandService.create(brandRequest);
        assertEquals(brandRequest.getName(), actualBrand.getName()); // Kiem tra ten moi duoc cap nhat co dung khong
    }
    @Test
    void updateWhileNameExists() {
        // Arrange
        BrandRequest brandRequest= new BrandRequest();
        brandRequest.setId(9L);
        brandRequest.setName("Dior");

        // Act
        assertThatThrownBy(() -> brandService.update(brandRequest)).isInstanceOf(RuntimeException.class);
    }
    @Test
    void deleteByIdWithIdNotExists() {
        long id = 100;
        //id này tìm kh tồn tại trả ngoại lệ
        assertThatThrownBy(() -> brandService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithReceiptExists() {
        long id = 1;
        assertThatThrownBy(() -> brandService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdSuccess() {
        long id = 9;
        brandService.deleteById(id);
        assertFalse(brandRepository.existsById(id));
    }
}