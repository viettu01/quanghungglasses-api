package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest implements Serializable {
    @NotBlank(message = "Tên quyền không được để trống")
    @Length(max = 20, message = "Tên quyền không được quá 20 ký tự")
    private String name;

    @Length(max = 200, message = "Mô tả không được quá 200 ký tự")
    private String description;
}