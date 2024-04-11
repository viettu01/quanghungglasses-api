package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findDistinctByCustomerAccountEmailAndOrderDetails_ProductDetails_ProductNameContaining(String email, String productName, Pageable pageable);

    Page<Order> findByFullnameContaining(String fullname, Pageable pageable);

    Page<Order> findByCustomerAccountEmail(String email, Pageable pageable);
}