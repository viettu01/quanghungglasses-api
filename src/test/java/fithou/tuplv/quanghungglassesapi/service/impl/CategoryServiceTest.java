package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryResponse;
import fithou.tuplv.quanghungglassesapi.repository.CategoryRepository;
import fithou.tuplv.quanghungglassesapi.service.CategoryService;
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
class CategoryServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Test
    void testFindAll() {
        long actualSuppliers = categoryService.findByNameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedSuppliers = categoryRepository.count(); // Lay tong so phan tu trong database
        assertEquals(expectedSuppliers, actualSuppliers);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        // Act
        PaginationDTO<CategoryResponse> actualCategory = categoryService.findByNameContaining("", pageable);

        // Assert
//        assertNotNull(actualSupplier); // Kiểm tra kết quả trả về khác null
        assertEquals(5, actualCategory.getPageSize()); // Kiểm tra số lượng phần tử trên mỗi trang
        assertEquals(1, actualCategory.getPageNumber()); // Kiểm tra trang hiện tại
        assertEquals("name", actualCategory.getSortBy()); // Kiểm tra sắp xếp theo tên
    }

    @Test
    void findBySlugWithSlugExists() {
        // Arrange
        String slug = "kinh-thoi-trang";

        // Act
        CategoryResponse actualCategory = categoryService.findBySlug(slug);

        // Assert
        assertEquals(slug, actualCategory.getSlug()); // Kiểm tra id trả về
    }

    @Test
    void findBySlugWithSlugNotExists() {
        String slug = "a";
        assertThatThrownBy(() -> categoryService.findBySlug(slug)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByNameContainingWithNotRecord() {
        // Arrange
        String name = "z";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<CategoryResponse> actualCategory = categoryService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(0, actualCategory.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "Kính";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<CategoryResponse> actualCategory = categoryService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(3, actualCategory.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "Phụ kiện";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<CategoryResponse> actualCategory = categoryService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(1, actualCategory.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createWhileNameExists() {
        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Kính thời trang");
        categoryRequest.setSlug("aaa");

        // Act
        assertThatThrownBy(() -> categoryService.create(categoryRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createWhileSlugExists() {
        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Kính cận");
        categoryRequest.setSlug("kinh-thoi-trang");

        // Act
        assertThatThrownBy(() -> categoryService.create(categoryRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createSuccess() {
        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName("Kính cận");
        categoryRequest.setSlug("kinh-can");
        categoryRequest.setStatus(true);

        // Act
        CategoryResponse actualCategory = categoryService.create(categoryRequest);
        assertNotNull(actualCategory.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void updateWhileNameExists() {
        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(5L);
        categoryRequest.setName("Phụ kiện");
        //categoryRequest.setSlug("0998765544");

        // Act
        assertThatThrownBy(() -> categoryService.update(categoryRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateWhileSlugExists() {
        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(5L);
        //categoryRequest.setName("G – Shop");
        categoryRequest.setSlug("phu-kien");

        // Act
        assertThatThrownBy(() -> categoryService.update(categoryRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSuccess() {
        // Arrange
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setId(5L);
        categoryRequest.setName("Kính cận đổi màu");
        categoryRequest.setSlug("Kinh-can-doi-mau");
        categoryRequest.setStatus(true);

        // Act
        CategoryResponse actualCategory = categoryService.create(categoryRequest);
        assertEquals(categoryRequest.getName(), actualCategory.getName()); // Kiem tra ten moi duoc cap nhat co dung khong
        assertEquals(categoryRequest.getSlug(), actualCategory.getSlug()); // Kiem tra so dien thoai moi duoc cap nhat co dung khong
    }

    @Test
    void deleteByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> categoryService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithReceiptExists() {
        long id = 2;
        assertThatThrownBy(() -> categoryService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdSuccess() {
        long id = 5;
        categoryService.deleteById(id);
        assertFalse(categoryRepository.existsById(id));
    }
}