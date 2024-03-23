package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findBySlug(String slug);

    Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findByNameContainingAndStatus(String name, Boolean status, Pageable pageable);

    Page<Product> findAll(Specification<Product> specification, Pageable pageable);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    Long countByStatus(Boolean status);
}