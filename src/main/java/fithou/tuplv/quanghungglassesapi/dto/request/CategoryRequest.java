package fithou.tuplv.quanghungglassesapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Tên danh mục không được để trống")
    @Length(max = 30, message = "Tên danh mục không được quá 30 ký tự")
    private String name;

    @NotBlank(message = "Slug không được để trống")
    @Length(max = 50, message = "Slug không được quá 50 ký tự")
    private String slug;

    @Length(max = 100, message = "Mô tả không được quá 100 ký tự")
    private String description;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;
}