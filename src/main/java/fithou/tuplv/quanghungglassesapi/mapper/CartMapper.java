package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.CartDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CartDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CartResponse;
import fithou.tuplv.quanghungglassesapi.entity.Cart;
import fithou.tuplv.quanghungglassesapi.entity.CartDetails;
import fithou.tuplv.quanghungglassesapi.repository.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class CartMapper {
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final SaleRepository saleRepository;
    private final SaleDetailsRepository saleDetailsRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final ProductDetailsRepository productDetailsRepository;

    public CartResponse convertToResponse(Cart cart) {
        CartResponse cartResponse = modelMapper.map(cart, CartResponse.class);
        cartResponse.getCartDetails().clear();
        cartResponse.setTotalProduct(cart.getCartDetails().size());
        cart.getCartDetails().forEach(cartDetails -> {
            cartResponse.getCartDetails().add(convertToResponse(cartDetails));
        });
//        cart.getCartDetails().forEach(cartDetails -> cartResponse.getCartDetails().add(convertToResponse(cartDetails)));
        return cartResponse;
    }

    public CartDetailsResponse convertToResponse(CartDetails cartDetails) {
        CartDetailsResponse cartDetailsResponse = modelMapper.map(cartDetails, CartDetailsResponse.class);
        cartDetailsResponse.setProductDetailsId(cartDetails.getProductDetails().getId());
        cartDetailsResponse.setProductDetailsThumbnails(cartDetails.getProductDetails().getProduct().getThumbnail());
        cartDetailsResponse.setProductName(cartDetails.getProductDetails().getProduct().getName());
        cartDetailsResponse.setProductSlug(cartDetails.getProductDetails().getProduct().getSlug());
        cartDetailsResponse.setProductColor(cartDetails.getProductDetails().getColor());

        // Lấy giá sau khi đã giảm giá từ bảng sale và product_sale kiểm tra xem thời gian hiện tại có nằm trong thời gian sale không
        double price = cartDetails.getProductDetails().getProduct().getPrice();
//        Date now = new Date();
//        Optional<Sale> sale = saleRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now);
//        if (sale.isPresent()) {
//            Optional<SaleDetails> productSale = saleDetailsRepository.findByProductAndSale(cartDetails.getProductDetails().getProduct(), sale.get());
//            if (productSale.isPresent())
//                price = price * ((double) (100 - productSale.get().getDiscount()) / 100);
//        }
        cartDetailsResponse.setProductPrice(price);

        return cartDetailsResponse;
    }

    public CartDetails convertToEntity(CartDetailsRequest cartDetailsRequest) {
        CartDetails cartDetails = modelMapper.map(cartDetailsRequest, CartDetails.class);
        cartDetails.setCart(Objects.requireNonNull(customerRepository
                        .findByAccountEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null))
                .getCart());
        cartDetails.setProductDetails(productDetailsRepository.findById(cartDetailsRequest.getProductDetailsId()).orElse(null));
        return cartDetails;
    }
}
