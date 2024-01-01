package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsRequest implements Serializable {
    private Long id;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0", inclusive = false, message = "Giá phải lớn hơn 0")
    private Integer quantity;

    @NotNull(message = "Mã giỏ hàng không được để trống")
    private Long cartId;

    @NotNull(message = "Mã sản phẩm không được để trống")
    private Long productDetailsId;
}