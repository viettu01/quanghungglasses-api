package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ReceiptDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ReceiptRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ReceiptResponse;
import fithou.tuplv.quanghungglassesapi.entity.ProductDetails;
import fithou.tuplv.quanghungglassesapi.entity.Receipt;
import fithou.tuplv.quanghungglassesapi.entity.ReceiptDetails;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.mapper.ReceiptMapper;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.ReceiptDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.ReceiptRepository;
import fithou.tuplv.quanghungglassesapi.service.ReceiptService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_RECEIPT_NOT_FOUND;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_RECEIPT_PRODUCT_DETAILS_EXIST;

@Service
@Transactional
@AllArgsConstructor
public class ReceiptServiceImpl implements ReceiptService {
    final ReceiptRepository receiptRepository;
    final ReceiptDetailsRepository receiptDetailsRepository;
    final ProductDetailsRepository productDetailsRepository;
    final ReceiptMapper receiptMapper;
    final PaginationMapper paginationMapper;

    @Override
    public PaginationDTO<ReceiptResponse> findByStaffFullnameContaining(String fullname, Pageable pageable) {
        return paginationMapper
                .mapToPaginationDTO(receiptRepository.findByStaffFullnameContaining(fullname, pageable)
                        .map(receiptMapper::convertToResponse));
    }

    @Override
    public ReceiptResponse findById(Long id) {
        return receiptMapper.convertToResponse(receiptRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_RECEIPT_NOT_FOUND)));
    }

    @Override
    public ReceiptResponse create(ReceiptRequest receiptRequest) {
        if (receiptRequest.getReceiptDetails().stream().map(ReceiptDetailsRequest::getProductDetailsId).collect(Collectors.toSet()).size()
                != receiptRequest.getReceiptDetails().size())
            throw new RuntimeException(ERROR_RECEIPT_PRODUCT_DETAILS_EXIST);

        Receipt receipt = receiptMapper.convertToEntity(receiptRequest);
        receiptRepository.save(receipt);
        receipt.getReceiptDetails().clear();
        receiptRequest.getReceiptDetails().forEach(receiptDetailsRequest -> {
            ReceiptDetails receiptDetails = receiptMapper.convertToEntity(receiptDetailsRequest);
            receiptDetails.setReceipt(receipt);
            receipt.getReceiptDetails().add(receiptDetailsRepository.save(receiptDetails));
            // cập nhật số lượng sản phẩm trong kho
            ProductDetails productDetails = productDetailsRepository.findById(receiptDetailsRequest.getProductDetailsId())
                    .orElseThrow(() -> new RuntimeException(ERROR_RECEIPT_NOT_FOUND));
            productDetails.setQuantity(productDetails.getQuantity() + receiptDetailsRequest.getQuantity());
            productDetailsRepository.save(productDetails);
        });

        return receiptMapper.convertToResponse(receipt);
    }

    @Override
    public ReceiptResponse updateStatus(Long id, Boolean status) {
        Receipt receipt = receiptRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_RECEIPT_NOT_FOUND));
        receipt.setStatus(status);
        return receiptMapper.convertToResponse(receiptRepository.save(receipt));
    }
}
