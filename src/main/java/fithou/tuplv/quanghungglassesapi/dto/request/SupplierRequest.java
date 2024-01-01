package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupplierRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Tên nhà cung cấp không được để trống")
    @Length(max = 20, message = "Tên nhà cung cấp không được quá 20 ký tự")
    private String name;

    @Length(max = 10, message = "Số điện thoại phải là 10 số")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Length(max = 200, message = "Địa chỉ không được quá 200 ký tự")
    private String address;
}