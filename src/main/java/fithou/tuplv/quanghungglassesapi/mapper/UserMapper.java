package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.RegisterRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.UserRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.RegisterResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.RoleResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.UserResponse;
import fithou.tuplv.quanghungglassesapi.entity.User;
import fithou.tuplv.quanghungglassesapi.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {
    final ModelMapper modelMapper;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

    public UserResponse convertToResponse(User user) {
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        user.getRoles().forEach(role -> userResponse.getRoles().add(modelMapper.map(role, RoleResponse.class)));
        return modelMapper.map(user, UserResponse.class);
    }

    public User convertToEntity(RegisterRequest registerRequest) {
        User user = modelMapper.map(registerRequest, User.class);
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        user.setStatus(true);
        return user;
    }

    public User convertToEntity(UserRequest userRequest) {
        User userEntity = modelMapper.map(userRequest, User.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(userRequest.getRoleName().stream()
                .map(roleRepository::findByName)
                .collect(Collectors.toList())
        );
        userEntity.setStatus(true);
        return userEntity;
    }

    public RegisterResponse convertToRegisterResponse(User user) {
        RegisterResponse registerResponse = modelMapper.map(user, RegisterResponse.class);
        registerResponse.setRole(user.getRoles().get(0).getName());
        return registerResponse;
    }
}
