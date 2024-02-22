package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.CartDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.CartRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CartDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CartResponse;
import fithou.tuplv.quanghungglassesapi.entity.Cart;
import fithou.tuplv.quanghungglassesapi.entity.CartDetails;
import fithou.tuplv.quanghungglassesapi.entity.ProductSale;
import fithou.tuplv.quanghungglassesapi.entity.Sale;
import fithou.tuplv.quanghungglassesapi.repository.CartRepository;
import fithou.tuplv.quanghungglassesapi.repository.CustomerRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductSaleRepository;
import fithou.tuplv.quanghungglassesapi.repository.SaleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CartMapper {
    final ModelMapper modelMapper;
    final CustomerRepository customerRepository;
    final CartRepository cartRepository;
    final SaleRepository saleRepository;
    final ProductSaleRepository productSaleRepository;

    public CartResponse convertToResponse(Cart cart) {
        CartResponse cartResponse = modelMapper.map(cart, CartResponse.class);
        cartResponse.setTotalProduct(cart.getCartDetails().size());
        cart.getCartDetails().forEach(cartDetails -> cartResponse.getCartDetails().add(convertToResponse(cartDetails)));
        return cartResponse;
    }

    public Cart convertToEntity(CartRequest cartRequest) {
        Cart cart = modelMapper.map(cartRequest, Cart.class);
        cart.setCustomer(customerRepository.findById(cartRequest.getCustomerId()).orElse(null));
        return cart;
    }

    public CartDetailsResponse convertToResponse(CartDetails cartDetails) {
        CartDetailsResponse cartDetailsResponse = modelMapper.map(cartDetails, CartDetailsResponse.class);
        cartDetailsResponse.setProductDetailsId(cartDetails.getProductDetails().getId());
//        cartDetailsResponse.setProductDetailsImage(cartDetails.getProductDetails().getImage());
        cartDetailsResponse.setProductName(cartDetails.getProductDetails().getProduct().getName());
        cartDetailsResponse.setProductColor(cartDetails.getProductDetails().getColor());

        // Lấy giá sau khi đã giảm giá từ bảng sale và product_sale kiểm tra xem thời gian hiện tại có nằm trong thời gian sale không
        double price = cartDetails.getProductDetails().getProduct().getPrice();
        Date now = new Date();
        Optional<Sale> sale = saleRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now);
        if (sale.isPresent()) {
            Optional<ProductSale> productSale = productSaleRepository.findByProductAndSale(cartDetails.getProductDetails().getProduct(), sale.get());
            if (productSale.isPresent())
                price = price * ((double) (100 - productSale.get().getDiscount()) / 100);
        }
        cartDetailsResponse.setProductPrice(price);

        return cartDetailsResponse;
    }

    public CartDetails convertToEntity(CartDetailsRequest cartDetailsRequest) {
        CartDetails cartDetails = modelMapper.map(cartDetailsRequest, CartDetails.class);
        cartDetails.setCart(cartRepository.findById(cartDetailsRequest.getCartId()).orElse(null));
        return cartDetails;
    }
}
