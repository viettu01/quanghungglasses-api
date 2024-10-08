package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReport {
    private Integer month; // Tháng
    private Integer year; // Năm
    private Integer totalQuantityOrder; // Tổng sản phẩm đã bán
    private Double totalMoneyOrder; // Tổng tiền bán
    private List<ProductReport> products; // Danh sách sản phẩm
}
