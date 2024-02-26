package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse extends UserResponse {
    private Integer totalOrder; // Tổng số đơn hàng
    private Double totalMoney; // Tổng số tiền đã mua
    private AccountResponse account;
}
