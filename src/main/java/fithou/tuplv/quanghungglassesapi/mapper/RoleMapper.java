package fithou.tuplv.quanghungglassesapi.mapper;


import fithou.tuplv.quanghungglassesapi.dto.request.RoleRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.RoleResponse;
import fithou.tuplv.quanghungglassesapi.entity.Role;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RoleMapper {
    final ModelMapper modelMapper;

    public RoleResponse convertToResponse(Role roleEntity) {
        return modelMapper.map(roleEntity, RoleResponse.class);
    }

    public Role convertToEntity(RoleRequest roleRequest) {
        return modelMapper.map(roleRequest, Role.class);
    }
}
