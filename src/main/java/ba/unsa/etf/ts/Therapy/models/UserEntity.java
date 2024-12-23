package ba.unsa.etf.ts.Therapy.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user")
@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "VARCHAR(64)")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private RoleEntity role;

    @Column(name = "user_type", columnDefinition = "VARCHAR(64)")
    private String type;

    @Column(name = "user_name", columnDefinition = "VARCHAR(1024)")
    private String name;

    @Column(name = "email", columnDefinition = "VARCHAR(256)")
    private String email;

    @Column(name = "password_hash", columnDefinition = "VARCHAR(256)")
    private String password;

    @Column(name = "image_url", columnDefinition = "VARCHAR(1024)")
    private String imageUrl;

    // Override UserDetails methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()));
    }

    public UserEntity(String type, String email, String name, String password, RoleEntity role, String imageUrl) {
        this.type = type;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
        this.imageUrl = imageUrl;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + userId +
                ", type=" + type + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + password + '\'' +
                '}';
    }

}
