package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.OrderDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.OrderRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderResponse;
import fithou.tuplv.quanghungglassesapi.entity.Customer;
import fithou.tuplv.quanghungglassesapi.entity.Order;
import fithou.tuplv.quanghungglassesapi.entity.OrderDetails;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.repository.OrderRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class OrderMapper {
    final ModelMapper modelMapper;
    final ProductDetailsRepository productDetailsRepository;
    final OrderRepository orderRepository;
    final StaffRepository staffRepository;
    final CustomerRepository customerRepository;

    // OrderMapper
    public OrderResponse convertToResponse(Order order) {
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        if (Objects.nonNull(order.getCustomer().getAccount()))
            orderResponse.setEmail(order.getCustomer().getAccount().getEmail());
        orderResponse.getOrderDetails().clear();
        order.getOrderDetails().forEach(orderDetails -> orderResponse.getOrderDetails().add(convertToResponse(orderDetails)));
        return orderResponse;
    }

    public Order convertToEntity(OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        order.getOrderDetails().clear();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(authority -> {
            if (authority.getAuthority().equals("ROLE_ADMIN") || authority.getAuthority().equals("ROLE_STAFF")) {
                Customer customer = customerRepository.findByPhone(orderRequest.getPhone()).orElse(null);
                if (customer == null) {
                    customer = new Customer();
                    customer.setPhone(orderRequest.getPhone());
                    customer.setFullname(orderRequest.getFullname());
                    customer.setAddress(orderRequest.getAddress());
                    customerRepository.save(customer);
                }
                order.setCustomer(customer);
                order.setStaff(staffRepository.findByAccountEmail(authentication.getName()).orElse(null));
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                order.setCustomer(customerRepository.findByAccountEmail(authentication.getName()).orElse(null));
            }
        });
        return order;
    }

    // OrderDetailsMapper
    public OrderDetailsResponse convertToResponse(OrderDetails orderDetails) {
        OrderDetailsResponse orderDetailsResponse = modelMapper.map(orderDetails, OrderDetailsResponse.class);
        orderDetailsResponse.setProductName(orderDetails.getProductDetails().getProduct().getName());
        orderDetailsResponse.setProductColor(orderDetails.getProductDetails().getColor());
        orderDetailsResponse.setProductPrice(orderDetails.getProductDetails().getProduct().getPrice());
        orderDetailsResponse.setProductThumbnail(orderDetails.getProductDetails().getProduct().getThumbnail());
        return orderDetailsResponse;
    }

    public OrderDetails convertToEntity(OrderDetailsRequest orderDetailsRequest) {
        OrderDetails orderDetails = modelMapper.map(orderDetailsRequest, OrderDetails.class);
        orderDetails.setPriceOriginal(orderDetails.getProductDetails().getProduct().getPrice());
        orderDetails.setProductDetails(productDetailsRepository.findById(orderDetailsRequest.getProductDetailsId()).orElse(null));
        return orderDetails;
    }
}