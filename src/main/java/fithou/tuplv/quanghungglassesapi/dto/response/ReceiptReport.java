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
public class ReceiptReport {
//    private Long productId; // Mã sản phẩm
//    private String productName; // Tên sản phẩm
//    private String productColor; // Màu sắc sản phẩm
//    private String productThumbnail; // Ảnh sản phẩm
//    private Integer quantityReceipt; // Số lượng nhập
//    private Double totalMoneyReceipt; // Tổng tiền nhập
//    private List<Long> receiptIds; // Danh sách mã phiếu nhập
//    private Date createdDate; // Ngày tạo
//    private String createdBy; // Người tạo

    //    private Integer day; // Ngày
    private Integer month; // Tháng
    private Integer year; // Năm
    private Integer totalQuantityReceipt; // Tổng số lượng nhập
    private Double totalMoneyReceiptTotal; // Tổng tiền nhập
    private List<ProductReport> products; // Danh sách sản phẩm
}
