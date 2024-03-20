package fithou.tuplv.quanghungglassesapi.service;

import fithou.tuplv.quanghungglassesapi.dto.request.CartDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CartDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CartResponse;

public interface CartService {
    CartResponse getCartByUserEmail();

    CartResponse addProductToCart(CartDetailsRequest cartDetailsRequest);

    void deleteCartDetails(Long cartDetailsId);

    void updateProductQuantity(CartDetailsRequest cartDetailsRequest);

    void plusProductQuantity(Long cartDetailsId);

    void minusProductQuantity(Long cartDetailsId);
}
