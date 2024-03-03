package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.ProductDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductResponse;
import fithou.tuplv.quanghungglassesapi.entity.Product;
import fithou.tuplv.quanghungglassesapi.entity.ProductDetails;
import fithou.tuplv.quanghungglassesapi.entity.SaleDetails;
import fithou.tuplv.quanghungglassesapi.entity.Sale;
import fithou.tuplv.quanghungglassesapi.repository.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@AllArgsConstructor
public class ProductMapper {
    final ModelMapper modelMapper;
    final CategoryRepository categoryRepository;
    final MaterialRepository materialRepository;
    final OriginRepository originRepository;
    final ShapeRepository shapeRepository;
    final BrandRepository brandRepository;
    final ProductRepository productRepository;
    final SaleRepository saleRepository;
    final SaleDetailsRepository saleDetailsRepository;

    // ProductMapper
    public ProductResponse convertToResponse(Product product) {
        ProductResponse productResponse = modelMapper.map(product, ProductResponse.class);
        productResponse.setCategoryId(product.getCategory().getId());
        productResponse.setCategoryName(product.getCategory().getName());
        productResponse.setMaterialId(product.getMaterial().getId());
        productResponse.setMaterialName(product.getMaterial().getName());
        productResponse.setOriginId(product.getOrigin().getId());
        productResponse.setOriginName(product.getOrigin().getName());
        productResponse.setShapeId(product.getShape().getId());
        productResponse.setShapeName(product.getShape().getName());
        productResponse.setBrandId(product.getBrand().getId());
        productResponse.setBrandName(product.getBrand().getName());

        // Lấy giá sau khi đã giảm giá từ bảng sale và product_sale kiểm tra xem thời gian hiện tại có nằm trong thời gian sale không
        double price = product.getPrice();
        Date now = new Date();
        Optional<Sale> sale = saleRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now);
        if (sale.isPresent()) {
            Optional<SaleDetails> productSale = saleDetailsRepository.findByProductAndSale(product, sale.get());
            if (productSale.isPresent())
                price = price * ((100 - productSale.get().getDiscount()) / 100);
        }
        productResponse.setPriceDiscount(price);

//        product.getProductDetails().forEach(productDetails
//                -> productResponse.getProductDetails().add(convertToResponse(productDetails)));
        return productResponse;
    }

    public Product convertToEntity(ProductRequest productRequest) {
        Product productEntity = modelMapper.map(productRequest, Product.class);
        productEntity.setCategory(categoryRepository.findById(productRequest.getCategoryId()).orElse(null));
        productEntity.setMaterial(materialRepository.findById(productRequest.getMaterialId()).orElse(null));
        productEntity.setOrigin(originRepository.findById(productRequest.getOriginId()).orElse(null));
        productEntity.setShape(shapeRepository.findById(productRequest.getShapeId()).orElse(null));
        productEntity.setBrand(brandRepository.findById(productRequest.getBrandId()).orElse(null));
//        productRequest.getProductDetails().forEach(productDetailsRequest
//                -> productEntity.getProductDetails().add(convertToEntity(productDetailsRequest)));
        return productEntity;
    }

    // ProductDetailsMapper
//    public ProductDetailsResponse convertToResponse(ProductDetails productDetails) {
//        return modelMapper.map(productDetails, ProductDetailsResponse.class);
//    }

    public ProductDetails convertToEntity(ProductDetailsRequest productDetailsRequest) {
        ProductDetails productDetails = modelMapper.map(productDetailsRequest, ProductDetails.class);
//        productDetails.setProduct(productRepository.findById(productDetailsRequest.getProductId()).orElse(null));
        return productDetails;
    }
}
