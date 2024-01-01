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
public class OriginRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Xuất xứ không được để trống")
    @Length(max = 50, message = "Xuất xứ không được quá 50 ký tự")
    private String name;
}