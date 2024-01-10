package fithou.tuplv.quanghungglassesapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String fullname;

    @Column(length = 10, unique = true)
    private String phone;

    @Column(length = 50, unique = true)
    private String email;

    @Column(length = 5)
    private String gender;

    @Column
    private Date birthday;

    @Column(length = 100)
    private String avatar;
}
