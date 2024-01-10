package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private List<Address> addresses;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL) // ALL: xóa user -> xóa cart
    private Cart cart;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
