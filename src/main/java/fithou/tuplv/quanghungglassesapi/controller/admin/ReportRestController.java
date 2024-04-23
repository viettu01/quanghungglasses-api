package fithou.tuplv.quanghungglassesapi.controller.admin;

import fithou.tuplv.quanghungglassesapi.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/report")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ReportRestController {
    final ReportService reportService;

    @GetMapping("/order")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getAllSaleReport(@RequestParam(value = "year", required = false) Integer year) {
        return ResponseEntity.ok().body(reportService.getOrderReport(year));
    }

    @GetMapping("/receipt")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getAllReceiptReport(@RequestParam(value = "year", required = false) Integer year) {
        return ResponseEntity.ok().body(reportService.getReceiptReport(year));
    }

    @GetMapping("/order/export")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> exportOrderReport(@RequestParam(value = "year", required = false) Integer year) {
        return ResponseEntity.ok().body(reportService.exportOrderReport(year));
    }

    @GetMapping("/receipt/export")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> exportReceiptReport(@RequestParam(value = "year", required = false) Integer year) {
        return ResponseEntity.ok().body(reportService.exportReceiptReport(year));
    }

    @GetMapping("/get-dashboard")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> countOrderInDate() {
        try {
            return ResponseEntity.ok().body(reportService.getDashboard());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-all-cart")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getAllCart() {
        try {
            return ResponseEntity.ok().body(reportService.getAllCart());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
