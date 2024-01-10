package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.AddressRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AddressResponse;
import fithou.tuplv.quanghungglassesapi.mapper.AddressMapper;
import fithou.tuplv.quanghungglassesapi.repository.AddressRepository;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_ADDRESS_NOT_FOUND;
import static fithou.tuplv.quanghungglassesapi.utils.Constants.ERROR_USER_NOT_FOUND;

@Service
@Transactional
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    final AddressMapper addressMapper;
    final AddressRepository addressRepository;
    final CustomerRepository customerRepository;

    @Override
    public List<AddressResponse> findByUserId(Long userId) {
        if (!customerRepository.existsById(userId))
            throw new RuntimeException(ERROR_USER_NOT_FOUND);

        return addressRepository.findByCustomerId(userId)
                .stream()
                .map(addressMapper::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AddressResponse create(AddressRequest addressRequest) {
        if (!customerRepository.existsById(addressRequest.getCustomerId()))
            throw new RuntimeException(ERROR_USER_NOT_FOUND);

        return addressMapper.convertToResponse(addressRepository.save(addressMapper.convertToEntity(addressRequest)));
    }

    @Override
    public AddressResponse update(AddressRequest addressRequest) {
        if (!addressRepository.existsById(addressRequest.getId()))
            throw new RuntimeException(ERROR_ADDRESS_NOT_FOUND);

        if (!customerRepository.existsById(addressRequest.getCustomerId()))
            throw new RuntimeException(ERROR_USER_NOT_FOUND);

        return addressMapper.convertToResponse(addressRepository.save(addressMapper.convertToEntity(addressRequest)));
    }

    @Override
    public void delete(Long id) {
        if (!addressRepository.existsById(id))
            throw new RuntimeException(ERROR_ADDRESS_NOT_FOUND);

        addressRepository.deleteById(id);
    }
}
