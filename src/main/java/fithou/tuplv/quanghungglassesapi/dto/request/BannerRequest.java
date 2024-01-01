package fithou.tuplv.quanghungglassesapi.dto.request;

import fithou.tuplv.quanghungglassesapi.validation.FileMaxSizeAndIsImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BannerRequest implements Serializable {
    private Long id;

    @NotBlank(message = "Tên banner không được để trống")
    @Length(max = 100, message = "Tên banner phải nhỏ hơn 100 ký tự")
    private String name;

    @FileMaxSizeAndIsImage(max = 2 * 1024 * 1024)
    private MultipartFile multipartFileImage;

    @NotNull(message = "Trạng thái không được để trống")
    private Boolean status;

    @NotNull(message = "Người tạo không được để trống")
    private Long userId;
}