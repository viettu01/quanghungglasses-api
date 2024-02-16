package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.dto.PaginationDTO;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductDetailsRequest;
import fithou.tuplv.quanghungglassesapi.dto.request.ProductRequest;
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
    public PaginationDTO<ProductResponse> findAll(Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(
                productRepository.findAll(pageable).map(productMapper::convertToResponse)
        );
    }

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
    public ProductResponse findBySlug(String slug) {
        return productMapper.convertToResponse(productRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND)));
    }

    @Override
    public PaginationDTO<ProductResponse> filter(Long categoryId, Long materialId, Long originId, Long shapeId, Long brandId, Double priceMin, Double priceMax, Pageable pageable) {
        return paginationMapper.mapToPaginationDTO(
                productRepository
                        .findByCategoryIdAndMaterialIdAndOriginIdAndShapeIdAndBrandIdAndPriceBetween(categoryId, materialId, originId, shapeId, brandId, priceMin, priceMax, pageable)
                        .map(productMapper::convertToResponse)
        );
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
        for (ProductDetailsRequest productDetailsRequest : productRequest.getProductDetails())
            for (MultipartFile file : productDetailsRequest.getImageFiles())
                if (file.isEmpty())
                    throw new RuntimeException(ERROR_PRODUCT_DETAILS_IMAGE_NOT_EMPTY);

        Product product = productMapper.convertToEntity(productRequest);
        product.setThumbnail(storageService.saveImageFile(DIR_FILE_PRODUCT, productRequest.getThumbnailFile()));
        productRepository.save(product);
        product.getProductDetails().clear();
        try {
            for (ProductDetailsRequest productDetailsRequest : productRequest.getProductDetails()) {
                ProductDetails productDetails = productMapper.convertToEntity(productDetailsRequest);
                productDetails.setProduct(product);
                for (MultipartFile file : productDetailsRequest.getImageFiles())
                    productDetails.getImages().add(storageService.saveImageFile(DIR_FILE_PRODUCT, file));
                productDetailsRepository.save(productDetails);
                product.getProductDetails().add(productDetails);
            }
        } catch (Exception e) {
            storageService.deleteFile(product.getThumbnail());
            throw new RuntimeException(e.getMessage());
        }
        return productMapper.convertToResponse(product);
    }

    @Override
    public ProductResponse update(ProductRequest productRequest) {
        String oldFileName = "";
        Product productExists = productRepository.findById(productRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND));

        if (!productExists.getName().equalsIgnoreCase(productRequest.getName()) && productRepository.existsByName(productRequest.getName()))
            throw new RuntimeException(ERROR_PRODUCT_NAME_ALREADY_EXISTS);
        if (!productExists.getSlug().equalsIgnoreCase(productRequest.getSlug()) && productRepository.existsBySlug(productRequest.getSlug()))
            throw new RuntimeException(ERROR_SLUG_ALREADY_EXISTS);
        for (ProductDetailsRequest productDetailsRequest : productRequest.getProductDetails()) {
            ProductDetails productDetails = productDetailsRepository.findById(productDetailsRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND));
            if (!productDetails.getColor().equalsIgnoreCase(productDetailsRequest.getColor())
                    && productExists.getProductDetails().stream().map(ProductDetails::getColor).collect(Collectors.toSet()).contains(productDetailsRequest.getColor()))
                throw new RuntimeException(ERROR_PRODUCT_DETAILS_COLOR_ALREADY_EXISTS);
        }

        Product product = productMapper.convertToEntity(productRequest);
        if (productRequest.getThumbnailFile().isEmpty())
            product.setThumbnail(productExists.getThumbnail());
        else {
            oldFileName = productExists.getThumbnail();
            product.setThumbnail(storageService.saveImageFile(DIR_FILE_PRODUCT, productRequest.getThumbnailFile()));
        }
        productRepository.save(product);
        try {
            // Cập nhật dữ liệu cho productDetails và các ảnh cũ sẽ được giữ lại và thêm ảnh mới
            for (ProductDetailsRequest productDetailsRequest : productRequest.getProductDetails()) {
                ProductDetails productDetails = productDetailsRepository.findById(productDetailsRequest.getId()).orElseThrow(() -> new RuntimeException(ERROR_PRODUCT_NOT_FOUND));
                productDetails.setColor(productDetailsRequest.getColor());
                productDetails.setQuantity(productDetailsRequest.getQuantity());
                for (MultipartFile file : productDetailsRequest.getImageFiles())
                    if (!file.isEmpty())
                        productDetails.getImages().add(storageService.saveImageFile(DIR_FILE_PRODUCT, file));

                productDetailsRepository.save(productDetails);
            }
            storageService.deleteFile(oldFileName);
        } catch (Exception e) {
            storageService.deleteFile(product.getThumbnail());
            throw new RuntimeException(e.getMessage());
        }
        return productMapper.convertToResponse(productExists);
    }

    @Override
    public Long countByStatus(Boolean status) {
        return productRepository.countByStatus(status);
    }

    @Override
    public Long countAll() {
        return productRepository.count();
    }
}
