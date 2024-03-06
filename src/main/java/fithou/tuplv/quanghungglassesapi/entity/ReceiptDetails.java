package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "receipt_details") // Bảng chi tiết phiếu nhập
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
//    private Integer requestedQuantity; // số lượng yêu cầu

//    @Column(nullable = false)
//    private Integer actualQuantity; // số lượng thực nhập

    @Column(nullable = false)
    private Integer quantity; // số lượng thực nhập

    @Column(nullable = false)
    private Double price; // giá nhập hàng

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @ManyToOne
    @JoinColumn(name = "product_details_id", nullable = false)
    private ProductDetails productDetails;
}
