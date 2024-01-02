package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findByNameContainingAndStatus(String name, Boolean status, Pageable pageable);

    Page<Category> findByNameContaining(String name, Pageable pageable);

    Optional<Category> findBySlug(String slug);

    List<Category> findByStatus(Boolean status);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    long countByStatus(Boolean status);
}