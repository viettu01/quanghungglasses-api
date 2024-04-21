package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Receipt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
    Page<Receipt> findBySupplierNameContaining(String supplierName, Pageable pageable);

    List<Receipt> findAllByUpdatedDateBetweenAndStatus(Date from, Date to, Boolean status);
}