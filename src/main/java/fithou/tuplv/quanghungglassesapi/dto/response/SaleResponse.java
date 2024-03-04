package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse implements Serializable {
    private Long id;
    private String name;
    private Integer totalProduct;
    private Date startDate;
    private Date endDate;
    private List<SaleDetailsResponse> saleDetails = new ArrayList<>();
    private StaffResponse staff;
    private Date createdDate;
    private Date updatedDate;
}
