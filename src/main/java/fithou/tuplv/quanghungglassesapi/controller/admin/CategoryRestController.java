package fithou.tuplv.quanghungglassesapi.controller.admin;

import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.service.CategoryService;
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
@RequestMapping("/api/admin/category")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CategoryRestController {
    final CategoryService categoryService;

    @GetMapping({"/", ""})
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "", required = false) String name,
                                    @RequestParam(value = "page-size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = "page-number", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                    @RequestParam(value = "sort-direction", defaultValue = SORT_DESC, required = false) String sortDir,
                                    @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy) {
        pageNumber = (pageNumber <= 0) ? 0 : (pageNumber - 1); // Nếu page <= 0 thì trả về page đầu tiên
        Sort sort = sortDir.equalsIgnoreCase(SORT_DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return ResponseEntity.ok().body(categoryService.findByNameContaining(name, pageable));
    }

    @GetMapping("/{slug}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getBySlug(@PathVariable String slug) {
        try {
            return ResponseEntity.ok().body(categoryService.findBySlug(slug));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping({"/", ""})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody CategoryRequest categoryRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok().body(categoryService.create(categoryRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping({"/", ""})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody CategoryRequest categoryRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok().body(categoryService.update(categoryRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping({"/{id}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            categoryService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("/count")
//    public ResponseEntity<?> countByStatus(@RequestParam Boolean status) {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("status", status.toString());
//        map.put("totalElements", categoryService.countByStatus(status).toString());
//        return ResponseEntity.ok().body(map);
//    }
//
//    @GetMapping("/count-all")
//    public ResponseEntity<?> countAll() {
//        return ResponseEntity.ok().body(Map.of("totalElements", categoryService.countAll()));
//    }
}
