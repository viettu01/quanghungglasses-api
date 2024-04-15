package fithou.tuplv.quanghungglassesapi.controller.admin;

import fithou.tuplv.quanghungglassesapi.dto.request.WarrantyRequest;
import fithou.tuplv.quanghungglassesapi.service.WarrantyService;
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
import java.util.Map;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin/warranty")
@CrossOrigin(origins = "http://localhost:4200")
public class WarrantyRestController {
    final WarrantyService warrantyService;

    @GetMapping({"/", ""})
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getAll(@RequestParam(value = "fullname", defaultValue = "", required = false) String fullname,
                                    @RequestParam(value = "page-size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = "page-number", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                    @RequestParam(value = "sort-direction", defaultValue = SORT_DESC, required = false) String sortDir,
                                    @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy) {
        pageNumber = (pageNumber <= 0) ? 0 : (pageNumber - 1); // Nếu page <= 0 thì trả về page đầu tiên
        Sort sort = sortDir.equalsIgnoreCase(SORT_DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return ResponseEntity.ok().body(warrantyService.findByCustomerFullname(fullname, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(warrantyService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> create(@Valid @RequestBody WarrantyRequest warrantyRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok().body(warrantyService.create(warrantyRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestParam Boolean status) {
        try {
            warrantyService.update(id, status);
            return ResponseEntity.ok().body(Map.of("message", "Cập nhật thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
