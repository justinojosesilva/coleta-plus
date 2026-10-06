package br.com.fiap.coleta_plus.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.assertj.core.api.Assertions.assertThat;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService("segredo-de-teste");

    private final UserDetails admin = User.withUsername("admin@coletaplus.local")
            .password("x").roles("ADMIN").build();

    @Test
    void tokenGeradoEhValidoParaOMesmoUsuario() {
        String token = jwtService.generateToken(admin);

        assertThat(jwtService.extractUsername(token)).isEqualTo("admin@coletaplus.local");
        assertThat(jwtService.isValid(token, admin)).isTrue();
    }

    @Test
    void tokenNaoEhValidoParaOutroUsuario() {
        String token = jwtService.generateToken(admin);
        UserDetails outro = User.withUsername("outro@coletaplus.local").password("x").roles("USER").build();

        assertThat(jwtService.isValid(token, outro)).isFalse();
    }

    @Test
    void tokenAssinadoComOutroSegredoEhRejeitado() {
        String token = new JwtService("outro-segredo").generateToken(admin);

        assertThat(jwtService.isValid(token, admin)).isFalse();
    }
}
