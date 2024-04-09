package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private Date createdDate;
    private Date updatedDate;
}
