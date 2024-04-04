package fithou.tuplv.quanghungglassesapi.controller;

import fithou.tuplv.quanghungglassesapi.dto.request.VnPaymentRequest;
import fithou.tuplv.quanghungglassesapi.service.OrderService;
import fithou.tuplv.quanghungglassesapi.service.impl.VNPayService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
@RequestMapping("/api/payment/vnpay")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class VNPayController {
    private final VNPayService vnPayService;
    private final OrderService orderService;

    @PostMapping("/create-payment")
    public ResponseEntity<?> createPayment(@ModelAttribute VnPaymentRequest vnPaymentRequest,
                                           HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createPayment(vnPaymentRequest.getAmount(), vnPaymentRequest.getOrderInfo(), baseUrl);
        return ResponseEntity.ok().body(Map.of("redirectUrl", vnpayUrl));
    }

    @GetMapping("/vnpay-payment")
    public void GetMapping(HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
                } else {
                    try {
                        orderService.update(Long.parseLong(orderInfo), 5, "");
                        response.sendRedirect("http://localhost:4200/admin/order/" + orderInfo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            orderService.updatePaymentStatus(Long.parseLong(orderInfo), true, sdf.parse(paymentTime));
            return;
        }

        authentication.getAuthorities().forEach(grantedAuthority -> {
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                try {
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
