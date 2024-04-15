package fithou.tuplv.quanghungglassesapi.controller.admin;

import fithou.tuplv.quanghungglassesapi.dto.request.CustomerRequest;
import fithou.tuplv.quanghungglassesapi.service.CustomerService;
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
@RequestMapping("/api/admin/customer")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CustomerRestController {
    final CustomerService customerService;

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

        return ResponseEntity.ok().body(customerService.findByFullnameContaining(fullname, pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(customerService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping({"/", ""})
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> create(@Valid @ModelAttribute CustomerRequest customerRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok().body(customerService.create(customerRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping({"/", ""})
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> update(@Valid @ModelAttribute CustomerRequest customerRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok().body(customerService.update(customerRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            customerService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
