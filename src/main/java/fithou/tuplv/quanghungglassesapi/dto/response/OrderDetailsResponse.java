package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsResponse implements Serializable {
    private String productName;
    private String productColor;
    private Double productPrice;
    private String productDetailsImage;
    private Double price;
    private Integer quantity;
}
