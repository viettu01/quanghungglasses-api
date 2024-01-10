package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "Họ tên không được để trống")
    @Length(max = 30, message = "Họ tên không được quá 30 ký tự")
    private String fullname;

    @NotBlank(message = "Số điện thoại không hợp lệ")
    @Length(max = 10, message = "Số điện thoại phải là 10 số")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Email không được để trống")
    @Length(max = 50, message = "Email không được quá 50 ký tự")
    @Email(message = "Email không hợp lệ")
    private String email;

    @Length(max = 5, message = "Giới tính không được quá 5 ký tự")
    private String gender;

    private Date birthday;

    private MultipartFile avatarFile;
}
