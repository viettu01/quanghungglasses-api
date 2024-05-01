package fithou.tuplv.quanghungglassesapi.controller.client;

import fithou.tuplv.quanghungglassesapi.service.ShapeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shape")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ShapeController {
    final ShapeService shapeService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(shapeService.findAll());
    }
}
