package fithou.tuplv.quanghungglassesapi.controller.client;

import fithou.tuplv.quanghungglassesapi.service.OriginService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/origin")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class OriginController {
    final OriginService originService;

    @GetMapping({"/", ""})
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().body(originService.findAll());
    }
}
