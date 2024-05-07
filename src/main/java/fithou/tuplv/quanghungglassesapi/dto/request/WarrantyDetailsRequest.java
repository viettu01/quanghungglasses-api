package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarrantyDetailsRequest {
    private Long id;
    private Long orderId;
    private Long productDetailsId;
    private Integer quantity; // số lượng sản phẩm cần bảo hành
    private Double cost; // chi phí bảo hành
    private String note; // ghi chú
    private Integer warrantyType; // 0: Đổi, 1: Sửa
}