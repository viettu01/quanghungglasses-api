package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.AddressRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {
    List<AddressResponse> findByUserId(Long userId);

    AddressResponse create(AddressRequest addressRequest);

    AddressResponse update(AddressRequest addressRequest);

    void delete(Long id);
}