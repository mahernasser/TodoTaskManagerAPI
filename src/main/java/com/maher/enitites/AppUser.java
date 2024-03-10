package com.maher.enitites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Document(collection = "users")
public class AppUser implements UserDetails {

    @Id
    private String id = UUID.randomUUID().toString();

    @NotEmpty
    private String name;
    @NotEmpty
    @JsonIgnore
    private String password;
    @NotEmpty
    private String email;
    private Date createdAt;


    public AppUser(@NotEmpty String name,@NotEmpty String password, @NotEmpty String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.createdAt = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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


}
