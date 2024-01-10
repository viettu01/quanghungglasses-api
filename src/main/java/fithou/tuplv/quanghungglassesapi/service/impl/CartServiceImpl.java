package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.CartDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CartDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CartResponse;
import fithou.tuplv.quanghungglassesapi.entity.CartDetails;
import fithou.tuplv.quanghungglassesapi.mapper.CartMapper;
import fithou.tuplv.quanghungglassesapi.repository.CartDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.CartRepository;
import fithou.tuplv.quanghungglassesapi.service.CartService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    final CartRepository cartRepository;
    final CartDetailsRepository cartDetailsRepository;
    final CartMapper cartMapper;

    @Override
    public CartResponse getCartByUserEmail(String email) {
        return cartMapper.convertToResponse(cartRepository.findByCustomerEmail(email));
    }

    @Override
    public CartDetailsResponse addProductToCart(CartDetailsRequest cartDetailsRequest) {
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartDetails> cartDetails = cartDetailsRepository.findByCartIdAndProductDetailsId(cartDetailsRequest.getCartId(), cartDetailsRequest.getProductDetailsId());
        if (cartDetails.isPresent()) {
            // Nếu có rồi thì tăng số lượng lên 1
            cartDetails.get().setQuantity(cartDetails.get().getQuantity() + 1);
            return cartMapper.convertToResponse(cartDetailsRepository.save(cartDetails.get()));
        }

        return cartMapper.convertToResponse(cartDetailsRepository.save(cartMapper.convertToEntity(cartDetailsRequest)));
    }

    @Override
    public CartDetailsResponse updateProductQuantity(CartDetailsRequest cartDetailsRequest) {
        return cartMapper.convertToResponse(cartDetailsRepository.save(cartMapper.convertToEntity(cartDetailsRequest)));
    }

    @Override
    public void deleteCartDetails(Long[] cartDetailsId) {
        for (Long id : cartDetailsId) {
            try {
                cartDetailsRepository.deleteById(id);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}