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
public class SaleDetailsResponse implements Serializable {
    private Long id;
    private Float discount; // Giảm giá: 0% - 100%
    private ProductResponse product;
}
