package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.MaterialRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.MaterialResponse;
import fithou.tuplv.quanghungglassesapi.entity.Material;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MaterialMapper {
    final ModelMapper modelMapper;

    public MaterialResponse convertToResponse(Material material) {
        return modelMapper.map(material, MaterialResponse.class);
    }

    public Material convertToEntity(MaterialRequest materialRequest) {
        return modelMapper.map(materialRequest, Material.class);
    }
}
