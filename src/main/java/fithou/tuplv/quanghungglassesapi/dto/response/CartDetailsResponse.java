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
    private Long productDetailsId;
    private String productDetailsThumbnails;
    private String productName;
    private String productSlug;
    private String productColor;
    private Double productPriceOriginal;
    private Double productPrice;
    private Integer quantityInStock;
    private Integer quantity;
}
