package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login, Long> {
}