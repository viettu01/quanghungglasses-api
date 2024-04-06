package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.WarrantyDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.WarrantyRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.WarrantyDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.WarrantyResponse;
import fithou.tuplv.quanghungglassesapi.entity.OrderDetails;
import fithou.tuplv.quanghungglassesapi.entity.Warranty;
import fithou.tuplv.quanghungglassesapi.entity.WarrantyDetails;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.repository.OrderRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WarrantyMapper {
    final ModelMapper modelMapper;
    final StaffRepository staffRepository;
    final CustomerRepository customerRepository;
    final OrderRepository orderRepository;
    final ProductDetailsRepository productDetailsRepository;

    public Warranty convertToEntity(WarrantyRequest warrantyRequest) {
        Warranty warranty = modelMapper.map(warrantyRequest, Warranty.class);
        warranty.setCustomer(customerRepository.findByPhone(warrantyRequest.getCustomerPhone()).orElse(null));
        warranty.setStaff(staffRepository.findByAccountEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null));
        warranty.getWarrantyDetails().clear();
        return warranty;
    }

    public WarrantyResponse convertToResponse(Warranty warranty) {
        WarrantyResponse warrantyResponse = modelMapper.map(warranty, WarrantyResponse.class);
        warrantyResponse.setCustomerName(warranty.getCustomer().getFullname());
        warrantyResponse.setCustomerPhone(warranty.getCustomer().getPhone());
        warrantyResponse.setStaffName(warranty.getStaff().getFullname());
        warrantyResponse.getWarrantyDetails().clear();
        warranty.getWarrantyDetails().forEach(warrantyDetails -> warrantyResponse.getWarrantyDetails().add(convertToResponse(warrantyDetails)));
        return warrantyResponse;
    }

    public WarrantyDetails convertToEntity(WarrantyDetailsRequest warrantyDetailsRequest) {
        WarrantyDetails warrantyDetails = modelMapper.map(warrantyDetailsRequest, WarrantyDetails.class);
        warrantyDetails.setOrder(orderRepository.findById(warrantyDetailsRequest.getOrderId()).orElse(null));
        warrantyDetails.setProductDetails(productDetailsRepository.findById(warrantyDetailsRequest.getProductDetailsId()).orElse(null));
        return warrantyDetails;
    }

    public WarrantyDetailsResponse convertToResponse(WarrantyDetails warrantyDetails) {
        WarrantyDetailsResponse warrantyDetailsResponse = modelMapper.map(warrantyDetails, WarrantyDetailsResponse.class);
        warrantyDetailsResponse.setOrderId(warrantyDetails.getOrder().getId());
        warrantyDetailsResponse.setProductDetailsId(warrantyDetails.getProductDetails().getId());
        warrantyDetailsResponse.setProductName(warrantyDetails.getProductDetails().getProduct().getName());
        warrantyDetailsResponse.setProductColor(warrantyDetails.getProductDetails().getColor());
        warrantyDetailsResponse.setProductQuantitySellInOrder(warrantyDetails.getOrder().getOrderDetails().stream()
                .filter(orderDetails -> orderDetails.getProductDetails().getId().equals(warrantyDetails.getProductDetails().getId()))
                .findFirst().map(OrderDetails::getQuantity).orElse(0));
        warrantyDetailsResponse.setProductPriceSellInOrder(warrantyDetails.getOrder().getOrderDetails().stream()
                .filter(orderDetails -> orderDetails.getProductDetails().getId().equals(warrantyDetails.getProductDetails().getId()))
                .findFirst().map(OrderDetails::getPrice).orElse(0.0));
        return warrantyDetailsResponse;
    }
}
