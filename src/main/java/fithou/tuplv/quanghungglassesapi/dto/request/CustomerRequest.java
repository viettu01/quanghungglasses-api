package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest extends UserRequest {
    //    @NotNull(message = "Tài khoản không được để trống")
//    private Long accountId;

    @Length(max = 200, message = "Địa chỉ không được quá 200 ký tự")
    private String address;

    @Valid
    private AccountRequest account;
}
