package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "supplier") // Bảng nhà cung cấp
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name; // Tên nhà cung cấp

    @Column(length = 10, unique = true)
    private String phone; // Số điện thoại nhà cung cấp

    @Column(length = 200)
    private String address; // Địa chỉ nhà cung cấp

    @OneToMany(mappedBy = "supplier")
    private List<Receipt> receipts;
}
