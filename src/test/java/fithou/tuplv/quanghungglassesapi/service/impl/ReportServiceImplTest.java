package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.response.CartReport;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderReport;
import fithou.tuplv.quanghungglassesapi.dto.response.ReceiptReport;
import fithou.tuplv.quanghungglassesapi.repository.CartDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.OrderRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.ReceiptRepository;
import fithou.tuplv.quanghungglassesapi.service.ReportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ReportServiceImplTest {
    @Autowired
    ReportService reportService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    CartDetailsRepository cartDetailsRepository;

    @Autowired
    ProductDetailsRepository productDetailsRepository;

    @Test
    public void testGetOrderStatusSuccess() {
        Calendar from = Calendar.getInstance();
        from.set(2024, Calendar.JANUARY, 1);
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        Calendar to = Calendar.getInstance();
        to.set(2024, Calendar.DECEMBER, 31);
        to.set(Calendar.HOUR_OF_DAY, 23);
        to.set(Calendar.MINUTE, 59);
        to.set(Calendar.SECOND, 59);
        assertEquals(2, orderRepository.findAllByCompletedDateBetweenAndOrderStatus(from.getTime(), to.getTime(), 5).size());
    }

    @Test
    public void testGetTotalProductInMonthSuccess() {
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(1, 0);
        expected.put(2, 0);
        expected.put(3, 3);
        expected.put(4, 4);
        expected.put(5, 0);
        expected.put(6, 0);
        expected.put(7, 0);
        expected.put(8, 0);
        expected.put(9, 0);
        expected.put(10, 0);
        expected.put(11, 0);
        expected.put(12, 0);
        List<OrderReport> orderReports = reportService.getOrderReport(2024);
        for (OrderReport orderReport : orderReports) {
            for (Integer key : expected.keySet()) {
                if (orderReport.getMonth().equals(key)) {
                    assertEquals(expected.get(key), orderReport.getTotalQuantityOrder());
                }
            }
        }
    }

    @Test
    public void testGetTotalMoneyInMonthSuccess() {
        HashMap<Integer, Double> expected = new HashMap<>();
        expected.put(1, 0.0);
        expected.put(2, 0.0);
        expected.put(3, 300000.0);
        expected.put(4, 440000.0);
        expected.put(5, 0.0);
        expected.put(6, 0.0);
        expected.put(7, 0.0);
        expected.put(8, 0.0);
        expected.put(9, 0.0);
        expected.put(10, 0.0);
        expected.put(11, 0.0);
        expected.put(12, 0.0);
        List<OrderReport> orderReports = reportService.getOrderReport(2024);
        for (OrderReport orderReport : orderReports) {
            for (Integer key : expected.keySet()) {
                if (orderReport.getMonth().equals(key)) {
                    assertEquals(expected.get(key), orderReport.getTotalMoneyOrder());
                }
            }
        }
    }

    @Test
    public void testExportOrderReportSuccess() {
        assertThatNoException().isThrownBy(() -> reportService.exportOrderReport(2024));
    }

    @Test
    public void testGetReceiptStatusSuccess() {
        Calendar from = Calendar.getInstance();
        from.set(2023, Calendar.JANUARY, 1);
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        Calendar to = Calendar.getInstance();
        to.set(2023, Calendar.DECEMBER, 31);
        to.set(Calendar.HOUR_OF_DAY, 23);
        to.set(Calendar.MINUTE, 59);
        to.set(Calendar.SECOND, 59);
        assertEquals(1, receiptRepository.findAllByUpdatedDateBetweenAndStatus(from.getTime(), to.getTime(), true).size());
    }

    @Test
    public void testGetTotalProductReceiptInMonthSuccess() {
        HashMap<Integer, Integer> expected = new HashMap<>();
        expected.put(1, 0);
        expected.put(2, 0);
        expected.put(3, 0);
        expected.put(4, 27);
        expected.put(5, 0);
        expected.put(6, 0);
        expected.put(7, 0);
        expected.put(8, 0);
        expected.put(9, 0);
        expected.put(10, 0);
        expected.put(11, 0);
        expected.put(12, 0);
        List<ReceiptReport> receiptReports = reportService.getReceiptReport(2023);
        for (ReceiptReport receiptReport : receiptReports) {
            for (Integer key : expected.keySet()) {
                if (receiptReport.getMonth().equals(key)) {
                    assertEquals(expected.get(key), receiptReport.getTotalQuantityReceipt());
                }
            }
        }
    }

    @Test
    public void testGetTotalMoneyReceiptInMonthSuccess() {
        HashMap<Integer, Double> expected = new HashMap<>();
        expected.put(1, 0.0);
        expected.put(2, 0.0);
        expected.put(3, 0.0);
        expected.put(4, 12500000.0);
        expected.put(5, 0.0);
        expected.put(6, 0.0);
        expected.put(7, 0.0);
        expected.put(8, 0.0);
        expected.put(9, 0.0);
        expected.put(10, 0.0);
        expected.put(11, 0.0);
        expected.put(12, 0.0);
        List<ReceiptReport> receiptReport = reportService.getReceiptReport(2023);
        for (ReceiptReport orderReport : receiptReport) {
            for (Integer key : expected.keySet()) {
                if (orderReport.getMonth().equals(key)) {
                    assertEquals(expected.get(key), orderReport.getTotalMoneyReceipt());
                }
            }
        }
    }

    @Test
    public void testExportReceiptReportSuccess() {
        assertThatNoException().isThrownBy(() -> reportService.exportReceiptReport(2023));
    }

    @Test
    public void testGetAllCartDetailSuccess() {
        assertNotNull(cartDetailsRepository.findAll());
    }

    @Test
    public void testCountOrderInDaySuccess() {
        Calendar from = Calendar.getInstance();
        from.set(Calendar.HOUR_OF_DAY, 0);
        from.set(Calendar.MINUTE, 0);
        from.set(Calendar.SECOND, 0);
        Calendar to = Calendar.getInstance();
        to.set(Calendar.HOUR_OF_DAY, 23);
        to.set(Calendar.MINUTE, 59);
        to.set(Calendar.SECOND, 59);
        assertEquals(0, orderRepository.findAllByCreatedDateBetween(from.getTime(), to.getTime()).size());
    }

    @Test
    public void testCountOrderWithStatusOnHold() {
        assertEquals(1, orderRepository.findAll().stream().filter(order -> order.getOrderStatus() == 0).count());
    }

    @Test
    public void testCountProductInStock() {
        assertEquals(28, productDetailsRepository.findAll().stream().filter(productDetails -> productDetails.getQuantity() == 0).count());
    }

    @Test
    public void testTotalQuantityProductInCart() {
        List<CartReport> cartReports = reportService.getAllCart();
        int expected = 19;
        for (CartReport cartReport : cartReports) {
            if (cartReport.getProductDetailsId() == 8)
                assertEquals(expected, cartReport.getTotalQuantityInCart());
        }
    }
}
