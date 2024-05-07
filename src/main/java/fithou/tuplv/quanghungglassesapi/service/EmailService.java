package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendVerificationCode(CustomerResponse customer) throws MessagingException, UnsupportedEncodingException;

    void sendEmailOrderSuccess(OrderResponse orderResponse) throws MessagingException, UnsupportedEncodingException;
}
