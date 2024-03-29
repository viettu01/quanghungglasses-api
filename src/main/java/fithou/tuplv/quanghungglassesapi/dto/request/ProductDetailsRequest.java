package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Màu sắc không được để trống")
    @Length(max = 20, message = "Màu sắc không được quá 20 ký tự")
    private String color;

    @NotNull(message = "Số lượng không được để trống")
    @DecimalMin(value = "0", message = "Số lượng sản phẩm phải > 0")
    private Integer quantity;
}