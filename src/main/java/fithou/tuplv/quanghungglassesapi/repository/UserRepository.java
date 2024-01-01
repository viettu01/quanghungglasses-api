package fithou.tuplv.quanghungglassesapi.repository;

import fithou.tuplv.quanghungglassesapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}