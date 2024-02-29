package fithou.tuplv.quanghungglassesapi.controller;

import fithou.tuplv.quanghungglassesapi.dto.request.*;
import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.LoginResponse;
import fithou.tuplv.quanghungglassesapi.security.CustomUserDetails;
import fithou.tuplv.quanghungglassesapi.security.jwt.JwtTokenProvider;
import fithou.tuplv.quanghungglassesapi.service.AccountService;
import fithou.tuplv.quanghungglassesapi.service.CustomerService;
import fithou.tuplv.quanghungglassesapi.service.EmailService;
import fithou.tuplv.quanghungglassesapi.service.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AuthRestController {
    final AccountService accountService;
    final CustomerService customerService;
    final StaffService staffService;
    final EmailService emailService;
    final JwtTokenProvider tokenProvider;
    final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            if (!accountService.existsByUsername(loginRequest.getEmail()))
                return ResponseEntity.badRequest().body(ERROR_USER_NOT_FOUND);

            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Nếu thông tin hợp lệ -> Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (LockedException ex) {
            // Người dùng bị khóa
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tài khoản bị khóa");
        } catch (BadCredentialsException ex) {
            // Người dùng nhập sai mật khẩu
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Sai mật khẩu");
        }
    }

//    @PostMapping("/register")
//    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest, BindingResult result) {
//        if (result.hasErrors()) {
//            HashMap<String, String> errors = new HashMap<>();
//            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
//            return ResponseEntity.badRequest().body(errors);
//        }
//
//        try {
//            CustomerResponse customerResponse = customerService.register(registerRequest);
////            emailService.sendVerificationCode(customerResponse);
//            return ResponseEntity.ok().body(Map.of("message", SUCCESS_REGISTER));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @ModelAttribute CustomerRequest customerRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }
        try {
            customerService.create(customerRequest);
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

    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            accountService.forgotPassword(forgotPasswordRequest);
            return ResponseEntity.ok().body(Map.of("message", "Lấy lại mật khẩu thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, BindingResult result) {
        if (result.hasErrors()) {
            HashMap<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(), fieldError.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        try {
            accountService.changePassword(changePasswordRequest);
            return ResponseEntity.ok().body(Map.of("message", "Đổi mật khẩu thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile/{email}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_STAFF') or #email == authentication.name")
    public ResponseEntity<?> getProfile(@PathVariable String email) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                if (authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_STAFF"))
                    return ResponseEntity.ok().body(staffService.findByAccountEmail(email));
            }
            return ResponseEntity.ok().body(customerService.findByAccountEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
