#!/usr/bin/env bash
# Executado NA VM pelo pipeline (via SSH). Sobe/atualiza um ambiente.
#   Uso: ./deploy.sh <staging|production>
# Espera encontrar, no mesmo diretório: docker-compose.deploy.yml e <ambiente>.env
set -euo pipefail

ENV_NAME="${1:?informe o ambiente: staging ou production}"
DIR="$(cd "$(dirname "$0")" && pwd)"
ENV_FILE="$DIR/$ENV_NAME.env"
PROJECT="coleta-$ENV_NAME"
COMPOSE=(docker compose -p "$PROJECT" -f "$DIR/docker-compose.deploy.yml" --env-file "$ENV_FILE")

[ -f "$ENV_FILE" ] || { echo "Arquivo $ENV_FILE não encontrado"; exit 1; }
chmod 600 "$ENV_FILE"

echo ">> [$ENV_NAME] baixando imagem"
"${COMPOSE[@]}" pull app

echo ">> [$ENV_NAME] subindo containers"
"${COMPOSE[@]}" up -d --remove-orphans

echo ">> [$ENV_NAME] aguardando a aplicação ficar saudável"
APP_CONTAINER="$("${COMPOSE[@]}" ps -q app)"
for i in $(seq 1 60); do
  STATUS="$(docker inspect -f '{{.State.Health.Status}}' "$APP_CONTAINER" 2>/dev/null || echo starting)"
  if [ "$STATUS" = "healthy" ]; then
    echo ">> [$ENV_NAME] OK - aplicação saudável"
    "${COMPOSE[@]}" ps
    docker image prune -f >/dev/null
    exit 0
  fi
  echo "   tentativa $i/60: $STATUS"
  sleep 10
done

echo ">> [$ENV_NAME] FALHA - aplicação não ficou saudável. Últimos logs:"
"${COMPOSE[@]}" logs --tail 100 app
exit 1
