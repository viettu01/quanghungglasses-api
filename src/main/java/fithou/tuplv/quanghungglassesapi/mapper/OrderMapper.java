package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.OrderDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.OrderRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.OrderResponse;
import fithou.tuplv.quanghungglassesapi.entity.Order;
import fithou.tuplv.quanghungglassesapi.entity.OrderDetails;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.repository.OrderRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
        orderResponse.setEmail(order.getCustomer().getEmail());
        order.getOrderDetails().forEach(orderDetails -> orderResponse.getOrderDetails().add(convertToResponse(orderDetails)));
        return orderResponse;
    }

    public Order convertToEntity(OrderRequest orderRequest) {
        Order order = modelMapper.map(orderRequest, Order.class);
        order.setCustomer(customerRepository.findById(orderRequest.getUserId()).orElse(null));
        order.setStaff(staffRepository.findById(orderRequest.getStaffId()).orElse(null));
        orderRequest.getOrderDetails().forEach(orderDetailsRequest -> order.getOrderDetails().add(convertToEntity(orderDetailsRequest)));
        return order;
    }

    // OrderDetailsMapper
    public OrderDetailsResponse convertToResponse(OrderDetails orderDetails) {
        OrderDetailsResponse orderDetailsResponse = modelMapper.map(orderDetails, OrderDetailsResponse.class);
        orderDetailsResponse.setProductName(orderDetails.getProductDetails().getProduct().getName());
        orderDetailsResponse.setProductColor(orderDetails.getProductDetails().getColor());
        orderDetailsResponse.setProductPrice(orderDetails.getProductDetails().getProduct().getPrice());
        orderDetailsResponse.setProductDetailsImage(orderDetails.getProductDetails().getImages().get(0));
        return orderDetailsResponse;
    }

    public OrderDetails convertToEntity(OrderDetailsRequest orderDetailsRequest) {
        OrderDetails orderDetails = modelMapper.map(orderDetailsRequest, OrderDetails.class);
        orderDetails.setOrder(orderRepository.findById(orderDetailsRequest.getOrderId()).orElse(null));
        orderDetails.setProductDetails(productDetailsRepository.findById(orderDetailsRequest.getProductDetailsId()).orElse(null));
        return orderDetails;
    }
}