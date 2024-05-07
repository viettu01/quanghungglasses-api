package fithou.tuplv.quanghungglassesapi.controller;

import fithou.tuplv.quanghungglassesapi.dto.request.VnPaymentRequest;
import fithou.tuplv.quanghungglassesapi.entity.Order;
import fithou.tuplv.quanghungglassesapi.mapper.OrderMapper;
import fithou.tuplv.quanghungglassesapi.repository.OrderRepository;
import fithou.tuplv.quanghungglassesapi.service.EmailService;
import fithou.tuplv.quanghungglassesapi.service.OrderService;
import fithou.tuplv.quanghungglassesapi.service.impl.VNPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_ORDER_NOT_FOUND;

@RestController
@RequestMapping("/api/payment/vnpay")
@CrossOrigin(origins = "*")
@Transactional
@Slf4j
public class VNPayController {
    private final VNPayService vnPayService;
    private final OrderService orderService;
    private Authentication authentication;
    private final EmailService emailService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public VNPayController(VNPayService vnPayService, OrderService orderService, EmailService emailService, OrderMapper orderMapper, OrderRepository orderRepository) {
        this.vnPayService = vnPayService;
        this.orderService = orderService;
        this.emailService = emailService;
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(@ModelAttribute VnPaymentRequest vnPaymentRequest,
                                           HttpServletRequest request) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createPayment(vnPaymentRequest.getAmount(), vnPaymentRequest.getOrderInfo(), baseUrl);
        return ResponseEntity.ok().body(Map.of("redirectUrl", vnpayUrl));
    }

    @GetMapping("/vnpay-payment")
    public void GetMapping(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        int paymentStatus = vnPayService.orderReturn(request);
        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String transactionId = request.getParameter("vnp_TransactionNo");
//        String totalPrice = request.getParameter("vnp_Amount");

        if (paymentStatus == 1) {
            authentication.getAuthorities().forEach(grantedAuthority -> {
                if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                    try {
                        response.sendRedirect("http://localhost:4200/thanh-toan-thanh-cong/" + orderInfo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (grantedAuthority.getAuthority().equals("ROLE_ADMIN") || grantedAuthority.getAuthority().equals("ROLE_STAFF")) {
                    try {
                        orderService.update(Long.parseLong(orderInfo), 5, "");
                        response.sendRedirect("http://localhost:4200/admin/order/" + orderInfo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            orderService.updatePaymentStatus(Long.parseLong(orderInfo), true, sdf.parse(paymentTime));
            try {
                Order order = orderRepository.findById(Long.parseLong(orderInfo)).orElseThrow(() -> new RuntimeException(ERROR_ORDER_NOT_FOUND));
                emailService.sendEmailOrderSuccess(orderMapper.convertToResponse(order));
            } catch (MessagingException | UnsupportedEncodingException e) {
                log.error("Error when send email: {}", e.getMessage());
            }
            return;
        }

        authentication.getAuthorities().forEach(grantedAuthority -> {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                try {
                    orderService.update(Long.parseLong(orderInfo), 6, "Huỷ thanh toán");
                    response.sendRedirect("http://localhost:4200/don-hang/" + orderInfo);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    orderService.update(Long.parseLong(orderInfo), 6, "Huỷ thanh toán");
                    response.sendRedirect("http://localhost:4200/admin/order/" + orderInfo);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
