package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySlug(String slug);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findByNameContainingAndStatus(String name, Boolean status, Pageable pageable);

    Page<Product> findByCategoryIdAndMaterialIdAndOriginIdAndShapeIdAndBrandIdAndPriceBetween(Long categoryId, Long materialId, Long originId, Long shapeId, Long brandId, Double priceMin, Double priceMax, Pageable pageable);

    Page<Product> findByCategorySlug(String slug, Pageable pageable);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    Long countByStatus(Boolean status);
}