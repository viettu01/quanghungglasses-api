package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.SaleDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.SaleRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.SaleResponse;
import fithou.tuplv.quanghungglassesapi.entity.Sale;
import fithou.tuplv.quanghungglassesapi.entity.SaleDetails;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.mapper.SaleMapper;
import fithou.tuplv.quanghungglassesapi.repository.SaleDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.SaleRepository;
import fithou.tuplv.quanghungglassesapi.service.SaleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class SaleServiceImpl implements SaleService {
    final SaleMapper saleMapper;
    final PaginationMapper paginationMapper;
    final SaleRepository saleRepository;
    final SaleDetailsRepository saleDetailsRepository;

    @Override
    public List<SaleResponse> findAll() {
        return saleRepository.findAll().stream().map(saleMapper::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public PaginationDTO<SaleResponse> findAll(Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(saleRepository.findAll(pageable).map(saleMapper::convertToResponse));
    }

    @Override
    public PaginationDTO<SaleResponse> findByNameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(saleRepository.findByNameContaining(name, pageable).map(saleMapper::convertToResponse));
    }

    @Override
    public SaleResponse findById(Long id) {
        return saleMapper.convertToResponse(saleRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_SALE_NOT_FOUND)));
    }

    @Override
    public SaleResponse create(SaleRequest saleRequest) {
        setTimes(saleRequest);
        if (saleRepository.existsByName(saleRequest.getName()))
            throw new RuntimeException(ERROR_SALE_NAME_ALREADY_EXISTS);
        if (saleRequest.getStartDate().after(saleRequest.getEndDate()))
            throw new RuntimeException(ERROR_SALE_START_DATE_AFTER_END_DATE);
        saleRequest.getSaleDetails().forEach(saleDetailsRequest -> {
            if (saleRequest.getSaleDetails().stream().filter(saleDetailsRequest1 -> saleDetailsRequest1.getProductId().equals(saleDetailsRequest.getProductId())).count() > 1) {
                throw new RuntimeException(ERROR_SALE_PRODUCT_ALREADY_EXISTS);
            }
        });

        // Hàm trả về danh sách chương trình khuyến mãi nào đang diễn ra trong khoảng thời gian mới tạo
        List<Sale> salesExists = saleRepository.findByStartDateBetweenOrEndDateBetween(
                saleRequest.getStartDate(), saleRequest.getEndDate(),
                saleRequest.getStartDate(), saleRequest.getEndDate());
        if (!salesExists.isEmpty()) {
            // Kiem tra danh sach san pham cua chuong trinh khuyen mai moi tao co trung voi chuong trinh khuyen mai dang dien ra khong
            Set<Long> productIds = new HashSet<>();
            saleRequest.getSaleDetails().forEach(saleDetailsRequest -> productIds.add(saleDetailsRequest.getProductId()));
            salesExists.forEach(sale -> {
                sale.getSaleDetails().forEach(saleDetails -> {
                    if (productIds.contains(saleDetails.getProduct().getId())) {
                        throw new RuntimeException("Sản phẩm '" + saleDetails.getProduct().getName() + "' đã tồn tại trong chương trình khuyến mãi đang diễn ra");
                    }
                });
            });
        }

        Sale sale = saleRepository.save(saleMapper.convertToEntity(saleRequest));
        sale.getSaleDetails().clear();
        saleRequest.getSaleDetails().forEach(saleDetailsRequest -> {
            SaleDetails saleDetails = saleMapper.convertToEntity(saleDetailsRequest);
            saleDetails.setSale(sale);
            sale.getSaleDetails().add(saleDetailsRepository.save(saleDetails));
        });
        return saleMapper.convertToResponse(sale);
    }

    @Override
    public SaleResponse update(SaleRequest saleRequest) {
        setTimes(saleRequest);
        Sale saleExists = saleRepository.findById(saleRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_SALE_NOT_FOUND));
        if (!saleExists.getName().equals(saleRequest.getName()) && saleRepository.existsByName(saleRequest.getName()))
            throw new RuntimeException(ERROR_SALE_NAME_ALREADY_EXISTS);
        if (saleRequest.getStartDate().after(saleRequest.getEndDate()))
            throw new RuntimeException(ERROR_SALE_START_DATE_AFTER_END_DATE);
        saleRequest.getSaleDetails().forEach(saleDetailsRequest -> {
            if (saleRequest.getSaleDetails().stream().filter(saleDetailsRequest1 -> saleDetailsRequest1.getProductId().equals(saleDetailsRequest.getProductId())).count() > 1) {
                throw new RuntimeException(ERROR_SALE_PRODUCT_ALREADY_EXISTS);
            }
        });

        // Hàm trả về danh sách chương trình khuyến mãi nào đang diễn ra trong khoảng thời gian mới tạo
        List<Sale> salesExists = saleRepository.findByStartDateBetweenOrEndDateBetween(
                saleRequest.getStartDate(), saleRequest.getEndDate(),
                saleRequest.getStartDate(), saleRequest.getEndDate());
        if (!salesExists.isEmpty()) {
            // Kiem tra danh sach san pham cua chuong trinh khuyen mai moi tao co trung voi chuong trinh khuyen mai dang dien ra khong
            Set<Long> productIdsExists = new HashSet<>();
            Set<Long> productIdsRequest = new HashSet<>();
            saleExists.getSaleDetails().forEach(saleDetails -> productIdsExists.add(saleDetails.getProduct().getId()));
            saleRequest.getSaleDetails().forEach(saleDetailsRequest2 -> productIdsRequest.add(saleDetailsRequest2.getProductId()));
            productIdsRequest.forEach(productIdRequest -> {
                if (!productIdsExists.contains(productIdRequest)) {
                    salesExists.forEach(sale -> {
                        sale.getSaleDetails().forEach(saleDetails2 -> {
                            if (saleDetails2.getProduct().getId().equals(productIdRequest)) {
                                throw new RuntimeException("Sản phẩm '" + saleDetails2.getProduct().getName() + "' đã tồn tại trong chương trình khuyến mãi đang diễn ra");
                            }
                        });
                    });
                }
            });
        }

        Sale saleUpdate = saleRepository.save(saleMapper.convertToEntity(saleRequest));
        for (SaleDetailsRequest saleDetailsRequest : saleRequest.getSaleDetails()) {
            SaleDetails saleDetails = saleMapper.convertToEntity(saleDetailsRequest);
            if (Objects.isNull(saleDetails.getId())) {
                saleDetails.setId(0L);
            }
            saleDetails.setSale(saleUpdate);
            saleDetailsRepository.save(saleDetails);
        }
        return saleMapper.convertToResponse(saleRepository.save(saleUpdate));
    }

    @Override
    public void deleteById(Long id) {
        Sale sale = saleRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_SALE_NOT_FOUND));
        sale.getSaleDetails().forEach(saleDetails -> saleDetailsRepository.deleteById(saleDetails.getId()));
        saleRepository.deleteById(id);
    }

    @Override
    public void deleteSaleDetailsById(Long id) {
        saleDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm khuyến mãi"));
        saleDetailsRepository.deleteById(id);
    }

    private void setTimes(SaleRequest saleRequest) {
        saleRequest.getStartDate().setHours(0);
        saleRequest.getStartDate().setMinutes(0);
        saleRequest.getStartDate().setSeconds(0);
        saleRequest.getEndDate().setHours(23);
        saleRequest.getEndDate().setMinutes(59);
        saleRequest.getEndDate().setSeconds(59);
    }
}
