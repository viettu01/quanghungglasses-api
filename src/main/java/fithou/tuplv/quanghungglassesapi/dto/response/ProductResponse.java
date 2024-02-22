package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse implements Serializable {
    private Long id;
    private String name;
    private Double price;
    private Double priceDiscount;
    private Integer quantity;
    private String thumbnail;
    private String description;
    private String slug;
    private boolean status;
    private Date createdDate;
    private Date updatedDate;

    private Long categoryId;
    private String categoryName;

    private Long materialId;
    private String materialName;

    private Long originId;
    private String originName;

    private Long shapeId;
    private String shapeName;

    private Long brandId;
    private String brandName;

    private List<String> images = new ArrayList<>();

    private List<ProductDetailsResponse> productDetails = new ArrayList<>();
}