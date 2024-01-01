package fithou.tuplv.quanghungglassesapi.validation.impl;

import fithou.tuplv.quanghungglassesapi.validation.FileMaxSizeAndIsImage;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FileMaxSizeAndIsImageValidator implements ConstraintValidator<FileMaxSizeAndIsImage, MultipartFile> {
    private long maxSize;

    @Override
    public void initialize(FileMaxSizeAndIsImage constraintAnnotation) {
        this.maxSize = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();  // Tắt message mặc định
        if (!file.isEmpty()) {
            // Kiểm tra kích thước file và định dạng file
            if (file.getSize() > maxSize) {
                context.buildConstraintViolationWithTemplate("Kích thước file không được vượt quá " + maxSize / 1024 / 1024 + " MB").addConstraintViolation();
                return false;
            } else if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
                context.buildConstraintViolationWithTemplate("File không phải là ảnh").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
