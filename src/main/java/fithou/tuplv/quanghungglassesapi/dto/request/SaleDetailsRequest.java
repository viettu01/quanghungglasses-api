package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDetailsRequest implements Serializable {
    private Long id;

    @NotNull(message = "Mức giảm giá không được để trống")
    @DecimalMin(value = "0", message = "Mức giảm giá phải ≥ 0 và ≤ 100")
    @DecimalMax(value = "100", message = "Mức giảm giá phải ≥ 0 và ≤ 100")
    private Float discount; // Giảm giá: 0% - 100%

//    @NotNull(message = "Mã khuyến mãi không được để trống")
//    private Long saleId;

    @NotNull(message = "Mã sản phẩm không được để trống")
    private Long productId;
}
