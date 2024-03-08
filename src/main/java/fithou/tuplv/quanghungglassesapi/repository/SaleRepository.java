package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Page<Sale> findAll(Pageable pageable);

    Page<Sale> findByNameContaining(String name, Pageable pageable);

    List<Sale> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(Date startDate, Date endDate);

    List<Sale> findByStartDateBetweenOrEndDateBetween(Date startDate, Date endDate, Date startDate2, Date endDate2);

    boolean existsByName(String name);
}