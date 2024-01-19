package fithou.tuplv.quanghungglassesapi;

import fithou.tuplv.quanghungglassesapi.dto.request.*;
import fithou.tuplv.quanghungglassesapi.service.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

import static fithou.tuplv.quanghungglassesapi.utils.Constants.*;

@SpringBootApplication
@EnableJpaAuditing
public class QuangHungGlassesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuangHungGlassesApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(RoleService roleService, AccountService accountService, CategoryService categoryService,
                          MaterialService materialService, OriginService originService, ShapeService shapeService,
                          BrandService brandService, SupplierService supplierService) {
        return args -> {
            // region Role
            if (!roleService.existsByName(ROLE_ADMIN))
                roleService.save(new RoleRequest(ROLE_ADMIN, ROLE_ADMIN));
            if (!roleService.existsByName(ROLE_MANAGER))
                roleService.save(new RoleRequest(ROLE_MANAGER, ROLE_MANAGER));
            if (!roleService.existsByName(ROLE_SALES))
                roleService.save(new RoleRequest(ROLE_SALES, ROLE_SALES));
            if (!roleService.existsByName(ROLE_WAREHOUSE))
                roleService.save(new RoleRequest(ROLE_WAREHOUSE, ROLE_WAREHOUSE));
            if (!roleService.existsByName(ROLE_USER))
                roleService.save(new RoleRequest(ROLE_USER, ROLE_USER));
            // endregion
            // region Staff ADMIN
            if (!accountService.existsByUsername(ADMIN_EMAIL))
                accountService.create(new AccountRequest(ADMIN_EMAIL, ADMIN_PASSWORD, true, Collections.singletonList(ROLE_ADMIN)));
            // endregion
            // region Category
            if (!categoryService.existsByName("Kính thời trang"))
                categoryService.create(new CategoryRequest(null, "Kính thời trang", "kinh-thoi-trang", "Kính thời trang", true));
            if (!categoryService.existsByName("Gọng kính cận"))
                categoryService.create(new CategoryRequest(null, "Gọng kính cận", "gong-kinh-can", "Gọng kính cận", true));
            if (!categoryService.existsByName("Tròng kính"))
                categoryService.create(new CategoryRequest(null, "Tròng kính", "trong-kinh", "Tròng kính", true));
            if (!categoryService.existsByName("Phụ kiện"))
                categoryService.create(new CategoryRequest(null, "Phụ kiện", "phu-kien", "Phụ kiện", true));
//            for (int i = 0; i < 100; i++) {
//                categoryService.create(new CategoryRequest(null, "Category " + i, "Category " + i, "Category " + i, true));
//            }
            // endregion
            // region Material
            if (!materialService.existsByName("Nhựa cứng"))
                materialService.create(new MaterialRequest(null, "Nhựa cứng"));
            if (!materialService.existsByName("Kim loại"))
                materialService.create(new MaterialRequest(null, "Kim loại"));
            if (!materialService.existsByName("Nhựa pha kim loại"))
                materialService.create(new MaterialRequest(null, "Nhựa pha kim loại"));
            if (!materialService.existsByName("Nhựa dẻo"))
                materialService.create(new MaterialRequest(null, "Nhựa dẻo"));
            if (!materialService.existsByName("Nhựa càng titan"))
                materialService.create(new MaterialRequest(null, "Nhựa càng titan"));
            if (!materialService.existsByName("Nhựa Ultem"))
                materialService.create(new MaterialRequest(null, "Nhựa Ultem"));
            if (!materialService.existsByName("Nhựa Acetate"))
                materialService.create(new MaterialRequest(null, "Nhựa Acetate"));
            if (!materialService.existsByName("Kim loại Titan"))
                materialService.create(new MaterialRequest(null, "Kim loại Titan"));
            if (!materialService.existsByName("Kim loại Thép không gỉ"))
                materialService.create(new MaterialRequest(null, "Kim loại Thép không gỉ"));
            // endregion
            // region Origin
            if (!originService.existsByName("Việt Nam"))
                originService.create(new OriginRequest(null, "Việt Nam"));
            if (!originService.existsByName("Trung Quốc"))
                originService.create(new OriginRequest(null, "Trung Quốc"));
            if (!originService.existsByName("Hàn Quốc"))
                originService.create(new OriginRequest(null, "Hàn Quốc"));
            if (!originService.existsByName("Nhật Bản"))
                originService.create(new OriginRequest(null, "Nhật Bản"));
            if (!originService.existsByName("Mỹ"))
                originService.create(new OriginRequest(null, "Mỹ"));
            if (!originService.existsByName("Pháp"))
                originService.create(new OriginRequest(null, "Pháp"));
            if (!originService.existsByName("Đức"))
                originService.create(new OriginRequest(null, "Đức"));
            if (!originService.existsByName("Ý"))
                originService.create(new OriginRequest(null, "Ý"));
            // endregion
            // region Shape
            if (!shapeService.existsByName("Mắt mèo"))
                shapeService.create(new ShapeRequest(null, "Mắt mèo"));
            if (!shapeService.existsByName("Tròn Oval"))
                shapeService.create(new ShapeRequest(null, "Tròn Oval"));
            if (!shapeService.existsByName("Vuông chữ nhật"))
                shapeService.create(new ShapeRequest(null, "Vuông chữ nhật"));
            if (!shapeService.existsByName("Vuông tròn"))
                shapeService.create(new ShapeRequest(null, "Vuông tròn"));
            if (!shapeService.existsByName("Tròn tròn"))
                shapeService.create(new ShapeRequest(null, "Tròn tròn"));
            if (!shapeService.existsByName("Vuông vuông"))
                shapeService.create(new ShapeRequest(null, "Vuông vuông"));
            if (!shapeService.existsByName("Tròn cạnh"))
                shapeService.create(new ShapeRequest(null, "Tròn cạnh"));
            if (!shapeService.existsByName("Đặc biệt"))
                shapeService.create(new ShapeRequest(null, "Đặc biệt"));
            if (!shapeService.existsByName("Đa giác"))
                shapeService.create(new ShapeRequest(null, "Đa giác"));
            // endregion
            // region Brand
            if (!brandService.existsByName("Rayban"))
                brandService.create(new BrandRequest(null, "Rayban"));
            if (!brandService.existsByName("Gucci"))
                brandService.create(new BrandRequest(null, "Gucci"));
            if (!brandService.existsByName("Dior"))
                brandService.create(new BrandRequest(null, "Dior"));
            if (!brandService.existsByName("Chanel"))
                brandService.create(new BrandRequest(null, "Chanel"));
            if (!brandService.existsByName("Prada"))
                brandService.create(new BrandRequest(null, "Prada"));
            if (!brandService.existsByName("Burberry"))
                brandService.create(new BrandRequest(null, "Burberry"));
            if (!brandService.existsByName("Versace"))
                brandService.create(new BrandRequest(null, "Versace"));
            if (!brandService.existsByName("Dolce & Gabbana"))
                brandService.create(new BrandRequest(null, "Dolce & Gabbana"));
            // endregion
            // region Supplier
            if (!supplierService.existsByName("G – Shop"))
                supplierService.create(new SupplierRequest(null, "G – Shop", "0943061993", "Số nhà 154 ngõ Văn Chương, Đống Đa, Hà Nội"));
            if (!supplierService.existsByName("Anna Eyewear"))
                supplierService.create(new SupplierRequest(null, "Anna Eyewear", "0966886634", "Số 75A ngõ 61 Lê Văn Lương, Hà Nội"));
            if (!supplierService.existsByName("Hiệu Kính Thành Luân"))
                supplierService.create(new SupplierRequest(null, "Hiệu Kính Thành Luân", "038289742", "Số 58 Lương Văn Can, Hà Nội"));
            if (!supplierService.existsByName("Công Ty TNHH Kính Mắt Thiên Vũ"))
                supplierService.create(new SupplierRequest(null, "Công Ty TNHH Kính Mắt Thiên Vũ", "0983675413", "151B Lê Duẩn, Hoàn Kiếm, Hà Nội"));
            if (!supplierService.existsByName("Vanila Shop"))
                supplierService.create(new SupplierRequest(null, "Vanila Shop", "0912101011", "Số 8 ngõ 381/55/4 Nguyễn Khang, Cầu Giấy, Hà Nội"));
            if (!supplierService.existsByName("Công Ty TNHH Kính Mắt Thành Đô"))
                supplierService.create(new SupplierRequest(null, "Công Ty TNHH Kính Mắt Thành Đô", "0975151118", "Số 17D ngõ 141/236 Giáp Nhị, Thịnh Liệt, Hoàng Mai, Hà Nội"));
            // endregion
        };
    }
}
