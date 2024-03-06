package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponse {
    private Long id;
    private Date createdDate;
    private Date updatedDate;
    private String staffFullname;
    private String supplierName;
    private Double totalMoney;
    private List<ReceiptDetailsResponse> receiptDetails = new ArrayList<>();
}
