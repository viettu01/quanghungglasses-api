package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.WarrantyDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.WarrantyRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.WarrantyResponse;
import fithou.tuplv.quanghungglassesapi.entity.Order;
import fithou.tuplv.quanghungglassesapi.entity.OrderDetails;
import fithou.tuplv.quanghungglassesapi.entity.Warranty;
import fithou.tuplv.quanghungglassesapi.entity.WarrantyDetails;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.mapper.WarrantyMapper;
import fithou.tuplv.quanghungglassesapi.repository.OrderRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.WarrantyDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.WarrantyRepository;
import fithou.tuplv.quanghungglassesapi.service.WarrantyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_ORDER_NOT_FOUND;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_WARRANTY_NOT_FOUND;

@Service
@Transactional
@AllArgsConstructor
public class WarrantyServiceImpl implements WarrantyService {
    final WarrantyRepository warrantyRepository;
    final WarrantyDetailsRepository warrantyDetailsRepository;
    final OrderRepository orderRepository;
    final ProductDetailsRepository productDetailsRepository;
    final PaginationMapper paginationMapper;
    final WarrantyMapper warrantyMapper;

    @Override
    public PaginationDTO<WarrantyResponse> findByCustomerFullname(String fullname, Pageable pageable) {
        return paginationMapper
                .mapToPaginationDTO(warrantyRepository.findByCustomerFullnameContaining(fullname, pageable)
                        .map(warrantyMapper::convertToResponse));
//        return paginationMapper
//                .mapToPaginationDTO(warrantyRepository.findAll(pageable)
//                        .map(warrantyMapper::convertToResponse));
    }

    @Override
    public WarrantyResponse findById(Long id) {
        Warranty warranty = warrantyRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_WARRANTY_NOT_FOUND));
        return warrantyMapper.convertToResponse(warranty);
    }

    @Override
    public WarrantyResponse create(WarrantyRequest warrantyRequest) {
        for (WarrantyDetailsRequest warrantyDetailsRequest : warrantyRequest.getWarrantyDetails()) {
            Order order = orderRepository.findById(warrantyDetailsRequest.getOrderId()).orElseThrow(() -> new RuntimeException(ERROR_ORDER_NOT_FOUND));
            for (OrderDetails orderDetails : order.getOrderDetails()) {
                if (orderDetails.getProductDetails().getId().equals(warrantyDetailsRequest.getProductDetailsId())) {
                    if (warrantyDetailsRequest.getQuantity() > orderDetails.getQuantity()) {
                        throw new RuntimeException("Số lượng sản phẩm bảo hành không được lớn hơn số lượng sản phẩm đã mua");
                    }
                }
            }
        }
        Warranty warranty = warrantyMapper.convertToEntity(warrantyRequest);
        warrantyRepository.save(warranty);
        warranty.getWarrantyDetails().clear();
        warrantyRequest.getWarrantyDetails().forEach(warrantyDetailsRequest -> {
            WarrantyDetails warrantyDetails = warrantyMapper.convertToEntity(warrantyDetailsRequest);
            warrantyDetails.setWarranty(warranty);
            warrantyDetailsRepository.save(warrantyDetails);
            warranty.getWarrantyDetails().add(warrantyDetails);
            if (warranty.getStatus() && (warrantyDetails.getWarrantyType() == 1 || warrantyDetails.getWarrantyType() == 2)) {
                productDetailsRepository.findById(warrantyDetails.getProductDetails().getId()).ifPresent(productDetails -> {
                    productDetails.setQuantity(productDetails.getQuantity() + warrantyDetails.getQuantity());
                    productDetailsRepository.save(productDetails);
                });
            }
        });
        return warrantyMapper.convertToResponse(warranty);
    }

    @Override
    public void update(Long id, Boolean status) {
        Warranty warranty = warrantyRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_WARRANTY_NOT_FOUND));
        warranty.setStatus(status);
        if (status) {
            warranty.getWarrantyDetails().forEach(warrantyDetails -> {
                if (warrantyDetails.getWarrantyType() == 1 || warrantyDetails.getWarrantyType() == 2) {
                    productDetailsRepository.findById(warrantyDetails.getProductDetails().getId()).ifPresent(productDetails -> {
                        productDetails.setQuantity(productDetails.getQuantity() + warrantyDetails.getQuantity());
                        productDetailsRepository.save(productDetails);
                    });
                }
            });
        }
        warrantyRepository.save(warranty);
    }
}
