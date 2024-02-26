package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest extends UserRequest {
//    @NotNull(message = "Tài khoản không được để trống")
//    private Long accountId;

    @Valid
    private AccountRequest account;
}
