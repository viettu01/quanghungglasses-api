package fithou.tuplv.quanghungglassesapi.controller.client;

import fithou.tuplv.quanghungglassesapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ProductController {
    final ProductService productService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAll(@RequestParam(value = "name", defaultValue = "", required = false) String name,
                                    @RequestParam(value = "page-size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                    @RequestParam(value = "page-number", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                    @RequestParam(value = "sort-direction", defaultValue = SORT_DESC, required = false) String sortDir,
                                    @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy) {
        pageNumber = (pageNumber <= 0) ? 0 : (pageNumber - 1); // Nếu page <= 0 thì trả về page đầu tiên
        Sort sort = sortDir.equalsIgnoreCase(SORT_DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        if (StringUtils.hasText(name))
            return ResponseEntity.ok().body(productService.findByNameContaining(name, pageable));

        return ResponseEntity.ok().body(productService.findAll(pageable));
    }

    @GetMapping("/category/{category-slug}")
    public ResponseEntity<?> getByCategorySlug(@PathVariable(value = "category-slug") String categorySlug,
                                               @RequestParam(value = "page-size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                               @RequestParam(value = "page-number", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                               @RequestParam(value = "sort-direction", defaultValue = SORT_DESC, required = false) String sortDir,
                                               @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy) {
        pageNumber = (pageNumber <= 0) ? 0 : (pageNumber - 1); // Nếu page <= 0 thì trả về page đầu tiên
        Sort sort = sortDir.equalsIgnoreCase(SORT_DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok().body(productService.findByCategorySlug(categorySlug, pageable));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getBySlug(@PathVariable String slug) {
        try {
            return ResponseEntity.ok().body(productService.findBySlug(slug));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
