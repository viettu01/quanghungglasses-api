package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff extends User implements Serializable {
    @Column(nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "staff")
    private List<Banner> banners;

    @OneToMany(mappedBy = "staff")
    private List<Receipt> receipts;

    @OneToMany(mappedBy = "staff")
    private List<Order> orders;

    @OneToMany(mappedBy = "staff")
    private List<Sale> sales;

    @OneToMany(mappedBy = "staff")
    private List<Warranty> warranty;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
