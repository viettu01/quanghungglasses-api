package fithou.tuplv.quanghungglassesapi.controller.client;

import fithou.tuplv.quanghungglassesapi.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CategoryController {
    final CategoryService categoryService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(categoryService.findAllCategoryAndProduct(true));
    }
}
