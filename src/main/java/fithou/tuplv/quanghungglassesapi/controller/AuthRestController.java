package fithou.tuplv.quanghungglassesapi.controller;

import fithou.tuplv.quanghungglassesapi.dto.request.LoginRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.LoginResponse;
import fithou.tuplv.quanghungglassesapi.security.CustomUserDetails;
import fithou.tuplv.quanghungglassesapi.security.jwt.JwtTokenProvider;
import fithou.tuplv.quanghungglassesapi.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_USER_NOT_FOUND;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "localhost:4200")
@AllArgsConstructor
public class AuthRestController {
    final AccountService accountService;
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
}
