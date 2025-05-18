"""
backup_creator.py

Este script gera um arquivo .zip com backup das pastas principais do projeto
(assets/, docs/, src/) e salva na pasta backups/.

Como usar:
1. A partir de qualquer lugar, execute:
   python scripts/python/backup_creator/backup_creator.py
2. O arquivo .zip será salvo em: backups/backup_YYYYMMDD_HHMM.zip (na raiz do projeto)

Mantém apenas os 5 backups mais recentes.
"""

import zipfile
import os
from datetime import datetime
from pathlib import Path

# Diretório raiz do projeto (3 níveis acima do script)
ROOT_DIR = Path(__file__).resolve().parents[3]

# Pastas a serem incluídas no backup (relativas à raiz)
FOLDERS_TO_BACKUP = [
    "assets",
    "docs",
    "src"
]

# Pasta onde o .zip será salvo
BACKUP_DIR = ROOT_DIR / "backups"
BACKUP_PREFIX = "backup_"
MAX_BACKUPS = 5

# Nome do arquivo com base na data atual
date_str = datetime.now().strftime("%Y%m%d_%H%M")
backup_filename = f"{BACKUP_PREFIX}{date_str}.zip"
backup_path = BACKUP_DIR / backup_filename


def create_backup():
    BACKUP_DIR.mkdir(parents=True, exist_ok=True)

    with zipfile.ZipFile(backup_path, "w", zipfile.ZIP_DEFLATED) as zipf:
        for folder in FOLDERS_TO_BACKUP:
            abs_folder = ROOT_DIR / folder
            if abs_folder.exists():
                for root, _, files in os.walk(abs_folder):
                    for file in files:
                        full_path = Path(root) / file
                        arcname = full_path.relative_to(ROOT_DIR)
                        zipf.write(full_path, arcname)
            else:
                print(f"Aviso: pasta '{folder}' não encontrada e será ignorada.")

    print(f"Backup criado com sucesso: {backup_path}")
    cleanup_old_backups()


def cleanup_old_backups():
    backups = sorted([
        f for f in BACKUP_DIR.iterdir()
        if f.is_file() and f.name.startswith(BACKUP_PREFIX) and f.name.endswith(".zip")
    ])
    if len(backups) > MAX_BACKUPS:
        to_delete = backups[:-MAX_BACKUPS]
        for file in to_delete:
            try:
                file.unlink()
                print(f"Backup antigo removido: {file.name}")
            except Exception as e:
                print(f"Erro ao remover {file.name}: {e}")


if __name__ == "__main__":
    create_backup()
