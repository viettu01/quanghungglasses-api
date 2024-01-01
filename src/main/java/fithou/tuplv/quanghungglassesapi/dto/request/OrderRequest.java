package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest implements Serializable {
    @NotBlank(message = "Địa chỉ không được để trống")
    @Length(max = 200, message = "Địa chỉ không được quá 200 ký tự")
    private String address;

    @NotBlank(message = "Số điện thoại không hợp lệ")
    @Length(max = 10, message = "Số điện thoại phải là 10 số")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    private Date paymentDate;

    @NotBlank(message = "Phương thức thanh toán không được để trống")
    private String paymentMethod;

    @NotNull(message = "Trạng thái thanh toán không được để trống")
    private Boolean paymentStatus;

    private Date completedDate;

    private String note;

    @NotNull(message = "Trạng thái đơn hàng không được để trống")
    private Integer orderStatus;

    @NotNull(message = "Mã người dùng không được để trống")
    private Long userId;

    @NotEmpty(message = "Danh sách sản phẩm không được để trống")
    private List<OrderDetailsRequest> orderDetails = new ArrayList<>();
}