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
public class OrderResponse implements Serializable {
    private Long id;
    private Long customerId;
    private String fullname;
    private String email;
    private String address;
    private String phone;
    private Date createdDate;
    private Date paymentDate;
    private Integer paymentMethod;
    private Boolean paymentStatus;
    private Date completedDate;
    private String eyeglassPrescription;
    private String note;
    private Integer orderStatus;
    private Date confirmDate; // ngày xác nhận đơn hàng
    private Date deliveryToShipperDate; // ngày giao cho shipper
    private Date deliveryDate; // ngày giao hàng
    private Date receiveDate; // ngày nhận hàng
    private Date cancelDate; // ngày hủy đơn hàng
    private String cancelReason; // lý do hủy đơn hàng
    private Double totalMoney;

    private List<OrderDetailsResponse> orderDetails = new ArrayList<>();
}