package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "warranty")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Warranty extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean status; // 0: Đã hoàn thành, 1: Chưa hoàn thành

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @OneToMany(mappedBy = "warranty")
    private List<WarrantyDetails> warrantyDetails;
}
