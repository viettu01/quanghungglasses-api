package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductResponse;
import fithou.tuplv.quanghungglassesapi.repository.ProductRepository;
import fithou.tuplv.quanghungglassesapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Test
    void testFindAll() {
        long actualSuppliers = productService.findByNameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedSuppliers = productRepository.count(); // Lay tong so phan tu trong database
        assertEquals(expectedSuppliers, actualSuppliers);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        // Act
        PaginationDTO<ProductResponse> actualSupplier = productService.findByNameContaining("", pageable);

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
        ProductResponse actualSupplier = productService.findById(id);

        // Assert
        assertEquals(id, actualSupplier.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> productService.findById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByNameContainingWithNotRecord() {
        // Arrange
        String name = "z";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<ProductResponse> actualSupplier = productService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(0, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "test";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<ProductResponse> actualSupplier = productService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(4, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "test14";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<ProductResponse> actualSupplier = productService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(1, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createWhileNameExists() throws IOException {
        // Arrange
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("test11");
        productRequest.setSlug("test11");
        productRequest.setPrice(100000.0);
        productRequest.setCategoryId(1L);
        productRequest.setOriginId(1L);
        productRequest.setBrandId(1L);
        productRequest.setShapeId(1L);
        productRequest.setMaterialId(1L);
        productRequest.setTimeWarranty(12);
        productRequest.setStatus(true);
        productRequest.setDescription("test11");

        List<ProductDetailsRequest> productDetailsRequests = List.of(
                new ProductDetailsRequest(1L, "color 1", 1),
                new ProductDetailsRequest(2L, "color 2", 1)
        );
        productRequest.setProductDetails(productDetailsRequests);

        // Khoi tao file anh voi duong dan trong thu muc resources
        MultipartFile multipartFile = new MockMultipartFile(
                "test.jpg",
                "test.jpg",
                "image/jpeg",
                Files.readAllBytes(new File("src/main/resources/static/images/anh1.jpeg").toPath())
        );
        productRequest.setThumbnailFile(multipartFile);

        // Act
        assertThatThrownBy(() -> productService.create(productRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createWhileSlugExists() throws IOException {
        // Arrange
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("test1");
        productRequest.setSlug("test11");
        productRequest.setPrice(100000.0);
        productRequest.setCategoryId(1L);
        productRequest.setOriginId(1L);
        productRequest.setBrandId(1L);
        productRequest.setShapeId(1L);
        productRequest.setMaterialId(1L);
        productRequest.setTimeWarranty(12);
        productRequest.setStatus(true);
        productRequest.setDescription("test11");

        List<ProductDetailsRequest> productDetailsRequests = List.of(
                new ProductDetailsRequest(1L, "color 1", 1),
                new ProductDetailsRequest(2L, "color 2", 1)
        );
        productRequest.setProductDetails(productDetailsRequests);

        // Khoi tao file anh voi duong dan trong thu muc resources
        MultipartFile multipartFile = new MockMultipartFile(
                "test.jpg",
                "test.jpg",
                "image/jpeg",
                Files.readAllBytes(new File("src/main/resources/static/images/anh1.jpeg").toPath())
        );
        productRequest.setThumbnailFile(multipartFile);

        // Act
        assertThatThrownBy(() -> productService.create(productRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createSuccess() throws IOException {
        // Arrange
        for (int i = 1; i <= 20; i++) {
            ProductRequest productRequest = new ProductRequest();
            productRequest.setName("Gọng kính " + i);
            productRequest.setSlug("gong-kinh-" + i);
            productRequest.setPrice(100000.0 * i);
            if (i < 5) {
                productRequest.setCategoryId(1L);
                productRequest.setOriginId(1L);
                productRequest.setBrandId(1L);
                productRequest.setShapeId(1L);
                productRequest.setMaterialId(1L);
            } else if (i < 10) {
                productRequest.setCategoryId(2L);
                productRequest.setOriginId(2L);
                productRequest.setBrandId(2L);
                productRequest.setShapeId(2L);
                productRequest.setMaterialId(2L);
            } else if (i < 15) {
                productRequest.setCategoryId(3L);
                productRequest.setOriginId(3L);
                productRequest.setBrandId(3L);
                productRequest.setShapeId(3L);
                productRequest.setMaterialId(3L);
            } else {
                productRequest.setCategoryId(4L);
                productRequest.setOriginId(4L);
                productRequest.setBrandId(4L);
                productRequest.setShapeId(4L);
                productRequest.setMaterialId(4L);
            }

            productRequest.setTimeWarranty(12);
            productRequest.setStatus(true);
            productRequest.setDescription("Gọng kính " + i);

            List<ProductDetailsRequest> productDetailsRequests = List.of(
                    new ProductDetailsRequest(null, "Màu 1", 1),
                    new ProductDetailsRequest(null, "Màu 2", 1)
            );
            productRequest.setProductDetails(productDetailsRequests);

            // Khoi tao file anh voi duong dan trong thu muc resources
            MultipartFile multipartFile = new MockMultipartFile(
                    "test.jpeg",
                    "test.jpeg",
                    "image/jpeg",
                    Files.readAllBytes(new File("src/main/resources/static/images/anh1.jpeg").toPath())
            );
            productRequest.setThumbnailFile(multipartFile);
            productRequest.setImageFiles(List.of(
                    new MockMultipartFile(
                            "test.jpeg",
                            "test.jpeg",
                            "image/jpeg",
                            Files.readAllBytes(new File("src/main/resources/static/images/anh1.jpeg").toPath())
                    ),
                    new MockMultipartFile(
                            "test.jpeg",
                            "test.jpeg",
                            "image/jpeg",
                            Files.readAllBytes(new File("src/main/resources/static/images/anh2.jpeg").toPath())
                    )
            ));

            ProductResponse actualProduct = productService.create(productRequest);

            assertNotNull(actualProduct.getId()); // Kiểm tra kết quả trả về khác null
        }
    }

    @Test
    void updateWhileNameExists() throws IOException {
        // Arrange
        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(10L);
        productRequest.setName("test11");
        productRequest.setSlug("test11");
        productRequest.setPrice(200000.0);
        productRequest.setCategoryId(2L);
        productRequest.setOriginId(2L);
        productRequest.setBrandId(2L);
        productRequest.setShapeId(2L);
        productRequest.setMaterialId(2L);
        productRequest.setTimeWarranty(15);
        productRequest.setStatus(true);
        productRequest.setDescription("test11");

        List<ProductDetailsRequest> productDetailsRequests = List.of(
                new ProductDetailsRequest(1L, "color 3", 1),
                new ProductDetailsRequest(2L, "color 4", 1)
        );
        productRequest.setProductDetails(productDetailsRequests);

        // Khoi tao file anh voi duong dan trong thu muc resources
        MultipartFile multipartFile = new MockMultipartFile(
                "test.jpg",
                "test.jpg",
                "image/jpeg",
                Files.readAllBytes(new File("src/main/resources/static/images/anh1.jpeg").toPath())
        );
        productRequest.setThumbnailFile(multipartFile);
        productRequest.setImageFiles(List.of(multipartFile));

        assertThatThrownBy(() -> productService.update(productRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateWhileSlugExists() throws IOException {
        // Arrange
        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(8L);
        productRequest.setName("test2");
        productRequest.setSlug("test11");
        productRequest.setPrice(200000.0);
        productRequest.setCategoryId(2L);
        productRequest.setOriginId(2L);
        productRequest.setBrandId(2L);
        productRequest.setShapeId(2L);
        productRequest.setMaterialId(2L);
        productRequest.setTimeWarranty(15);
        productRequest.setStatus(true);
        productRequest.setDescription("test11");

        List<ProductDetailsRequest> productDetailsRequests = List.of(
                new ProductDetailsRequest(1L, "color 3", 1),
                new ProductDetailsRequest(2L, "color 4", 1)
        );
        productRequest.setProductDetails(productDetailsRequests);

        // Khoi tao file anh voi duong dan trong thu muc resources
        MultipartFile multipartFile = new MockMultipartFile(
                "test.jpg",
                "test.jpg",
                "image/jpeg",
                Files.readAllBytes(new File("src/main/resources/static/images/anh1.jpeg").toPath())
        );
        productRequest.setThumbnailFile(multipartFile);
        productRequest.setImageFiles(List.of(multipartFile));

        assertThatThrownBy(() -> productService.update(productRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSuccess() throws IOException {
        // Arrange
        ProductRequest productRequest = new ProductRequest();
        productRequest.setId(8L);
        productRequest.setName("test2");
        productRequest.setSlug("test2");
        productRequest.setPrice(200000.0);
        productRequest.setCategoryId(2L);
        productRequest.setOriginId(2L);
        productRequest.setBrandId(2L);
        productRequest.setShapeId(2L);
        productRequest.setMaterialId(2L);
        productRequest.setTimeWarranty(15);
        productRequest.setStatus(true);
        productRequest.setDescription("test2");

        List<ProductDetailsRequest> productDetailsRequests = List.of(
                new ProductDetailsRequest(1L, "color 3", 1),
                new ProductDetailsRequest(2L, "color 4", 1)
        );
        productRequest.setProductDetails(productDetailsRequests);

        // Khoi tao file anh voi duong dan trong thu muc resources
        MultipartFile multipartFile = new MockMultipartFile(
                "test.jpg",
                "test.jpg",
                "image/jpeg",
                Files.readAllBytes(new File("src/main/resources/static/images/anh1.jpeg").toPath())
        );
        productRequest.setThumbnailFile(multipartFile);
        productRequest.setImageFiles(List.of(multipartFile));

        ProductResponse actualProduct = productService.update(productRequest);

        assertEquals(productRequest.getName(), actualProduct.getName()); // Kiểm tra tên mới được cập nhật có đúng không
        assertEquals(productRequest.getSlug(), actualProduct.getSlug()); // Kiểm tra slug mới được cập nhật có đúng không
        assertEquals(productRequest.getPrice(), actualProduct.getPrice()); // Kiểm tra giá mới được cập nhật có đúng không
        assertEquals(productRequest.getCategoryId(), actualProduct.getCategoryId()); // Kiểm tra danh mục mới được cập nhật có đúng không
        assertEquals(productRequest.getOriginId(), actualProduct.getOriginId()); // Kiểm tra xuất xứ mới được cập nhật có đúng không
        assertEquals(productRequest.getBrandId(), actualProduct.getBrandId()); // Kiểm tra thương hiệu mới được cập nhật có đúng không
        assertEquals(productRequest.getShapeId(), actualProduct.getShapeId()); // Kiểm tra hình dạng mới được cập nhật có đúng không
        assertEquals(productRequest.getMaterialId(), actualProduct.getMaterialId()); // Kiểm tra chất liệu mới được cập nhật có đúng không
        assertEquals(productRequest.getTimeWarranty(), actualProduct.getTimeWarranty()); // Kiểm tra thời gian bảo hành mới được cập nhật có đúng không
        assertEquals(productRequest.getStatus(), actualProduct.isStatus()); // Kiểm tra trạng thái mới được cập nhật có đúng không
        assertEquals(productRequest.getDescription(), actualProduct.getDescription()); // Kiểm tra mô tả mới được cập nhật có đúng không
        productRequest.getProductDetails().forEach(productDetailsRequest -> {
            actualProduct.getProductDetails().forEach(productDetailsResponse -> {
                if (productDetailsRequest.getColor().equals(productDetailsResponse.getColor())) {
                    assertEquals(productDetailsRequest.getQuantity(), productDetailsResponse.getQuantity()); // Kiểm tra số lượng mới được cập nhật có đúng không
                }
            });
        });
    }

    @Test
    void deleteByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> productService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithReceiptExists() {
        long id = 9;
        assertThatThrownBy(() -> productService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdSuccess() {
        long id = 10;
        productService.deleteById(id);
        assertFalse(productRepository.existsById(id));
    }
}