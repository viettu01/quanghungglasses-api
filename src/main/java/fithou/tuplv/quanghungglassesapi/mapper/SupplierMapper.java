package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.SupplierRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.SupplierResponse;
import fithou.tuplv.quanghungglassesapi.entity.Supplier;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SupplierMapper {
    final ModelMapper modelMapper;

    public SupplierResponse convertToResponse(Supplier supplier) {
        return modelMapper.map(supplier, SupplierResponse.class);
    }

    public Supplier convertToEntity(SupplierRequest supplierRequest) {
        return modelMapper.map(supplierRequest, Supplier.class);
    }
}
