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

    @ManyToOne
    @JoinColumn(name = "product_details_id", nullable = false)
    private ProductDetails productDetails;

    @Column(nullable = false)
    private Integer warrantyType; // 0: Đổi, 1: Sửa

    @ManyToOne
    @JoinColumn(name = "warranty_id", nullable = false)
    private Warranty warranty;
}
