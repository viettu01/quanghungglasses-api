package fithou.tuplv.quanghungglassesapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsResponse implements Serializable {
    private Long id;
    private String color;
    private Integer quantity;
    private String thumbnails;
    private String slug;
    private String name;
    private Double price;
    private Double discount;
    private Double priceDiscount;
    private Date createdDate;
    private Date updatedDate;
}