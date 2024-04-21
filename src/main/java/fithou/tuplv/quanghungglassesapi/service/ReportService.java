package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.response.OrderReport;

import java.util.List;

public interface ReportService {
    List<OrderReport> getOrderReport(Integer year);

    List<?> getReceiptReport(Integer year);
}
