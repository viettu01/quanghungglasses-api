package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "product_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String color;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToMany(mappedBy = "productDetails")
    private List<ReceiptDetails> receiptDetails;

    @OneToMany(mappedBy = "productDetails", cascade = CascadeType.ALL)
    private List<CartDetails> cartDetails;

    @OneToMany(mappedBy = "productDetails")
    private List<OrderDetails> orderDetails;

    @OneToMany(mappedBy = "productDetails")
    private List<WarrantyDetails> warrantyDetails;
}
