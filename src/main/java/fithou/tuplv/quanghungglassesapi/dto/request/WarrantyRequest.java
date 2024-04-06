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
public class WarrantyRequest {
    private Long id;
    private String customerPhone;
    private Boolean status;
    private List<WarrantyDetailsRequest> warrantyDetails;
}
