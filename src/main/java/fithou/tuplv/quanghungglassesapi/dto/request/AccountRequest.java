package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequest {
    @NotBlank(message = "Email không được để trống")
    @Length(max = 50, message = "Email không được quá 50 ký tự")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Length(min = 6, max = 20, message = "Mật khẩu phải từ 6 đến 20 ký tự")
    private String password;

    private MultipartFile avatarFile;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;

    @NotEmpty(message = "Quyền không được để trống")
    private List<String> roleName = new ArrayList<>();

    @NotNull(message = "Trạng thái xác thực email không được để trống")
    private Boolean isVerifiedEmail;
}
