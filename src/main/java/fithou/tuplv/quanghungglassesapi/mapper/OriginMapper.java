package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.OriginRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OriginResponse;
import fithou.tuplv.quanghungglassesapi.entity.Origin;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OriginMapper {
    final ModelMapper modelMapper;

    public OriginResponse convertToResponse(Origin origin) {
        return modelMapper.map(origin, OriginResponse.class);
    }

    public Origin convertToEntity(OriginRequest originRequest) {
        return modelMapper.map(originRequest, Origin.class);
    }
}
