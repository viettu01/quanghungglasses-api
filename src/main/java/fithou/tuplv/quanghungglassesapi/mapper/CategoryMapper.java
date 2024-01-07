package fithou.tuplv.quanghungglassesapi.mapper;

import fithou.tuplv.quanghungglassesapi.dto.request.CategoryRequest;
import fithou.tuplv.quanghungglassesapi.dto.response.CategoryResponse;
import fithou.tuplv.quanghungglassesapi.entity.Category;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryMapper {
    final ModelMapper modelMapper;

    public CategoryResponse convertToResponse(Category categoryEntity) {
        CategoryResponse categoryResponse = modelMapper.map(categoryEntity, CategoryResponse.class);
        categoryResponse.setTotalProduct(categoryEntity.getProducts().size());
        return categoryResponse;
    }

    public Category convertToEntity(CategoryRequest categoryRequest) {
        return modelMapper.map(categoryRequest, Category.class);
    }
}
