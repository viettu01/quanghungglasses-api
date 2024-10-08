package fithou.tuplv.quanghungglassesapi.controller.client;

import fithou.tuplv.quanghungglassesapi.dto.request.CartDetailsRequest;
import fithou.tuplv.quanghungglassesapi.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CartController {
    final CartService cartService;

    @GetMapping({"/", ""})
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCartByUserId() {
        return ResponseEntity.ok(cartService.getCartByUserEmail());
    }

    @PostMapping("/add-product-to-cart")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addProductToCart(@Valid @RequestBody CartDetailsRequest cartDetailsRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            return ResponseEntity.ok(cartService.addProductToCart(cartDetailsRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update-product-quantity")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateProductQuantity(@RequestBody CartDetailsRequest cartDetailsRequest) {
        try {
            cartService.updateProductQuantity(cartDetailsRequest);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(Map.of("message", "Cập nhật số lượng sản phẩm thành công"));
    }

    @PutMapping("/plus-product-quantity/{cartDetailsId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> plusProductQuantity(@PathVariable Long cartDetailsId) {
        try {
            cartService.plusProductQuantity(cartDetailsId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(Map.of("message", "Cập nhật số lượng sản phẩm thành công"));
    }

    @PutMapping("/minus-product-quantity/{cartDetailsId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> minusProductQuantity(@PathVariable Long cartDetailsId) {
        try {
            cartService.minusProductQuantity(cartDetailsId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(Map.of("message", "Cập nhật số lượng sản phẩm thành công"));
    }

    @DeleteMapping("/delete-cart-details/{cartDetailsIds}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCartDetails(@PathVariable Long cartDetailsIds) {
        try {
            cartService.deleteCartDetails(cartDetailsIds);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }
}
