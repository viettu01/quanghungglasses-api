package fithou.tuplv.quanghungglassesapi.controller.admin;

import fithou.tuplv.quanghungglassesapi.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/report")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ReportRestController {
    final ReportService reportService;

    @GetMapping("/order")
    public ResponseEntity<?> getAllSaleReport(@RequestParam(value = "year", required = false) Integer year) {
        return ResponseEntity.ok().body(reportService.getOrderReport(year));
    }

    @GetMapping("/receipt")
    public ResponseEntity<?> getAllReceiptReport(@RequestParam(value = "year", required = false) Integer year) {
        return ResponseEntity.ok().body(reportService.getReceiptReport(year));
    }

    @GetMapping("/order/export")
    public ResponseEntity<?> exportOrderReport(@RequestParam(value = "year", required = false) Integer year) {
        return ResponseEntity.ok().body(Map.of("file", reportService.exportOrderReport(year)));
    }
}
