package br.com.fiap.coleta_plus.security;

import br.com.fiap.coleta_plus.model.User;
import br.com.fiap.coleta_plus.model.UserRole;
import br.com.fiap.coleta_plus.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void papelRecebePrefixoRoleParaFuncionarComHasRole() {
        User user = new User();
        user.setEmail("admin@coletaplus.local");
        user.setPassword("hash");
        user.setRole(UserRole.ADMIN);
        when(userRepository.findByEmail("admin@coletaplus.local")).thenReturn(Optional.of(user));

        UserDetails details = service.loadUserByUsername("admin@coletaplus.local");

        // @PreAuthorize("hasRole('ADMIN')") exige a authority "ROLE_ADMIN"
        assertThat(details.getAuthorities()).extracting(GrantedAuthority::getAuthority)
                .containsExactly("ROLE_ADMIN");
    }

    @Test
    void usuarioInexistenteLancaExcecao() {
        when(userRepository.findByEmail("x@x.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.loadUserByUsername("x@x.com"))
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
