package br.com.fiap.coleta_plus;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Teste de integração: sobe o contexto completo contra um Oracle real (no CI, um service container).
 * Com ddl-auto=validate, o Hibernate confere se todas as entidades batem com as tabelas
 * criadas pelas migrations do Flyway — falha se alguma tabela/coluna não existir.
 */
@SpringBootTest(properties = "spring.jpa.hibernate.ddl-auto=validate")
class ColetaPlusApplicationIT {

	@Test
	void contextLoadsAndSchemaMatchesEntities() {
	}

}
