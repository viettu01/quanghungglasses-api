package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryProductResponse;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryResponse;
import fithou.tuplv.quanghungglassesapi.entity.Category;
import fithou.tuplv.quanghungglassesapi.entity.Product;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {
    final ModelMapper modelMapper;
    final ProductMapper productMapper;

    public CategoryResponse convertToResponse(Category category) {
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);
//        if (category.getProducts() != null)
//            categoryResponse.setTotalProduct(category.getProducts().size());
//        else
//            categoryResponse.setTotalProduct(0);
        return categoryResponse;
    }

    public CategoryProductResponse convertToCategoryProductResponse(Category category) {
        CategoryProductResponse categoryProductResponse = modelMapper.map(category, CategoryProductResponse.class);
        categoryProductResponse.getProducts().clear();
        for (Product product : category.getProducts()) {
            if (product.getStatus())
                categoryProductResponse.getProducts().add(productMapper.convertToResponse(product));
        }
        return categoryProductResponse;
    }

    public Category convertToEntity(CategoryRequest categoryRequest) {
        return modelMapper.map(categoryRequest, Category.class);
    }
}
