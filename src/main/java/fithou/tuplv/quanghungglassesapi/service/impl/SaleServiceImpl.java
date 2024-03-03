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

import java.util.List;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_SALE_NOT_FOUND;

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
        saleRequest.getSaleDetails().forEach(saleDetailsRequest -> {
            if (saleRequest.getSaleDetails().stream().filter(saleDetailsRequest1 -> saleDetailsRequest1.getProductId().equals(saleDetailsRequest.getProductId())).count() > 1) {
                throw new RuntimeException("Mã sản phẩm không được trùng nhau");
            }
        });

        // Thoi gian bat dau chuong trinh khuyen mai phai truoc thoi gian ket thuc
        if (saleRequest.getStartDate().after(saleRequest.getEndDate())) {
            throw new RuntimeException("Thời gian bắt đầu không được sau thời gian kết thúc");
        }

        // Kiem tra thoi gian bat dau chuong trinh giam gia moi phai sau thoi gian ket thuc chuong trinh giam gia cu
        saleRepository.findAll().forEach(sale -> {
            if (sale.getEndDate().after(saleRequest.getStartDate())) {
                throw new RuntimeException("Thời gian bắt đầu chương trình khuyến mãi mới phải sau thời gian kết thúc chương trình khuyến mãi cũ");
            }
        });

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

        saleRequest.getSaleDetails().forEach(saleDetailsRequest -> {
            if (saleRequest.getSaleDetails().stream().filter(saleDetailsRequest1 -> saleDetailsRequest1.getProductId().equals(saleDetailsRequest.getProductId())).count() > 1) {
                throw new RuntimeException("Mã sản phẩm không được trùng nhau");
            }
        });

        // Thoi gian bat dau chuong trinh khuyen mai phai truoc thoi gian ket thuc
        if (saleRequest.getStartDate().after(saleRequest.getEndDate())) {
            throw new RuntimeException("Thời gian bắt đầu không được sau thời gian kết thúc");
        }

        // Kiem tra thoi gian dang chinh sua phai khac thoi gian hien tai chua chinh sua thi moi kiem tra thoi gian bat dau chuong trinh giam gia moi phai sau thoi gian ket thuc chuong trinh giam gia cu
        if (!saleRequest.getStartDate().equals(saleExists.getStartDate()) || !saleRequest.getEndDate().equals(saleExists.getEndDate())) {
            saleRepository.findAll().forEach(sale1 -> {
                if (sale1.getEndDate().after(saleRequest.getStartDate())) {
                    throw new RuntimeException("Thời gian bắt đầu chương trình khuyến mãi mới phải sau thời gian kết thúc chương trình khuyến mãi cũ");
                }
            });
        }

        Sale saleUpdate = saleRepository.save(saleMapper.convertToEntity(saleRequest));
        for (SaleDetailsRequest saleDetailsRequest : saleRequest.getSaleDetails()) {
            SaleDetails saleDetails = saleDetailsRepository.findByProductIdAndSaleId(saleDetailsRequest.getProductId(), saleUpdate.getId()).orElse(null);
            if (saleDetails == null) {
                saleDetails = saleMapper.convertToEntity(saleDetailsRequest);
                saleDetails.setSale(saleUpdate);
            } else {
                saleDetails.setDiscount(saleDetailsRequest.getDiscount());
            }
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

    private void setTimes(SaleRequest saleRequest) {
        saleRequest.getStartDate().setHours(0);
        saleRequest.getStartDate().setMinutes(0);
        saleRequest.getStartDate().setSeconds(0);
        saleRequest.getEndDate().setHours(23);
        saleRequest.getEndDate().setMinutes(59);
        saleRequest.getEndDate().setSeconds(59);
    }
}
