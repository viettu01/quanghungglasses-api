package fithou.tuplv.quanghungglassesapi.controller.client;

import fithou.tuplv.quanghungglassesapi.service.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brand")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class BrandController {
    final BrandService brandService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(brandService.findAll());
    }
}
