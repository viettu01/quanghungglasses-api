package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.request.CartDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CartResponse;
import fithou.tuplv.quanghungglassesapi.entity.Cart;
import fithou.tuplv.quanghungglassesapi.entity.CartDetails;
import fithou.tuplv.quanghungglassesapi.entity.ProductDetails;
import fithou.tuplv.quanghungglassesapi.mapper.CartMapper;
import fithou.tuplv.quanghungglassesapi.repository.CartDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.CartRepository;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Component
public class CartServiceImpl implements CartService {
    private final ProductDetailsRepository productDetailsRepository;
    final CartRepository cartRepository;
    final CartDetailsRepository cartDetailsRepository;
    final CartMapper cartMapper;
    final CustomerRepository customerRepository;
    final AuthenticationManager authenticationManager;

    @Override
    public CartResponse getCartByUserEmail() {
        Cart cart = cartRepository.findByCustomerAccountEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        // sap xep lai cart details theo updated date giam dan
        cart.getCartDetails().sort((o1, o2) -> o2.getUpdatedDate().compareTo(o1.getUpdatedDate()));

        return cartMapper.convertToResponse(cart);
    }

    @Override
    public CartResponse addProductToCart(CartDetailsRequest cartDetailsRequest) {
        ProductDetails productDetails = productDetailsRepository.findById(cartDetailsRequest.getProductDetailsId()).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        if (productDetails.getQuantity() <= 0)
            throw new RuntimeException("Sản phẩm đã hết hàng");

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Optional<CartDetails> cartDetails = cartDetailsRepository
                .findByCartAndProductDetailsId(
                        Objects.requireNonNull(customerRepository.findByAccountEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null)).getCart(),
                        cartDetailsRequest.getProductDetailsId());
        if (cartDetails.isPresent()) {
            if ((cartDetails.get().getQuantity() + cartDetailsRequest.getQuantity()) > productDetails.getQuantity())
                throw new RuntimeException("Số lượng sản phẩm trong giỏ hàng đã đạt giới hạn");

            cartDetails.get().setQuantity(cartDetails.get().getQuantity() + cartDetailsRequest.getQuantity());
            return cartMapper.convertToResponse(cartDetailsRepository.save(cartDetails.get()).getCart());
        }

        return cartMapper.convertToResponse(cartDetailsRepository.save(cartMapper.convertToEntity(cartDetailsRequest)).getCart());
    }

    @Override
    public void updateProductQuantity(CartDetailsRequest cartDetailsRequest) {
        ProductDetails productDetails = productDetailsRepository.findById(cartDetailsRequest.getProductDetailsId()).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
        if (cartDetailsRequest.getQuantity() > productDetails.getQuantity())
            throw new RuntimeException("Số lượng sản phẩm trong giỏ hàng đã đạt giới hạn");
        cartDetailsRepository.save(cartMapper.convertToEntity(cartDetailsRequest));
    }

    @Override
    public void plusProductQuantity(Long cartDetailsId) {
        CartDetails cartDetails = cartDetailsRepository.findById(cartDetailsId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));
        if (cartDetails.getQuantity() >= cartDetails.getProductDetails().getQuantity())
            throw new RuntimeException("Số lượng sản phẩm trong giỏ hàng đã đạt giới hạn");
        cartDetails.setQuantity(cartDetails.getQuantity() + 1);
        cartDetailsRepository.save(cartDetails);
    }

    @Override
    public void minusProductQuantity(Long cartDetailsId) {
        CartDetails cartDetails = cartDetailsRepository.findById(cartDetailsId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));
        if (cartDetails.getQuantity() <= 1)
            throw new RuntimeException("Số lượng sản phẩm trong giỏ hàng không thể nhỏ hơn 1");
        cartDetails.setQuantity(cartDetails.getQuantity() - 1);
        cartDetailsRepository.save(cartDetails);
    }

    @Override
    public void deleteCartDetails(Long cartDetailsId) {
        cartDetailsRepository.findById(cartDetailsId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm trong giỏ hàng"));
        cartDetailsRepository.deleteById(cartDetailsId);
    }
}