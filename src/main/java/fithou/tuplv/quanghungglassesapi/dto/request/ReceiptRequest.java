package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptRequest {
    private Boolean status;
    private Long userId;
    private Long supplierId;
    private List<ReceiptDetailsRequest> receiptDetails;
}
