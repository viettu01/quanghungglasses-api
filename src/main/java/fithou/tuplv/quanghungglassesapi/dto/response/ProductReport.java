package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductReport {
    private Long id;
    private String name;
    private String color;
    private Integer totalQuantity; // Số lượng đã bán / đã nhập / tồn kho
    private Double totalMoney; // Tổng tiền đã bán / đã nhập
}
