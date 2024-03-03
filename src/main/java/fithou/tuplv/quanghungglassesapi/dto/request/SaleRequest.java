package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Tên chương trình khuyến mãi không được để trống")
    @Length(max = 50, message = "Tên chương trình khuyến mãi không được quá 50 ký tự")
    private String name;

    private Date startDate;

    private Date endDate;

    @NotEmpty(message = "Danh sách sản phẩm khuyến mãi không được để trống")
    @Valid
    private List<SaleDetailsRequest> saleDetails = new ArrayList<>();
}
