package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
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
    private Long id;

    @NotBlank(message = "Họ tên không được để trống")
    @Length(max = 30, message = "Họ tên không được quá 30 ký tự")
    private String fullname;

    @NotBlank(message = "Địa chỉ không được để trống")
    @Length(max = 200, message = "Địa chỉ không được quá 200 ký tự")
    private String address;

    @NotBlank(message = "Số điện thoại không hợp lệ")
    @Length(max = 10, message = "Số điện thoại phải là 10 số")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    private Date paymentDate; // ngày thanh toán đơn hàng

    @NotNull(message = "Phương thức thanh toán không được để trống")
    private Integer paymentMethod; // phương thức thanh toán (0: tiền mặt, 1: VNPAY)

    @NotNull(message = "Trạng thái thanh toán không được để trống")
    private Boolean paymentStatus; // trạng thái thanh toán (0: chưa thanh toán, 1: đã thanh toán)

    private Date completedDate; // ngày hoàn thành đơn hàng

    private MultipartFile eyeglassPrescriptionImage; // Đơn kính (file)

    private String note; // ghi chú

    @NotNull(message = "Trạng thái đơn hàng không được để trống")
    private Integer orderStatus; // trạng thái đơn hàng (0: chờ xác nhận, 1: đã xác nhận, 2: đang giao hàng, 3: đã giao hàng, 4: đã hủy)

    private String cancelReason; // lý do hủy đơn hàng

    @NotEmpty(message = "Danh sách sản phẩm không được để trống")
    @Valid
    private List<OrderDetailsRequest> orderDetails = new ArrayList<>(); // danh sách sản phẩm
}