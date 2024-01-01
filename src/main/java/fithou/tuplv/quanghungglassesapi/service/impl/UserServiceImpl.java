package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.RegisterRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.UserRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.RegisterResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.UserResponse;
import fithou.tuplv.quanghungglassesapi.entity.Cart;
import fithou.tuplv.quanghungglassesapi.entity.User;
import fithou.tuplv.quanghungglassesapi.mapper.UserMapper;
import fithou.tuplv.quanghungglassesapi.repository.CartRepository;
import fithou.tuplv.quanghungglassesapi.repository.RoleRepository;
import fithou.tuplv.quanghungglassesapi.repository.UserRepository;
import fithou.tuplv.quanghungglassesapi.security.CustomUserDetails;
import fithou.tuplv.quanghungglassesapi.security.jwt.JwtTokenProvider;
import fithou.tuplv.quanghungglassesapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_USER_NOT_FOUND;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final CartRepository cartRepository;
    final JwtTokenProvider tokenProvider;
    final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException(ERROR_USER_NOT_FOUND);

        return new CustomUserDetails(user);
    }

    @Override
    public UserResponse save(UserRequest userRequest) {
        User user = userRepository.save(userMapper.convertToEntity(userRequest));
        UserResponse userResponse = userMapper.convertToResponse(user);
        cartRepository.save(new Cart(null, user, null));
        return userResponse;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        return userMapper.convertToRegisterResponse(userRepository.save(userMapper.convertToEntity(registerRequest)));
    }
}
