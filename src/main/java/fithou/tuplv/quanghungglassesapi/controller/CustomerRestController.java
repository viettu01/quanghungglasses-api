package fithou.tuplv.quanghungglassesapi.controller;

import fithou.tuplv.quanghungglassesapi.dto.request.RegisterRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.VerifyEmailRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import fithou.tuplv.quanghungglassesapi.service.CustomerService;
import fithou.tuplv.quanghungglassesapi.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class CustomerRestController {
    final CustomerService customerService;
    final EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            CustomerResponse customerResponse = customerService.register(registerRequest);
            emailService.sendVerificationCode(customerResponse);
            return ResponseEntity.ok().body(Map.of("message", SUCCESS_REGISTER));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend-verification-code")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            CustomerResponse customerResponse = customerService.resendVerificationCode(email);
            emailService.sendVerificationCode(customerResponse);
            return ResponseEntity.ok().body(Map.of("message", SUCCESS_RESEND_VERIFICATION_CODE));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody VerifyEmailRequest verifyEmailRequest) {
        try {
            customerService.verifyEmail(verifyEmailRequest);
            return ResponseEntity.ok().body(Map.of("message", SUCCESS_VERIFY_EMAIL));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
