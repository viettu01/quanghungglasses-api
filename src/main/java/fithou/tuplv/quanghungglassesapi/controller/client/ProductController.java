package fithou.tuplv.quanghungglassesapi.controller.client;

import fithou.tuplv.quanghungglassesapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        return ResponseEntity.ok().body(productService.findByNameContainingAndStatus(name, true, pageable));
    }

    @GetMapping("/category/{category-slug}")
    public ResponseEntity<?> getByCategorySlug(@PathVariable(value = "category-slug") String categorySlug,
                                               @RequestParam(value = "origin-name", defaultValue = "", required = false) List<String> originName,
                                               @RequestParam(value = "brand-name", defaultValue = "", required = false) List<String> brandName,
                                               @RequestParam(value = "material-name", defaultValue = "", required = false) List<String> materialName,
                                               @RequestParam(value = "shape-name", defaultValue = "", required = false) List<String> shapeName,
                                               @RequestParam(value = "time-warranty", required = false) List<Integer> timeWarranty,
                                               @RequestParam(value = "price-min", required = false) Double priceMin,
                                               @RequestParam(value = "price-max", required = false) Double priceMax,
                                               @RequestParam(value = "page-size", defaultValue = DEFAULT_PAGE_SIZE, required = false) Integer pageSize,
                                               @RequestParam(value = "page-number", defaultValue = DEFAULT_PAGE_NUMBER, required = false) Integer pageNumber,
                                               @RequestParam(value = "sort-direction", defaultValue = SORT_DESC, required = false) String sortDir,
                                               @RequestParam(value = "sort-by", defaultValue = "id", required = false) String sortBy) {
        pageNumber = (pageNumber <= 0) ? 0 : (pageNumber - 1); // Nếu page <= 0 thì trả về page đầu tiên
        Sort sort = sortDir.equalsIgnoreCase(SORT_DESC) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return ResponseEntity.ok().body(productService.findByCategorySlug(
                categorySlug,
                originName,
                brandName,
                materialName,
                shapeName,
                timeWarranty,
                priceMin,
                priceMax,
                pageable));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getBySlug(@PathVariable String slug) {
        try {
            return ResponseEntity.ok().body(productService.findBySlug(slug));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getProductDetailsById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().body(productService.findProductDetailsByIdInCart(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
