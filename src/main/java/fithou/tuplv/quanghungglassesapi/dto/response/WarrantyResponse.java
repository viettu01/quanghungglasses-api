package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarrantyResponse {
    private Long id;
    private String customerName;
    private String customerPhone;
    private String staffName;
    private Boolean status;
    private Date createdDate;
    private Date updatedDate;
    private List<WarrantyDetailsResponse> warrantyDetails;
}
