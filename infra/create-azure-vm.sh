#!/usr/bin/env bash
# Cria a VM no Azure que hospeda os ambientes staging (porta 8081) e produção (porta 8080).
# Pré-requisitos: Azure CLI instalado e logado (az login) com a assinatura Azure for Students.
#   Uso: ./infra/create-azure-vm.sh
set -euo pipefail

RG="${RG:-rg-coleta-plus}"
LOCATION="${LOCATION:-chilecentral}"   # Azure for Students: B-series disponível aqui
VM="${VM:-vm-coleta-plus}"
SIZE="${SIZE:-Standard_B2as_v2}"       # 2 vCPU / 8 GB: comporta 2 Oracle + 2 APIs
KEY="${KEY:-$HOME/.ssh/coleta_plus_vm}"
DIR="$(cd "$(dirname "$0")" && pwd)"

[ -f "$KEY" ] || ssh-keygen -t ed25519 -N "" -C "github-actions-coleta-plus" -f "$KEY"

az group create -n "$RG" -l "$LOCATION" -o none

az vm create -g "$RG" -n "$VM" \
  --image Ubuntu2404 --size "$SIZE" \
  --admin-username azureuser --ssh-key-values "$KEY.pub" \
  --public-ip-sku Standard --custom-data "$DIR/cloud-init.yml" -o none

# Libera apenas as portas das APIs (o SSH 22 já é liberado pelo az vm create)
az vm open-port -g "$RG" -n "$VM" --port 8080 --priority 1010 -o none   # produção
az vm open-port -g "$RG" -n "$VM" --port 8081 --priority 1020 -o none   # staging

IP=$(az vm show -d -g "$RG" -n "$VM" --query publicIps -o tsv)
cat <<MSG

VM criada! IP público: $IP

Configure no GitHub (Settings > Secrets and variables > Actions):
  Variables:  VM_HOST = $IP      VM_USER = azureuser
  Secret:     VM_SSH_KEY = conteúdo de $KEY   (chave PRIVADA)

Para desligar e não gastar crédito:   az vm deallocate -g $RG -n $VM
Para apagar tudo após a correção:     az group delete -n $RG
MSG
