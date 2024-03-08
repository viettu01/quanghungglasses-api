package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.SaleDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.SaleRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.SaleResponse;
import fithou.tuplv.quanghungglassesapi.entity.Sale;
import fithou.tuplv.quanghungglassesapi.entity.SaleDetails;
import fithou.tuplv.quanghungglassesapi.repository.ProductRepository;
import fithou.tuplv.quanghungglassesapi.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SaleMapper {
    final ModelMapper modelMapper;
    final StaffRepository staffRepository;
    final ProductRepository productRepository;

    public Sale convertToEntity(SaleRequest saleRequest) {
        Sale sale = modelMapper.map(saleRequest, Sale.class);
        sale.setStaff(staffRepository.findByAccountEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null));
        return sale;
    }

    public SaleResponse convertToResponse(Sale sale) {
        SaleResponse saleResponse = modelMapper.map(sale, SaleResponse.class);
        saleResponse.setTotalProduct(sale.getSaleDetails().size());
        return saleResponse;
    }

    public SaleDetails convertToEntity(SaleDetailsRequest saleDetailsRequest) {
        SaleDetails saleDetails = modelMapper.map(saleDetailsRequest, SaleDetails.class);
        saleDetails.setProduct(productRepository.findById(saleDetailsRequest.getProductId()).orElse(null));
        return saleDetails;
    }
}
