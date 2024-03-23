package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.response.CustomerResponse;
import fithou.tuplv.quanghungglassesapi.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.FROM_EMAIL;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.STORE_NAME;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    final JavaMailSender mailSender;

    @Override
    public void sendEmailForgotPassword(CustomerResponse customer) throws MessagingException, UnsupportedEncodingException {

    }

    @Override
    public void sendVerificationCode(CustomerResponse customerResponse) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        String verificationCode = customerResponse.getAccount().getVerificationCode();
        String subject = "Xác minh địa chỉ email của bạn";
        String content = "Xin chào " + customerResponse.getFullname() + ",<br>"
                + "Bạn đã yêu cầu xác minh email " + customerResponse.getAccount().getEmail() + ":<br>"
                + "Để tiếp tục, vui lòng nhập mã xác minh:<br>"
                + "<h3>" + verificationCode + "</h3>"
                + "Đường dẫn này sẽ hết hạn trong 5 phút.<br>"
                + "Nếu không phải bạn yêu cầu. Xin hãy bỏ qua tin nhắn này.<br>"
                + "Email này được được gửi tự động. Vui lòng không trả lời email này.<br>"
                + "Trân trọng,<br>"
                + STORE_NAME;

        helper.setFrom(FROM_EMAIL, STORE_NAME);
        helper.setTo(customerResponse.getAccount().getEmail());
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
