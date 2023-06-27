package org.borave.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorne as autorizações/grupos do usuário, se aplicável
        return null;
    }

    @Override
    public String getPassword() {
        // Retorne a senha do usuário
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Retorne o nome de usuário do usuário
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        // Verifique se a conta do usuário está expirada
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Verifique se a conta do usuário está bloqueada
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Verifique se as credenciais do usuário estão expiradas
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Verifique se o usuário está habilitado/ativo
        return true;
    }
}
