package fithou.tuplv.quanghungglassesapi.dto.request;

import fithou.tuplv.quanghungglassesapi.validation.FileMaxSizeAndIsImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Length(max = 50, message = "Tên sản phẩm không được quá 50 ký tự")
    private String name;

    @NotBlank(message = "Slug không được để trống")
    @Length(max = 100, message = "Slug không được quá 100 ký tự")
    private String slug;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0", inclusive = false, message = "Giá sản phẩm phải lớn 0")
    private Double price;

    @NotNull(message = "Thời gian bảo hành không được để trống")
    @DecimalMin(value = "0", message = "Thời gian bảo hành phải lớn hơn hoặc bằng 0")
    private Integer timeWarranty;

//    @NotBlank(message = "Ảnh sản phẩm không được để trống")
//    private String thumbnail;

    @FileMaxSizeAndIsImage(max = 2 * 1024 * 1024)
    private MultipartFile thumbnailFile;

    @NotBlank(message = "Mô tả sản phẩm không được để trống")
    private String description;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;

    @NotNull(message = "Chất liệu không được để trống")
    private Long materialId;

    @NotNull(message = "Xuất xứ không được để trống")
    private Long originId;

    @NotNull(message = "Hình dạng không được để trống")
    private Long shapeId;

    @NotNull(message = "Thương hiệu không được để trống")
    private Long brandId;

    private List<MultipartFile> imageFiles = new ArrayList<>();

    @NotEmpty(message = "Danh sách chi tiết sản phẩm không được để trống")
    @Valid
    private List<ProductDetailsRequest> productDetails = new ArrayList<>();
}