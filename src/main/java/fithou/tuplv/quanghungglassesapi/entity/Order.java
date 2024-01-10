package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "`order`")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String fullname; // tên người nhận hàng

    @Column(nullable = false)
    private String address; // địa chỉ nhận hàng

    @Column(nullable = false)
    private String phone; // số điện thoại người nhận hàng

    @Column(nullable = false)
    @CreatedDate
    private Date createdDate; // ngày đặt hàng

    @Column
    private Date paymentDate; // ngày thanh toán đơn hàng

    @Column(nullable = false)
    private String paymentMethod; // phương thức thanh toán

    @Column(nullable = false)
    private Boolean paymentStatus; // trạng thái thanh toán (0: chưa thanh toán, 1: đã thanh toán)

    @Column
    private Date completedDate; // ngày hoàn thành đơn hàng

    @Column
    private String note; // ghi chú

    @Column(nullable = false)
    private Integer orderStatus; // trạng thái đơn hàng (0: chờ xác nhận, 1: đã xác nhận, 2: đang giao hàng, 3: đã giao hàng, 4: đã hủy)

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetails;
}
