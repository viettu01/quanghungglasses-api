package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.RoleRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.RoleResponse;

public interface RoleService {
    RoleResponse save(RoleRequest roleRequest);
    boolean existsByName(String name);
}
