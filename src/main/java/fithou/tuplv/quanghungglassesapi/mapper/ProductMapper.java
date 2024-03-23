package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.ProductDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductDetailsInvoiceResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductResponse;
import fithou.tuplv.quanghungglassesapi.entity.Product;
import fithou.tuplv.quanghungglassesapi.entity.ProductDetails;
import fithou.tuplv.quanghungglassesapi.entity.Sale;
import fithou.tuplv.quanghungglassesapi.repository.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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
//        double price = product.getPrice();
        Date now = new Date();
        // Hàm trả về danh sách chương trình khuyến mãi nào đang diễn ra trong khoảng thời gian
        List<Sale> salesExists = saleRepository.findByStartDateBetweenOrEndDateBetweenOrStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now, now, now, now, now);
        salesExists.forEach(sale -> sale.getSaleDetails().forEach(saleDetails -> {
            if (saleDetails.getProduct().getId().equals(product.getId())) {
                productResponse.setDiscount(saleDetails.getDiscount());
                Double priceDiscount = product.getPrice() * ((100 - saleDetails.getDiscount()) / 100);
                productResponse.setPriceDiscount(priceDiscount);
//                    price = price * ((100 - saleDetails.getDiscount()) / 100);
            }
        }));
//        productResponse.setPriceDiscount(price);
        return productResponse;
    }

    public Product convertToEntity(ProductRequest productRequest) {
        Product productEntity = modelMapper.map(productRequest, Product.class);
        productEntity.setCategory(categoryRepository.findById(productRequest.getCategoryId()).orElse(null));
        productEntity.setMaterial(materialRepository.findById(productRequest.getMaterialId()).orElse(null));
        productEntity.setOrigin(originRepository.findById(productRequest.getOriginId()).orElse(null));
        productEntity.setShape(shapeRepository.findById(productRequest.getShapeId()).orElse(null));
        productEntity.setBrand(brandRepository.findById(productRequest.getBrandId()).orElse(null));
        return productEntity;
    }

    public ProductDetails convertToEntity(ProductDetailsRequest productDetailsRequest) {
        return modelMapper.map(productDetailsRequest, ProductDetails.class);
    }

    public ProductDetailsInvoiceResponse convertToProductDetailsInvoiceResponse(ProductDetails productDetails) {
        ProductDetailsInvoiceResponse productDetailsInvoiceResponse = modelMapper.map(productDetails, ProductDetailsInvoiceResponse.class);
        productDetailsInvoiceResponse.setName(productDetails.getProduct().getName());
        return productDetailsInvoiceResponse;
    }

    public ProductDetailsResponse convertToProductDetailsResponse(ProductDetails productDetails) {
        ProductDetailsResponse productDetailsResponse = modelMapper.map(productDetails, ProductDetailsResponse.class);
        productDetailsResponse.setName(productDetails.getProduct().getName());
        productDetailsResponse.setSlug(productDetails.getProduct().getSlug());
        productDetailsResponse.setPrice(productDetails.getProduct().getPrice());
        productDetailsResponse.setThumbnails(productDetails.getProduct().getThumbnail());
        Date now = new Date();
        // Hàm trả về danh sách chương trình khuyến mãi nào đang diễn ra trong khoảng thời gian
        List<Sale> salesExists = saleRepository.findByStartDateBetweenOrEndDateBetweenOrStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now, now, now, now, now);
        salesExists.forEach(sale -> sale.getSaleDetails().forEach(saleDetails -> {
            if (saleDetails.getProduct().getId().equals(productDetails.getProduct().getId())) {
                productDetailsResponse.setDiscount(saleDetails.getDiscount());
                Double priceDiscount = productDetails.getProduct().getPrice() * ((100 - saleDetails.getDiscount()) / 100);
                productDetailsResponse.setPriceDiscount(priceDiscount);
//                    price = price * ((100 - saleDetails.getDiscount()) / 100);
            }
        }));
//        productDetailsResponse.setDiscount(productDetails.getProduct().getDiscount());
//        productDetailsResponse.setPriceDiscount(productDetails.getProduct().getPriceDiscount());
        return productDetailsResponse;
    }
}
