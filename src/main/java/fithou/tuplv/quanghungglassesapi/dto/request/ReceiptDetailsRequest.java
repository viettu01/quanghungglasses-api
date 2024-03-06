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
public class ReceiptDetailsRequest implements Serializable {
    private Long id;

//    @NotNull(message = "Số lượng yêu cầu không được để trống")
//    @DecimalMin(value = "0", inclusive = false, message = "Số lượng phải lớn hơn 0")
//    private Integer requestedQuantity; // số lượng yêu cầu

//    @NotNull(message = "Số lượng thực nhập không được để trống")
//    @DecimalMin(value = "0", inclusive = false, message = "Số lượng phải lớn hơn 0")
//    private Integer actualQuantity; // số lượng thực nhập

    @NotNull(message = "Số lượng nhập không được để trống")
    @DecimalMin(value = "0", inclusive = false, message = "Số lượng phải lớn hơn 0")
    private Integer quantity; // số lượng thực nhập

    @NotNull(message = "Giá nhập hàng không được để trống")
    @DecimalMin(value = "0", inclusive = false, message = "Giá phải lớn hơn 0")
    private Double price;

    @NotNull(message = "Mã sản phẩm không được để trống")
    private Long productDetailsId;
}