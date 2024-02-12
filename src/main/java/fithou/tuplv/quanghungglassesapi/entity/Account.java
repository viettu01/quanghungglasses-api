package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100)
    private String avatar;

    @Column(nullable = false)
    private Boolean status; // true: bình thường, false: bị khóa

    @Column(length = 6)
    private String verificationCode;

    @Column(nullable = false)
    private Boolean isVerifiedEmail; // true: đã xác minh, false: chưa xác minh

    @Column
    private Date verificationCodeExpiredAt;

    @ManyToMany(fetch = FetchType.EAGER) // EAGER: load hết dữ liệu liên quan
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToOne(mappedBy = "account")
    private Staff staff;

    @OneToOne(mappedBy = "account")
    private Customer customer;
}
