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
    //    private Integer requestedQuantity; // số lượng yêu cầu
    //    private Integer actualQuantity; // số lượng thực nhập
    private Integer quantity; // số lượng thực nhập
    private Double price;
    private ProductDetailsResponse productDetails;
}
