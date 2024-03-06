package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ReceiptRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ReceiptResponse;
import org.springframework.data.domain.Pageable;

public interface ReceiptService {
    PaginationDTO<ReceiptResponse> findByStaffFullnameContaining(String fullname, Pageable pageable);

    ReceiptResponse findById(Long id);

    ReceiptResponse create(ReceiptRequest receiptRequest);

    ReceiptResponse updateStatus(Long id, Boolean status);
}
