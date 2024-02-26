package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponse extends UserResponse {
    private Integer totalOrder; // Tổng số đơn hàng đã xử lý
    private Double totalMoney; // Tổng số tiền đã bán
    AccountResponse account;
}
