package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.response.*;

import java.util.List;

public interface ReportService {
    List<OrderReport> getOrderReport(Integer year);

    List<ReceiptReport> getReceiptReport(Integer year);

    byte[] exportOrderReport(Integer year);

    byte[] exportReceiptReport(Integer year);

    DashboardResponse getDashboard();

    List<CartReport> getAllCart();
}
