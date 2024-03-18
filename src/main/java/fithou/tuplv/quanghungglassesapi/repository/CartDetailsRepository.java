package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Cart;
import fithou.tuplv.quanghungglassesapi.entity.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {
    Optional<CartDetails> findByCartAndProductDetailsId(Cart cart, Long productId);
}