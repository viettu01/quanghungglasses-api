package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findDistinctByCustomerAccountEmailAndOrderDetails_ProductDetails_ProductNameContaining(String email, String productName, Pageable pageable);

    Page<Order> findDistinctByCustomerAccountEmailAndOrderStatusAndOrderDetails_ProductDetails_ProductNameContaining(String email, Integer orderStatus, String productName, Pageable pageable);

    Page<Order> findDistinctByCustomerAccountEmailAndOrderStatusGreaterThanEqualAndOrderStatusIsLessThanEqualAndOrderDetails_ProductDetails_ProductNameContaining(String email, Integer orderStatus, Integer orderStatusLessThan, String productName, Pageable pageable);

    Page<Order> findByFullnameContaining(String fullname, Pageable pageable);

    List<Order> findAllByCompletedDateBetweenAndOrderStatus(Date from, Date to, Integer status);
}