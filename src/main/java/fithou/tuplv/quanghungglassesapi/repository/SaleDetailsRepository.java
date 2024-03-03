package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Product;
import fithou.tuplv.quanghungglassesapi.entity.Sale;
import fithou.tuplv.quanghungglassesapi.entity.SaleDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleDetailsRepository extends JpaRepository<SaleDetails, Long> {
    Optional<SaleDetails> findByProductAndSale(Product product, Sale sale);

    Optional<SaleDetails> findByProductIdAndSaleId(Long productId, Long saleId);
}