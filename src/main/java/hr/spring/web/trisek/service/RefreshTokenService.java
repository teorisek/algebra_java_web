package hr.spring.web.trisek.service;

import hr.spring.web.trisek.model.RefreshToken;
import hr.spring.web.trisek.model.User;
import hr.spring.web.trisek.repository.RefreshTokenRepository;
import hr.spring.web.trisek.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){

        //refreshTokenRepository.deleteByUserInfo_Username(username);

        //refreshTokenRepository.findByToken(username).ifPresent(refreshTokenRepository::delete);

        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUserInfo_Username(username);

        if(existingRefreshToken.isPresent()){
            refreshTokenRepository.deleteByToken(existingRefreshToken.get().getToken());
        }

        Optional<RefreshToken> existingRefreshToken2 = refreshTokenRepository.findByUserInfo_Username(username);

        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        RefreshToken refreshToken = RefreshToken.builder()
                .userInfo(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(600000)) // set expiry of refresh token to 10 minutes - you can configure it application.properties file
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefreshToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if(refreshToken.isPresent()){
            refreshTokenRepository.delete(refreshToken.get());
        } else {
            throw new RuntimeException("Refresh Token is not in DB..!!");
        }
    }

    public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if(token.getExpiryDate().compareTo(Instant.now())<0){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }

    public void deleteByUsername(String username) {
        refreshTokenRepository.deleteByUserInfo_Username(username);
    }

}