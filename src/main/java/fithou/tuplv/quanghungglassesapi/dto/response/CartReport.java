package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartReport {
    private Long productDetailsId;
    private String productThumbnails;
    private String productName;
    private String productColor;
    private Integer totalQuantityInStock; // Số lượng sản phẩm trong kho
    private Integer totalQuantityInCart; // Số lượng sản phẩm trong giỏ hàng
}
