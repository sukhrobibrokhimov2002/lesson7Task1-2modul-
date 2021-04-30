package uz.sukhrob.lesson7task1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.sukhrob.lesson7task1.entity.enums.Permissions;
import uz.sukhrob.lesson7task1.entity.template.AbsEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User extends AbsEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    private boolean enabled = false;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Permissions> permissions = this.role.getPermissions();
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        for (Permissions permission : permissions) {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(permission.name()));
        }
        return grantedAuthoritySet;
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
        return enabled;
    }
}
