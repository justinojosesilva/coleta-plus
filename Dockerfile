# ---------- Estágio 1: build ----------
# Imagem com JDK + Maven: compila e empacota o .jar. Não vai para produção.
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

# Dependências primeiro: enquanto o pom.xml não mudar, esta camada fica em cache
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw -B -q dependency:go-offline

COPY src src
# Os testes rodam no pipeline (etapa "Build & testes"); aqui só empacotamos
RUN ./mvnw -B -q -DskipTests package \
    && cp target/coleta-plus-*.jar app.jar

# ---------- Estágio 2: runtime ----------
# Apenas JRE (sem Maven/JDK/código-fonte): imagem menor e com menos superfície de ataque
FROM eclipse-temurin:21-jre

ARG APP_VERSION=dev
LABEL org.opencontainers.image.title="coleta-plus" \
      org.opencontainers.image.description="Coleta+ ESG API - Cidades ESG Inteligentes" \
      org.opencontainers.image.version="${APP_VERSION}"

# Usuário sem privilégios de root
RUN groupadd --system app && useradd --system --gid app --home /app app
WORKDIR /app
COPY --from=build --chown=app:app /workspace/app.jar app.jar
USER app

ENV APP_VERSION=${APP_VERSION} \
    JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+ExitOnOutOfMemoryError"

EXPOSE 8080

# O Docker marca o container como "healthy" só quando a API (e o banco) respondem
HEALTHCHECK --interval=15s --timeout=5s --start-period=90s --retries=5 \
    CMD curl -fs http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
