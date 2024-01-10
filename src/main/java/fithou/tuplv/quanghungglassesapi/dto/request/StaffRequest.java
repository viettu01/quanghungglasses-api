package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffRequest extends UserRequest {
    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;

    @NotNull(message = "Tài khoản không được để trống")
    private Long accountId;
}