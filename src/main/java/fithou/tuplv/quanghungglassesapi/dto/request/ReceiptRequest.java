package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptRequest {
    @NotNull(message = "Trạng thái phiếu nhập không được để trống")
    private Boolean status;

    @NotNull(message = "Mã nhà cung cấp không được để trống")
    private Long supplierId;

    @Valid
    @NotEmpty(message = "Danh sách chi tiết phiếu nhập không được để trống")
    private List<ReceiptDetailsRequest> receiptDetails;
}
