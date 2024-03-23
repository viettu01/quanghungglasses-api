package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductDetailsInvoiceResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductDetailsResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.ProductResponse;
import fithou.tuplv.quanghungglassesapi.entity.Product;
import fithou.tuplv.quanghungglassesapi.entity.ProductDetails;
import fithou.tuplv.quanghungglassesapi.mapper.PaginationMapper;
import fithou.tuplv.quanghungglassesapi.mapper.ProductMapper;
import fithou.tuplv.quanghungglassesapi.repository.ProductDetailsRepository;
import fithou.tuplv.quanghungglassesapi.repository.ProductRepository;
import fithou.tuplv.quanghungglassesapi.service.ProductService;
import fithou.tuplv.quanghungglassesapi.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@Service
@Transactional
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    final ProductMapper productMapper;
    final PaginationMapper paginationMapper;
    final ProductRepository productRepository;
    final ProductDetailsRepository productDetailsRepository;
    final StorageService storageService;

    @Override
    public PaginationDTO<ProductResponse> findByNameContaining(String name, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(
                productRepository.findByNameContaining(name, pageable).map(productMapper::convertToResponse)
        );
    }

    @Override
    public PaginationDTO<ProductResponse> findByNameContainingAndStatus(String name, Boolean status, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(
                productRepository.findByNameContainingAndStatus(name, status, pageable).map(productMapper::convertToResponse)
        );
    }

    @Override
    public PaginationDTO<ProductResponse> findByCategorySlug(String categorySlug, List<String> originNames, List<String> brandNames,
                                                             List<String> materialNames, List<String> shapeNames, List<Integer> timeWarranties,
                                                             Double priceMin, Double priceMax, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(
                productRepository.findAll(
                                ProductSpecifications.filterProducts(
                                        categorySlug,
                                        originNames,
                                        brandNames,
                                        materialNames,
                                        shapeNames,
                                        timeWarranties,
                                        priceMin,
                                        priceMax
                                ), pageable)
                        .map(productMapper::convertToResponse)
        );
    }

    @Override
    public ProductDetailsInvoiceResponse findProductDetailsById(Long id) {
        return productMapper.convertToProductDetailsInvoiceResponse(
                productDetailsRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_DETAILS_NOT_FOUND))
        );
    }

    @Override
    public ProductDetailsResponse findProductDetailsByIdInCart(Long id) {
        return productMapper.convertToProductDetailsResponse(
                productDetailsRepository
                        .findById(id)
                        .orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_DETAILS_NOT_FOUND))
        );
    }

    @Override
    public ProductResponse findBySlug(String slug) {
        return productMapper.convertToResponse(productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND)));
    }

    @Override
    public ProductResponse findById(Long id) {
        return productMapper.convertToResponse(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND)));
    }

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        if (productRepository.existsByName(productRequest.getName()))
            throw new RuntimeException(ERROR_PRODUCT_NAME_ALREADY_EXISTS);
        if (productRepository.existsBySlug(productRequest.getSlug()))
            throw new RuntimeException(ERROR_SLUG_ALREADY_EXISTS);
        if (productRequest.getThumbnailFile().isEmpty())
            throw new RuntimeException(ERROR_PRODUCT_THUMBNAIL_NOT_EMPTY);
        if (productRequest.getProductDetails().stream().map(ProductDetailsRequest::getColor).collect(Collectors.toSet()).size()
                != productRequest.getProductDetails().size())
            throw new RuntimeException(ERROR_PRODUCT_DETAILS_COLOR_ALREADY_EXISTS);
        if (productRequest.getImageFiles().isEmpty())
            throw new RuntimeException(ERROR_PRODUCT_IMAGE_NOT_EMPTY);

        Product product = productMapper.convertToEntity(productRequest);
        product.setThumbnail(storageService.saveImageFile(DIR_FILE_PRODUCT, productRequest.getThumbnailFile()));
        for (MultipartFile imageFile : productRequest.getImageFiles())
            product.getImages().add(storageService.saveImageFile(DIR_FILE_PRODUCT, imageFile));
        try {
            productRepository.save(product);
        } catch (Exception e) {
            storageService.deleteFile(product.getThumbnail());
            product.getImages().forEach(storageService::deleteFile);
            throw new RuntimeException(e.getMessage());
        }

        product.getProductDetails().clear();
        try {
            for (ProductDetailsRequest productDetailsRequest : productRequest.getProductDetails()) {
                ProductDetails productDetails = productMapper.convertToEntity(productDetailsRequest);
                productDetails.setProduct(product);
                productDetailsRepository.save(productDetails);
                product.getProductDetails().add(productDetails);
            }
        } catch (Exception e) {
            storageService.deleteFile(product.getThumbnail());
            product.getImages().forEach(storageService::deleteFile);
            throw new RuntimeException(e.getMessage());
        }
        return productMapper.convertToResponse(product);
    }

    @Override
    public ProductResponse update(ProductRequest productRequest) {
        Product productExists = productRepository.findById(productRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND));

        if (!productExists.getName().equalsIgnoreCase(productRequest.getName()) && productRepository.existsByName(productRequest.getName()))
            throw new RuntimeException(ERROR_PRODUCT_NAME_ALREADY_EXISTS);
        if (!productExists.getSlug().equalsIgnoreCase(productRequest.getSlug()) && productRepository.existsBySlug(productRequest.getSlug()))
            throw new RuntimeException(ERROR_SLUG_ALREADY_EXISTS);
        for (ProductDetailsRequest productDetailsRequest : productRequest.getProductDetails()) {
            ProductDetails productDetails = productDetailsRepository
                    .findByIdAndProductId(productDetailsRequest.getId(), productRequest.getId())
                    .orElse(null);
            if (productDetails != null && !productDetails.getColor().equalsIgnoreCase(productDetailsRequest.getColor())
                    && productExists.getProductDetails().stream().map(ProductDetails::getColor).collect(Collectors.toSet()).contains(productDetailsRequest.getColor()))
                throw new RuntimeException(ERROR_PRODUCT_DETAILS_COLOR_ALREADY_EXISTS);
            else if (productDetails == null && productExists.getProductDetails().stream().map(ProductDetails::getColor).collect(Collectors.toSet()).contains(productDetailsRequest.getColor()))
                throw new RuntimeException(ERROR_PRODUCT_DETAILS_COLOR_ALREADY_EXISTS);
        }

        Product product = productMapper.convertToEntity(productRequest);
        if (productRequest.getThumbnailFile().isEmpty())
            product.setThumbnail(productExists.getThumbnail());
        else {
            storageService.deleteFile(productExists.getThumbnail());
            product.setThumbnail(storageService.saveImageFile(DIR_FILE_PRODUCT, productRequest.getThumbnailFile()));
        }
        product.getImages().addAll(productExists.getImages());
        for (MultipartFile file : productRequest.getImageFiles())
            if (!file.isEmpty())
                product.getImages().add(storageService.saveImageFile(DIR_FILE_PRODUCT, file));
        productRepository.save(product);
        try {
            for (ProductDetailsRequest productDetailsRequest : productRequest.getProductDetails()) {
                ProductDetails productDetails =
                        productDetailsRepository
                                .findByIdAndProductId(productDetailsRequest.getId(), product.getId())
                                .orElse(null);
                if (productDetails == null) {
                    ProductDetails newProductDetails = productMapper.convertToEntity(productDetailsRequest);
                    newProductDetails.setProduct(product);
                    productDetailsRepository.save(newProductDetails);
                } else {
                    productDetails.setColor(productDetailsRequest.getColor());
                    productDetails.setQuantity(productDetailsRequest.getQuantity());
                    productDetailsRepository.save(productDetails);
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
//            storageService.deleteFile(product.getThumbnail());
            throw new RuntimeException(e.getMessage());
        }
        return productMapper.convertToResponse(productExists);
    }

    @Override
    public ProductResponse updateStatus(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND));
        product.setStatus(!product.getStatus());
        productRepository.save(product);
        return productMapper.convertToResponse(product);
    }

    @Override
    public Long countByStatus(Boolean status) {
        return productRepository.countByStatus(status);
    }

    @Override
    public Long countAll() {
        return productRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND));
        for (ProductDetails productDetails : product.getProductDetails()) {
            if (!productDetails.getReceiptDetails().isEmpty())
                throw new RuntimeException(ERROR_PRODUCT_HAS_RECEIPT);
            if (!productDetails.getOrderDetails().isEmpty())
                throw new RuntimeException(ERROR_PRODUCT_HAS_ORDER);
            if (!productDetails.getWarrantyDetails().isEmpty())
                throw new RuntimeException(ERROR_PRODUCT_HAS_WARRANTY);
        }
        storageService.deleteFile(product.getThumbnail());
        product.getImages().forEach(storageService::deleteFile);
        productDetailsRepository.deleteAll(product.getProductDetails());
        productRepository.deleteById(id);
    }

    @Override
    public void deleteProductDetailsById(Long id) {
        ProductDetails productDetails = productDetailsRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_DETAILS_NOT_FOUND));

        if (!productDetails.getReceiptDetails().isEmpty())
            throw new RuntimeException(ERROR_PRODUCT_HAS_RECEIPT);
        if (!productDetails.getOrderDetails().isEmpty())
            throw new RuntimeException(ERROR_PRODUCT_HAS_ORDER);
        if (!productDetails.getWarrantyDetails().isEmpty())
            throw new RuntimeException(ERROR_PRODUCT_HAS_WARRANTY);
        productDetailsRepository.deleteById(id);
    }

    @Override
    public void deleteImageById(Long id, String imageName) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND));
        for (int i = 0; i < product.getImages().size(); i++) {
            if (product.getImages().get(i).equals(imageName)) {
                product.getImages().remove(i);
                storageService.deleteFile(imageName);
                productRepository.save(product);
            }
        }
    }
}
