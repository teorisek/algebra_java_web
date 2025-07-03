package hr.spring.web.trisek.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "login")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Login {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private LocalDateTime time;

    private String ip;

    public Login(String username, LocalDateTime time, String ip) {
        this.username = username;
        this.time = time;
        this.ip = ip;
    }
}