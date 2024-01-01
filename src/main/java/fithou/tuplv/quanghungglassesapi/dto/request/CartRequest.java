package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartRequest implements Serializable {
    @NotNull(message = "Mã người dùng không được để trống")
    private Long userId;
}