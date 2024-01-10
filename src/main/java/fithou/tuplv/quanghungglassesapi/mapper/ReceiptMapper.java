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
import org.springframework.stereotype.Component;

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
        receipt.setStaff(staffRepository.findById(receipt.getStaff().getId()).orElse(null));
//        receipt.getReceiptDetails().forEach(receiptDetails -> receiptResponse.getReceiptDetails().add(convertToResponse(receiptDetails)));
        return receiptResponse;
    }

    public Receipt convertToEntity(ReceiptRequest receiptRequest) {
        Receipt receipt = modelMapper.map(receiptRequest, Receipt.class);
        receipt.setStaff(staffRepository.findById(receiptRequest.getUserId()).orElse(null));
        receipt.setSupplier(supplierRepository.findById(receiptRequest.getSupplierId()).orElse(null));
//        receiptRequest.getReceiptDetails().forEach(receiptDetailsRequest -> receipt.getReceiptDetails().add(convertToEntity(receiptDetailsRequest)));
        return receipt;
    }

    // ReceiptDetailsMapper
    public ReceiptDetailsResponse convertToResponse(ReceiptDetails receiptDetails) {
        ReceiptDetailsResponse receiptDetailsResponse = modelMapper.map(receiptDetails, ReceiptDetailsResponse.class);
        receiptDetailsResponse.setProductName(receiptDetails.getProductDetails().getProduct().getName());
        receiptDetailsResponse.setProductColor(receiptDetails.getProductDetails().getColor());
        receiptDetailsResponse.setProductDetailsImage(receiptDetails.getProductDetails().getImages().get(0));
        return receiptDetailsResponse;
    }

    private ReceiptDetails convertToEntity(ReceiptDetailsRequest receiptDetailsRequest) {
        ReceiptDetails receiptDetails = modelMapper.map(receiptDetailsRequest, ReceiptDetails.class);
        receiptDetails.setReceipt(receiptRepository.findById(receiptDetailsRequest.getReceiptId()).orElse(null));
        receiptDetails.setProductDetails(productDetailsRepository.findById(receiptDetailsRequest.getProductDetailsId()).orElse(null));
        return modelMapper.map(receiptDetailsRequest, ReceiptDetails.class);
    }
}
