package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String fullname;

    @Column(length = 10, unique = true)
    private String phone;

    @Column(length = 5)
    private String gender;

    @Column
    private Date birthday;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100)
    private String avatar;

    @Column(nullable = false)
    private Boolean status; // true: bình thường, false: bị khóa

    @ManyToMany(fetch = FetchType.EAGER) // EAGER: load hết dữ liệu liên quan
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Banner> banners;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

    @OneToMany(mappedBy = "user")
    private List<Receipt> receipts;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL) // ALL: xóa user -> xóa cart
    private Cart cart;
}
