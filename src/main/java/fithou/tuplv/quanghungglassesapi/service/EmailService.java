package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {
    void sendEmailForgotPassword(CustomerResponse customer) throws MessagingException, UnsupportedEncodingException;

    void sendVerificationCode(CustomerResponse customer) throws MessagingException, UnsupportedEncodingException;
}
