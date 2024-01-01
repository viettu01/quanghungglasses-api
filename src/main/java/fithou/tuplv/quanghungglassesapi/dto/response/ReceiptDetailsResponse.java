package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetailsResponse {
    private Long id;
    private Integer quantity;
    private Double price;
    private String productName;
    private String productColor;
    private String productDetailsImage;
}
