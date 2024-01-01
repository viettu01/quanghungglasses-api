package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.RegisterRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.UserRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.RegisterResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.UserResponse;

public interface UserService {
    UserResponse save(UserRequest userRequest);
    boolean existsByEmail(String email);
    RegisterResponse register(RegisterRequest registerRequest);
}