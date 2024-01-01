package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.BrandRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.OriginRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.BrandResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.OriginResponse;
import fithou.tuplv.quanghungglassesapi.entity.Brand;
import fithou.tuplv.quanghungglassesapi.entity.Origin;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BrandMapper {
    final ModelMapper modelMapper;

    public BrandResponse convertToResponse(Brand brand) {
        return modelMapper.map(brand, BrandResponse.class);
    }

    public Brand convertToEntity(BrandRequest brandRequest) {
        return modelMapper.map(brandRequest, Brand.class);
    }
}
