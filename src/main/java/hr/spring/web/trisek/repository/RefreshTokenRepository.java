package hr.spring.web.trisek.repository;

import hr.spring.web.trisek.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserInfo_Username(String username);
    void deleteByToken(String token);
    void deleteByUserInfo_Username(String userInfoUsername);
}
