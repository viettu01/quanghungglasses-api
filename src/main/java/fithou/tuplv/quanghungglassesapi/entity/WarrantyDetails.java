package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "warranty_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarrantyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer warrantyType; // 0: Đổi, 1: Sửa

    @Column(nullable = false)
    private Integer quantity; // số lượng sản phẩm cần bảo hành

    @ManyToOne
    @JoinColumn(name = "product_details_id", nullable = false)
    private ProductDetails productDetails;

    @ManyToOne
    @JoinColumn(name = "warranty_id", nullable = false)
    private Warranty warranty;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
