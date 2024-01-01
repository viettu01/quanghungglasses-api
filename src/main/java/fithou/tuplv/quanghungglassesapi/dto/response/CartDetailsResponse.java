package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDetailsResponse {
    private Long id;
    private Long cartId;
    private Long productDetailsId;
    private String productDetailsImage;
    private String productName;
    private String productColor;
    private Double productPrice;
    private Integer quantity;
}
