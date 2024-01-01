package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.OriginRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ShapeRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.OriginResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.ShapeResponse;
import fithou.tuplv.quanghungglassesapi.entity.Origin;
import fithou.tuplv.quanghungglassesapi.entity.Shape;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShapeMapper {
    final ModelMapper modelMapper;

    public ShapeResponse convertToResponse(Shape shape) {
        return modelMapper.map(shape, ShapeResponse.class);
    }

    public Shape convertToEntity(ShapeRequest shapeRequest) {
        return modelMapper.map(shapeRequest, Shape.class);
    }
}
