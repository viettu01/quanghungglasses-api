package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Product;
import fithou.tuplv.quanghungglassesapi.entity.ProductSale;
import fithou.tuplv.quanghungglassesapi.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductSaleRepository extends JpaRepository<ProductSale, Long> {
    Optional<ProductSale> findByProductAndSale(Product product, Sale sale);
}