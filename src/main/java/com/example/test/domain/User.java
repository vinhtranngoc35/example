package com.example.test.domain;

import com.example.test.domain.enumration.ERole;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = 'DELETED' WHERE id = ?")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    private ERole role;

    private boolean isEnabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}