package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VnPaymentResponse {
    public Boolean success;
    public String paymentMethod;
    public String orderDescription;
    public String orderId;
    public String paymentId;
    public String transactionId;
    public String token;
    public String vnPayResponseCode;
}
