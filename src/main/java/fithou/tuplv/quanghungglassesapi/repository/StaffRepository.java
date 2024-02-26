package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByAccountEmail(String email);

    Page<Staff> findByFullnameContaining(String name, Pageable pageable);

    boolean existsByPhone(String phone);
}