package fithou.tuplv.quanghungglassesapi.validation;

import fithou.tuplv.quanghungglassesapi.validation.impl.ListFileMaxSizeAndIsImageValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ListFileMaxSizeAndIsImageValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ListFileMaxSizeAndIsImage {
    String message() default "Kích thước file vượt quá giới hạn";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long max() default Long.MAX_VALUE;
}
