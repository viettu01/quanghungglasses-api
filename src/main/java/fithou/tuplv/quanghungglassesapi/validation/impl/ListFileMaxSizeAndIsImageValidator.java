package fithou.tuplv.quanghungglassesapi.validation.impl;

import fithou.tuplv.quanghungglassesapi.validation.ListFileMaxSizeAndIsImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class ListFileMaxSizeAndIsImageValidator implements ConstraintValidator<ListFileMaxSizeAndIsImage, List<MultipartFile>> {
    private long maxSize;

    @Override
    public void initialize(ListFileMaxSizeAndIsImage constraintAnnotation) {
        this.maxSize = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();  // Tắt message mặc định
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                if (file.getSize() > maxSize) {
                    context.buildConstraintViolationWithTemplate("Kích thước file không được vượt quá " + maxSize / 1024 / 1024 + " MB").addConstraintViolation();
                    return false;
                } else if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
                    context.buildConstraintViolationWithTemplate("File không phải là ảnh").addConstraintViolation();
                    return false;
                }
            }
        }
        return true;
    }
}
