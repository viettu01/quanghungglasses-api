package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordRequest {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Mã xác minh không được để trống")
    private String verificationCode;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Length(min = 6, max = 20, message = "Mật khẩu phải từ 6 đến 20 ký tự")
    @Pattern(regexp = "^(?=[^A-Z]*[A-Z])(?=[^a-z]*[a-z])(?=\\D*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{9,}$", message = "Mật khẩu phải chứa ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt")
    private String newPassword;
}
