package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String email;
    private Boolean status;
    private String verificationCode;
    private Boolean isVerifiedEmail;
    private Date verificationCodeExpiredAt;
    private List<RoleResponse> roles;
}