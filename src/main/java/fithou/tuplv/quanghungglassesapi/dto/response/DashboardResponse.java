package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DashboardResponse {
    private Long totalOrderInDay; // Tổng số đơn hàng trong ngày
    private Long totalOrderOnHold; // Tổng số đơn hàng đang chờ xử lý
    private Long totalOrderCompleted; // Tổng số đơn hàng đã hoàn thành
    private Long totalProductOutOfStock; // Tổng số sản phẩm hết hàng
    private Long totalProductSold; // Tổng số sản phẩm đã bán
    private Long totalCustomerNew; // Tổng số khách hàng mới
}
