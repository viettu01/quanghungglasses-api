package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.SaleDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.SaleRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.SaleResponse;
import fithou.tuplv.quanghungglassesapi.repository.SaleRepository;
import fithou.tuplv.quanghungglassesapi.service.SaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class SaleServiceImplTest {
    @Autowired
    SaleRepository saleRepository;

    @Autowired
    SaleService saleService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    void testFindAll() {
        long actualMaterials = saleService.findByNameContaining("", PageRequest.of(0, 10)).getTotalElements(); // Lay tong so phan tu
        long expectedMaterials = saleRepository.count(); // Lay tong so phan tu trong database
        assertEquals(actualMaterials, expectedMaterials);
    }

    @Test
    void testFindAllWithPaginate() {
        // Arrange
        // Lấy 5 phần tử trên trang 1, sắp xếp theo tên tăng dần
        Pageable pageable = PageRequest.of(0, 5, Sort.by("name").ascending());

        // Act
        PaginationDTO<SaleResponse> actualSale = saleService.findByNameContaining("", pageable);

        // Assert
        assertEquals(5, actualSale.getPageSize()); // Kiểm tra số lượng phần tử trên mỗi trang
        assertEquals(1, actualSale.getPageNumber()); // Kiểm tra trang hiện tại
        assertEquals("name", actualSale.getSortBy()); // Kiểm tra sắp xếp theo tên
    }

    @Test
    void findByIdWithIdExists() {
        // Arrange
        long id = 4;

        // Act
        SaleResponse actualSale = saleService.findById(id);

        // Assert
        assertEquals(id, actualSale.getId()); // Kiểm tra id trả về
    }

    @Test
    void findByIdWithIdNotExists() {
        long id = 100;
        assertThatThrownBy(() -> saleService.findById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void testFindByNameContainingWithNotRecord() {
        // Arrange
        String name = "z";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<SaleResponse> actualMaterial = saleService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(0, actualMaterial.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithRecord() {
        // Arrange
        String name = "sale";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<SaleResponse> actualSale = saleService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(3, actualSale.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void testFindByNameContainingWithOneRecord() {
        // Arrange
        String name = "G – Shop 2";
        Pageable pageable = PageRequest.of(0, 10);

        // Act
        PaginationDTO<SaleResponse> actualMaterial = saleService.findByNameContaining(name, pageable);

        // Assert
        assertEquals(1, actualMaterial.getNumberOfElements()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createSuccess() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setName("Sale 15/03");
        saleRequest.setStartDate(new Date("2024/03/15"));
        saleRequest.setEndDate(new Date("2024/03/15"));
        List<SaleDetailsRequest> saleDetailsRequests = List.of(
                new SaleDetailsRequest(null, 20f, 1L),
                new SaleDetailsRequest(null, 20f, 2L)
        );
        saleRequest.setSaleDetails(saleDetailsRequests);

        //Kiểm tra id trả về khác null hay không
        // Act
        SaleResponse actualSale = saleService.create(saleRequest);
        assertNotNull(actualSale.getId()); // Kiểm tra kết quả trả về khác null
    }

    @Test
    void createWhileNameExists() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setName("Sale 15/03");
        saleRequest.setStartDate(new Date("2024/03/15"));
        saleRequest.setEndDate(new Date("2024/03/15"));
        List<SaleDetailsRequest> saleDetailsRequests = List.of(
                new SaleDetailsRequest(null, 20f, 1L),
                new SaleDetailsRequest(null, 20f, 2L)
        );
        saleRequest.setSaleDetails(saleDetailsRequests);

        assertThatThrownBy(() -> saleService.create(saleRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createWhileStartDateAfterEndDate() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setName("Sale 16/03");
        saleRequest.setStartDate(new Date("2024/03/16"));
        saleRequest.setEndDate(new Date("2024/03/15"));
        List<SaleDetailsRequest> saleDetailsRequests = List.of(
                new SaleDetailsRequest(null, 20f, 1L),
                new SaleDetailsRequest(null, 20f, 2L)
        );
        saleRequest.setSaleDetails(saleDetailsRequests);

        assertThatThrownBy(() -> saleService.create(saleRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createWhileProductExistsInOtherSale() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setName("Sale 16/03");
        saleRequest.setStartDate(new Date("2024/03/15"));
        saleRequest.setEndDate(new Date("2024/03/19"));
        List<SaleDetailsRequest> saleDetailsRequests = List.of(
                new SaleDetailsRequest(null, 20f, 1L),
                new SaleDetailsRequest(null, 20f, 2L)
        );
        saleRequest.setSaleDetails(saleDetailsRequests);

        assertThatThrownBy(() -> saleService.create(saleRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateSuccess() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setId(8L);
        saleRequest.setName("Sale 16/03");
        saleRequest.setStartDate(new Date("2024/03/16"));
        saleRequest.setEndDate(new Date("2024/03/16"));

        //Kiểm tra id trả về khác null hay không
        // Act
        SaleResponse actualSale = saleService.update(saleRequest);
        assertEquals(8, actualSale.getId());
        assertEquals("Sale 16/03", actualSale.getName());
        assertEquals(new Date("2024/03/16 00:00:00"), actualSale.getStartDate());
        assertEquals(new Date("2024/03/16 23:59:59"), actualSale.getEndDate());
    }

    @Test
    void updateWhileNameExists() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setId(8L);
        saleRequest.setName("sale 8/3");
        saleRequest.setStartDate(new Date("2024/03/15"));
        saleRequest.setEndDate(new Date("2024/03/15"));
        List<SaleDetailsRequest> saleDetailsRequests = List.of(
                new SaleDetailsRequest(null, 20f, 1L),
                new SaleDetailsRequest(null, 20f, 2L)
        );
        saleRequest.setSaleDetails(saleDetailsRequests);

        assertThatThrownBy(() -> saleService.update(saleRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateWhileStartDateAfterEndDate() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setId(8L);
        saleRequest.setName("Sale 16/03");
        saleRequest.setStartDate(new Date("2024/03/16"));
        saleRequest.setEndDate(new Date("2024/03/15"));
        List<SaleDetailsRequest> saleDetailsRequests = List.of(
                new SaleDetailsRequest(null, 20f, 1L),
                new SaleDetailsRequest(null, 20f, 2L)
        );
        saleRequest.setSaleDetails(saleDetailsRequests);

        assertThatThrownBy(() -> saleService.update(saleRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateWhileProductExistsInOtherSale() {
        // Arrange
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("adminquanghung@gmail.com", "1234")
        ));

        SaleRequest saleRequest = new SaleRequest();
        saleRequest.setId(6L);
        saleRequest.setName("Sale 16/03");
        saleRequest.setStartDate(new Date("2024/03/07"));
        saleRequest.setEndDate(new Date("2024/03/09"));
        List<SaleDetailsRequest> saleDetailsRequests = List.of(
                new SaleDetailsRequest(null, 20f, 6L)
        );
        saleRequest.setSaleDetails(saleDetailsRequests);

        assertThatThrownBy(() -> saleService.update(saleRequest)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdWithIdNotExists() {
        long id = 100;
        //id này tìm kh tồn tại trả ngoại lệ
        assertThatThrownBy(() -> saleService.deleteById(id)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteByIdSuccess() {
        long id = 5;
        saleService.deleteById(id);
        assertFalse(saleRepository.existsById(id));
    }
}