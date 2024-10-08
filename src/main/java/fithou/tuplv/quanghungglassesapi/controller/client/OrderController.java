package fithou.tuplv.quanghungglassesapi.controller.client;

import fithou.tuplv.quanghungglassesapi.dto.request.OrderRequest;
import fithou.tuplv.quanghungglassesapi.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    final OrderService orderService;

    @GetMapping({"/", ""})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllOrders(@RequestParam(value = "product-name", required = false) String productName,
                                          @RequestParam(value = "order-status", required = false) Integer orderStatus,
                                          @RequestParam(value = "page-size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                          @RequestParam(value = "page-number", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                          @RequestParam(value = "sort-direction", defaultValue = SORT_DESC, required = false) String sortDir,
                                          @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy) {
        pageNumber = (pageNumber <= 0) ? 0 : (pageNumber - 1); // Nếu page <= 0 thì trả về page đầu tiên
        Sort sort = sortDir.equalsIgnoreCase(SORT_DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return ResponseEntity.ok().body(orderService.findByCustomerAccountEmailAndOrderStatus(productName, orderStatus, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(orderService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> create(@Valid @ModelAttribute OrderRequest orderRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok().body(orderService.create(orderRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> update(@ModelAttribute OrderRequest orderRequest) {
        try {
            return ResponseEntity.ok().body(orderService.update(orderRequest.getId(), orderRequest.getOrderStatus(), orderRequest.getCancelReason()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
