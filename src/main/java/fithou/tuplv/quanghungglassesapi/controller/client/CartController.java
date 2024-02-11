package fithou.tuplv.quanghungglassesapi.controller.client;

import fithou.tuplv.quanghungglassesapi.dto.request.CartDetailsRequest;
import fithou.tuplv.quanghungglassesapi.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CartController {
    final CartService cartService;

    @GetMapping("/{email}")
    public ResponseEntity<?> getCartByUserId(@PathVariable String email) {
        return ResponseEntity.ok(cartService.getCartByUserEmail(email));
    }

    @PostMapping("/add-product-to-cart")
    public ResponseEntity<?> addProductToCart(@RequestBody CartDetailsRequest cartDetailsRequest) {
        return ResponseEntity.ok(cartService.addProductToCart(cartDetailsRequest));
    }

    @PutMapping("/update-product-quantity")
    public ResponseEntity<?> updateProductQuantity(@RequestBody CartDetailsRequest cartDetailsRequest) {
        return ResponseEntity.ok(cartService.updateProductQuantity(cartDetailsRequest));
    }

    @DeleteMapping("/delete-cart-details")
    public ResponseEntity<?> deleteCartDetails(@RequestBody Long[] cartDetailsIds) {
        cartService.deleteCartDetails(cartDetailsIds);
        return ResponseEntity.ok().build();
    }
}
