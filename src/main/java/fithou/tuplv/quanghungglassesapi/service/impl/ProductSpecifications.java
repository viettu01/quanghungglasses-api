package fithou.tuplv.quanghungglassesapi.service.impl;

import fithou.tuplv.quanghungglassesapi.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductSpecifications {
    public static Specification<Product> filterProducts(String categorySlug,
                                                        List<String> origins,
                                                        List<String> brands,
                                                        List<String> materials,
                                                        List<String> shapes,
                                                        List<Integer> timeWarranties,
                                                        Double priceMin,
                                                        Double priceMax) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("category").get("slug"), categorySlug));
            predicates.add(criteriaBuilder.equal(root.get("status"), true));

            if (brands != null && !brands.isEmpty()) {
                predicates.add(root.get("brand").get("name").in(brands));
            }
            if (materials != null && !materials.isEmpty()) {
                predicates.add(root.get("material").get("name").in(materials));
            }
            if (shapes != null && !shapes.isEmpty()) {
                predicates.add(root.get("shape").get("name").in(shapes));
            }
            if (origins != null && !origins.isEmpty()) {
                predicates.add(root.get("origin").get("name").in(origins));
            }
            if (timeWarranties != null && !timeWarranties.isEmpty()) {
                predicates.add(root.get("timeWarranty").in(timeWarranties));
            }
            if (Objects.nonNull(priceMin) && Objects.isNull(priceMax)) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), priceMin));
            }
            if (Objects.isNull(priceMin) && Objects.nonNull(priceMax)) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), priceMax));
            }
            if (Objects.nonNull(priceMin) && Objects.nonNull(priceMax)) {
                predicates.add(criteriaBuilder.between(root.get("price"), priceMin, priceMax));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
