package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Họ tên không được để trống")
    @Length(max = 30, message = "Họ tên không được quá 30 ký tự")
    private String fullname;

    @NotBlank(message = "Số điện thoại không hợp lệ")
    @Length(max = 10, message = "Số điện thoại phải là 10 số")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Tên thành phố không được để trống")
    @Length(max = 20, message = "Tên thành phố không được quá 20 ký tự")
    private String city;

    @NotBlank(message = "Tên Quận/Huyện không được để trống")
    @Length(max = 20, message = "Tên Quận/Huyện không được quá 20 ký tự")
    private String district;

    @NotBlank(message = "Tên Phường/Xã không được để trống")
    @Length(max = 20, message = "Tên Phường/Xã không được quá 20 ký tự")
    private String ward;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Length(max = 100, message = "Địa chỉ không được quá 100 ký tự")
    private String address;

    @NotNull(message = "Mã người dùng không được để trống")
    private Long customerId;
}