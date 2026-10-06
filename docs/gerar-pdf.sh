#!/usr/bin/env bash
# Gera docs/Documentacao-Coleta-Plus.pdf a partir de docs/documentacao.html (usa o Chrome headless).
# Coloque os prints em docs/prints/ com os nomes indicados no HTML e rode novamente.
set -euo pipefail
DIR="$(cd "$(dirname "$0")" && pwd)"
CHROME="${CHROME:-/Applications/Google Chrome.app/Contents/MacOS/Google Chrome}"
"$CHROME" --headless=new --disable-gpu --no-pdf-header-footer --allow-file-access-from-files \
  --virtual-time-budget=5000 --print-to-pdf="$DIR/Documentacao-Coleta-Plus.pdf" "file://$DIR/documentacao.html" 2>/dev/null
echo "PDF gerado: $DIR/Documentacao-Coleta-Plus.pdf"
