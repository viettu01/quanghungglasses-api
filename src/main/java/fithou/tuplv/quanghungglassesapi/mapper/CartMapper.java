package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.CartDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CartDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CartResponse;
import fithou.tuplv.quanghungglassesapi.entity.Cart;
import fithou.tuplv.quanghungglassesapi.entity.CartDetails;
import fithou.tuplv.quanghungglassesapi.entity.Sale;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.SaleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class CartMapper {
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;
    private final ProductDetailsRepository productDetailsRepository;

    public CartResponse convertToResponse(Cart cart) {
        CartResponse cartResponse = modelMapper.map(cart, CartResponse.class);
        cartResponse.getCartDetails().clear();
        cartResponse.setTotalProduct(cart.getCartDetails().size());
        cart.getCartDetails().forEach(cartDetails -> {
            cartResponse.getCartDetails().add(convertToResponse(cartDetails));
        });
        return cartResponse;
    }

    public CartDetailsResponse convertToResponse(CartDetails cartDetails) {
        CartDetailsResponse cartDetailsResponse = modelMapper.map(cartDetails, CartDetailsResponse.class);
        cartDetailsResponse.setProductDetailsId(cartDetails.getProductDetails().getId());
        cartDetailsResponse.setProductDetailsThumbnails(cartDetails.getProductDetails().getProduct().getThumbnail());
        cartDetailsResponse.setProductName(cartDetails.getProductDetails().getProduct().getName());
        cartDetailsResponse.setProductSlug(cartDetails.getProductDetails().getProduct().getSlug());
        cartDetailsResponse.setProductColor(cartDetails.getProductDetails().getColor());
        cartDetailsResponse.setQuantityInStock(cartDetails.getProductDetails().getQuantity());

        // Lấy giá sau khi đã giảm giá từ bảng sale và product_sale kiểm tra xem thời gian hiện tại có nằm trong thời gian sale không
        cartDetailsResponse.setProductPrice(cartDetails.getProductDetails().getProduct().getPrice());
        Date now = new Date();
        // Hàm trả về danh sách chương trình khuyến mãi nào đang diễn ra trong khoảng thời gian
        List<Sale> salesExists = saleRepository.findByStartDateBetweenOrEndDateBetweenOrStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now, now, now, now, now);
        salesExists.forEach(sale -> {
            sale.getSaleDetails().forEach(saleDetails -> {
                if (saleDetails.getProduct().getId().equals(cartDetails.getProductDetails().getProduct().getId())) {
                    Double priceDiscount = saleDetails.getProduct().getPrice() * ((100 - saleDetails.getDiscount()) / 100);
                    cartDetailsResponse.setProductPrice(priceDiscount);
                }
            });
        });


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
