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
public class CategoryResponse implements Serializable {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private Integer totalProduct;
    private Boolean status;
    private Date createdDate;
    private Date updatedDate;
}