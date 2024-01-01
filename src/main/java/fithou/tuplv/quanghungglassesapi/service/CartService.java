package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.CartDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CartDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CartResponse;

public interface CartService {
    CartResponse getCartByUserEmail(String email);

    CartDetailsResponse addProductToCart(CartDetailsRequest cartDetailsRequest);

    void deleteCartDetails(Long[] cartDetailsId);

    CartDetailsResponse updateProductQuantity(CartDetailsRequest cartDetailsRequest);
}
