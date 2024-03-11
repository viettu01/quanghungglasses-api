package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ReceiptDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ReceiptRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ReceiptResponse;
import fithou.tuplv.quanghungglassesapi.repository.ReceiptRepository;
import fithou.tuplv.quanghungglassesapi.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ReceiptServiceTest {

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    void testFindAll() {
        long actualSuppliers = receiptService.findBySupplierNameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedSuppliers = receiptRepository.count(); // Lay tong so phan tu trong database
        assertEquals(expectedSuppliers, actualSuppliers);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("supplierName").ascending());

        // Act
        PaginationDTO<ReceiptResponse> actualSupplier = receiptService.findBySupplierNameContaining("Vanila Shop", pageable);

        // Assert
//        assertNotNull(actualSupplier); // Kiểm tra kết quả trả về khác null
        assertEquals(5, actualSupplier.getPageSize()); // Kiểm tra số lượng phần tử trên mỗi trang
        assertEquals(1, actualSupplier.getPageNumber()); // Kiểm tra trang hiện tại
        assertEquals("supplierName", actualSupplier.getSortBy()); // Kiểm tra sắp xếp theo tên
    }

    @Test
    void findByIdWithIdExists() {
        // Arrange
        long id = 8;

        // Act
        ReceiptResponse actualSupplier = receiptService.findById(id);

        // Assert
        assertEquals(id, actualSupplier.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> receiptService.findById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByNameContainingWithNotRecord() {
        // Arrange
        String name = "z";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<ReceiptResponse> actualSupplier = receiptService.findBySupplierNameContaining(name, pageable);

        // Assert
        assertEquals(0, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "v";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<ReceiptResponse> actualSupplier = receiptService.findBySupplierNameContaining(name, pageable);

        // Assert
        assertEquals(3, actualSupplier.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createWhileProductDetailsExists() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        ReceiptRequest receiptRequest = new ReceiptRequest();
        receiptRequest.setSupplierId(1L);
        receiptRequest.setStatus(true);
        List<ReceiptDetailsRequest> receiptDetailsRequests = List.of(
                new ReceiptDetailsRequest(1L, 1, 20000.0, 9L),
                new ReceiptDetailsRequest(2L, 2, 10000.0, 9L)
        );
        receiptRequest.setReceiptDetails(receiptDetailsRequests);

        // Act
        assertThatThrownBy(() -> receiptService.create(receiptRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createSuccess() {
        // Arrange
        // Xác thực từ username và password.
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        ReceiptRequest receiptRequest = new ReceiptRequest();
        receiptRequest.setSupplierId(1L);
        receiptRequest.setStatus(true);
        List<ReceiptDetailsRequest> receiptDetailsRequests = List.of(
                new ReceiptDetailsRequest(1L, 1, 20000.0, 9L),
                new ReceiptDetailsRequest(2L, 2, 10000.0, 8L)
        );
        receiptRequest.setReceiptDetails(receiptDetailsRequests);


        // Act
        ReceiptResponse actualReceipt = receiptService.create(receiptRequest);
        assertNotNull(actualReceipt.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void updateStatusSuccess() {
        // Act
        assertThatNoException().isThrownBy(() -> receiptService.updateStatus(8L, true));
    }
}