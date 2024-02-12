package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.SupplierRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.SupplierResponse;
import fithou.tuplv.quanghungglassesapi.entity.Supplier;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.mapper.SupplierMapper;
import fithou.tuplv.quanghungglassesapi.repository.SupplierRepository;
import fithou.tuplv.quanghungglassesapi.service.SupplierService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    final SupplierRepository supplierRepository;
    final PaginationMapper paginationMapper;
    final SupplierMapper supplierMapper;

    @Override
    public List<SupplierResponse> findAll() {
        return supplierRepository.findAll().stream().map(supplierMapper::convertToResponse).collect(Collectors.toList());
    }

    @Override
    public PaginationDTO<SupplierResponse> findAll(Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(supplierRepository.findAll(pageable).map(supplierMapper::convertToResponse));
    }

    @Override
    public PaginationDTO<SupplierResponse> findByNameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(supplierRepository.findByNameContaining(name, pageable).map(supplierMapper::convertToResponse));
    }

    @Override
    public SupplierResponse findById(Long id) {
        return supplierMapper.convertToResponse(supplierRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_SUPPLIER_NOT_FOUND)));
    }

    @Override
    public SupplierResponse create(SupplierRequest supplierRequest) {
        if (supplierRepository.existsByName(supplierRequest.getName()))
            throw new RuntimeException(ERROR_SUPPLIER_NAME_ALREADY_EXISTS);
        if (supplierRepository.existsByPhone(supplierRequest.getPhone()))
            throw new RuntimeException(ERROR_PHONE_ALREADY_EXISTS);
        return supplierMapper.convertToResponse(supplierRepository.save(supplierMapper.convertToEntity(supplierRequest)));
    }

    @Override
    public SupplierResponse update(SupplierRequest supplierRequest) {
        Supplier supplier = supplierRepository.findById(supplierRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_SUPPLIER_NOT_FOUND));
        if (!supplier.getName().equalsIgnoreCase(supplierRequest.getName()) && supplierRepository.existsByName(supplierRequest.getName()))
            throw new RuntimeException(ERROR_SUPPLIER_NAME_ALREADY_EXISTS);
        if (!supplier.getPhone().equalsIgnoreCase(supplierRequest.getPhone()) && supplierRepository.existsByPhone(supplierRequest.getPhone()))
            throw new RuntimeException(ERROR_PHONE_ALREADY_EXISTS);
        return supplierMapper.convertToResponse(supplierRepository.save(supplierMapper.convertToEntity(supplierRequest)));
    }

    @Override
    public void deleteById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_SUPPLIER_NOT_FOUND));
        if (!supplier.getReceipts().isEmpty())
            throw new RuntimeException(ERROR_SUPPLIER_HAS_RECEIPT);
        supplierRepository.deleteById(id);
    }

    @Override
    public void deleteByIds(Long[] ids) {
        for (Long id : ids) {
            try {
                supplierRepository.deleteById(id);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public boolean existsByName(String name) {
        return supplierRepository.existsByName(name);
    }
}
