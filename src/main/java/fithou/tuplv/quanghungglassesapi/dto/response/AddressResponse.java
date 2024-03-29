package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse implements Serializable {
    private Long id;
    private String fullname;
    private String phone;
    private String city;
    private String district;
    private String ward;
    private String address;
}