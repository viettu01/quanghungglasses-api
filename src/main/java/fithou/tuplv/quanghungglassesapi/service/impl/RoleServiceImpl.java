package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.RoleRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.RoleResponse;
import fithou.tuplv.quanghungglassesapi.mapper.RoleMapper;
import fithou.tuplv.quanghungglassesapi.repository.RoleRepository;
import fithou.tuplv.quanghungglassesapi.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    final RoleMapper roleMapper;
    final RoleRepository roleRepository;

    @Override
    public RoleResponse save(RoleRequest roleRequest) {
        return roleMapper.convertToResponse(roleRepository.save(roleMapper.convertToEntity(roleRequest)));
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepository.existsByName(name);
    }
}
