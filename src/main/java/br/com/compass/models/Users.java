package br.com.compass.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "birth_date", nullable = false)
    private Date birthDate;
    
    @Column(length = 11)
    private String cpf;
    
    @Column(length = 15)
    private String phone;
    
    @Column(nullable = false)
    private String email;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Column(nullable = false)
    private String role;
    
    @Column(length = 20, insertable = false)
    private String status;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Date created_at;

    @Transient
    private Integer passwordAttemptCounter = 0;

    public Users() {}

    public Users(String name, java.util.Date birthDate, String cpf, String phone, String password, String email, String role) {
        this.name = name;
        this.birthDate = new Date(birthDate.getTime());
        this.cpf = cpf.replaceAll("[^0-9]", "");
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
        this.role = role;
    }
}
