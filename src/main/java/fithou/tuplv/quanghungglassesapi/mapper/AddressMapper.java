package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.AddressRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.AddressResponse;
import fithou.tuplv.quanghungglassesapi.entity.Address;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AddressMapper {
    final ModelMapper modelMapper;
    final CustomerRepository customerRepository;

    public AddressResponse convertToResponse(Address address) {
        return modelMapper.map(address, AddressResponse.class);
    }

    public Address convertToEntity(AddressRequest addressRequest) {
        Address address = modelMapper.map(addressRequest, Address.class);
        address.setCustomer(customerRepository.findById(addressRequest.getCustomerId()).orElse(null));
        return address;
    }
}
