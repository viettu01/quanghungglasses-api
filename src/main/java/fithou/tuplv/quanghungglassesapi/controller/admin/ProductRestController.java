package fithou.tuplv.quanghungglassesapi.controller.admin;

import fithou.tuplv.quanghungglassesapi.dto.request.ProductRequest;
import fithou.tuplv.quanghungglassesapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@RestController
@RequestMapping("/api/admin/product")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ProductRestController {
    final ProductService productService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "", required = false) String name,
                                    @RequestParam(value = "status", defaultValue = "", required = false) Boolean status,
                                    @RequestParam(value = "page-size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = "page-number", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                    @RequestParam(value = "sort-direction", defaultValue = SORT_DESC, required = false) String sortDir,
                                    @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy) {
        pageNumber = (pageNumber <= 0) ? 0 : (pageNumber - 1); // Nếu page <= 0 thì trả về page đầu tiên
        Sort sort = sortDir.equalsIgnoreCase(SORT_DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        if (status != null)
            return ResponseEntity.ok().body(productService.findByNameContainingAndStatus(name, status, pageable));

        if (StringUtils.hasText(name))
            return ResponseEntity.ok().body(productService.findByNameContaining(name, pageable));

        return ResponseEntity.ok().body(productService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(productService.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping({"/", ""})
    public ResponseEntity<?> create(@Valid @ModelAttribute ProductRequest productRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            return ResponseEntity.ok().body(productService.create(productRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping({"/", ""})
    public ResponseEntity<?> update(@Valid @ModelAttribute ProductRequest productRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            return ResponseEntity.ok().body(productService.update(productRequest));
        } catch (Exception e) {
//            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> countByStatus(@RequestParam Boolean status) {
        HashMap<String, String> map = new HashMap<>();
        map.put("status", status.toString());
        map.put("totalElements", productService.countByStatus(status).toString());
        return ResponseEntity.ok().body(map);
    }

    @GetMapping("/count-all")
    public ResponseEntity<?> countAll() {
        return ResponseEntity.ok().body(Map.of("totalElements", productService.countAll()));
    }

//    @DeleteMapping({"/{id}"})
//    public ResponseEntity<?> delete(@PathVariable Long id) {
//        try {
//            productService.deleteById(id);
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @DeleteMapping("/{id}/images/**")
    public ResponseEntity<?> deleteImage(HttpServletRequest request, @PathVariable Long id) {
        String path = request.getRequestURI().substring(request.getContextPath().length() + "/api/admin/product/".length() + id.toString().length() + "/images/".length());
        try {
            productService.deleteImageById(id, path);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
