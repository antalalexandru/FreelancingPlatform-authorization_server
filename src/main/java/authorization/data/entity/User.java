package authorization.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String username;

    @Column
    @ToString.Exclude
    @JsonProperty(access = WRITE_ONLY)
    private String password;

    @Column
    @ToString.Exclude
    @JsonProperty(access = WRITE_ONLY)
    private String email;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(group.getRole()));
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

    /**
     * TODO when warning system is implemented.
     * Should return false is user is banned or temporarily suspended, true otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
