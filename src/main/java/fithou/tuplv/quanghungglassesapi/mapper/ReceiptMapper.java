package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.ReceiptDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ReceiptRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ReceiptDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.ReceiptResponse;
import fithou.tuplv.quanghungglassesapi.entity.Receipt;
import fithou.tuplv.quanghungglassesapi.entity.ReceiptDetails;
import fithou.tuplv.quanghungglassesapi.repository.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ReceiptMapper {
    final ModelMapper modelMapper;
    final ProductMapper productMapper;
    final ReceiptDetailsRepository receiptDetailsRepository;
    final ReceiptRepository receiptRepository;
    final SupplierRepository supplierRepository;
    final StaffRepository staffRepository;
    final ProductDetailsRepository productDetailsRepository;

    // ReceiptMapper
    public ReceiptResponse convertToResponse(Receipt receipt) {
        ReceiptResponse receiptResponse = modelMapper.map(receipt, ReceiptResponse.class);
        receiptResponse.setStaffFullname(receipt.getStaff().getFullname());
        receiptResponse.setSupplierId(receipt.getSupplier().getId());
        receiptResponse.setSupplierName(receipt.getSupplier().getName());
        receiptResponse.setTotalMoney(receipt.getReceiptDetails().stream().mapToDouble(receiptDetails -> receiptDetails.getQuantity() * receiptDetails.getPrice()).sum());
        receiptResponse.setReceiptDetails(receipt.getReceiptDetails().stream().map(receiptDetails -> {
            ReceiptDetailsResponse receiptDetailsResponse = modelMapper.map(receiptDetails, ReceiptDetailsResponse.class);
            receiptDetailsResponse.setProductName(receiptDetails.getProductDetails().getProduct().getName());
            receiptDetailsResponse.setProductColor(receiptDetails.getProductDetails().getColor());
            return receiptDetailsResponse;
        }).collect(Collectors.toList()));
        return receiptResponse;
    }

    public Receipt convertToEntity(ReceiptRequest receiptRequest) {
        Receipt receipt = modelMapper.map(receiptRequest, Receipt.class);
        receipt.setSupplier(supplierRepository.findById(receiptRequest.getSupplierId()).orElse(null));
        receipt.setStaff(staffRepository.findByAccountEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null));
        return receipt;
    }

    public ReceiptDetails convertToEntity(ReceiptDetailsRequest receiptDetailsRequest) {
        ReceiptDetails receiptDetails = modelMapper.map(receiptDetailsRequest, ReceiptDetails.class);
        receiptDetails.setProductDetails(productDetailsRepository.findById(receiptDetailsRequest.getProductDetailsId()).orElse(null));
        return receiptDetails;
    }
}
