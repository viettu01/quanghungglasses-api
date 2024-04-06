package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarrantyDetailsResponse {
    private Long id;
    private Long orderId; // id đơn hàng
    private Long productDetailsId; // id chi tiết sản phẩm
    private String productName; // tên sản phẩm
    private String productColor; // màu sắc sản phẩm
    private Integer productQuantitySellInOrder; // số lượng bán trong đơn hàng
    private Double productPriceSellInOrder; // giá bán trong đơn hàng
    private Integer quantity; // số lượng sản phẩm cần bảo hành
    private Double cost; // chi phí bảo hành
    private Integer warrantyType; // 0: Đổi, 1: Sửa
}
