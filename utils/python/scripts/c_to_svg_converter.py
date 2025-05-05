"""
Este script deve ser executado a partir da raiz do projeto, usando importações absolutas.

Exemplo de execução:
    python utils/python/scripts/c_to_svg_converter.py --config utils/python/configs/c_to_svg/name/.../name.json

Isso garante que as importações do pacote 'utils.python.lib' funcionem corretamente.

---
Formato esperado do arquivo JSON de configuração:

{
  "input_dir": "caminho/para/entrada",
  "output_dir": "caminho/para/saida",
  "file_names": ["arquivo1.c", "arquivo2.c"],
  "frame_names": [
    ["arquivo1_frame1.svg", "arquivo1_frame2.svg"],
    ["arquivo2_frame1.svg", "arquivo2_frame2.svg"]
  ], ou "shared_frame_names": ["arquivo_frame.svg", "arquivo_frame.svg"],
  "width": x,
  "height": x,
  "pixel_size": 10
}
"""

import sys
import os

# Adiciona o diretório raiz do projeto ao sys.path
sys.path.append(
    os.path.abspath(  # Caminho absoluto até:
        os.path.join(  # Vai subindo pastas:
            os.path.dirname(__file__),  # pasta atual = scripts/
            "../../.."  # sobe 3 níveis até chegar na raiz do projeto
        )
    )
)

import json
import argparse
from utils.python.lib import processar_arquivo


def main():
    parser = argparse.ArgumentParser(description="Converter arquivos .c em SVGs com base em uma configuração.")
    parser.add_argument("--config", required=True, help="Caminho para o arquivo de configuração JSON.")
    args = parser.parse_args()

    with open(args.config, "r", encoding="utf-8") as f:
        config = json.load(f)

    input_dir = config["input_dir"]
    output_dir = config["output_dir"]
    file_names = config["file_names"]
    frame_names = config.get("frame_names")
    shared_frame_names = config.get("shared_frame_names")
    largura = config["width"]
    altura = config["height"]
    pixel_size = config["pixel_size"]

    for i, file_name in enumerate(file_names):
        base_name = os.path.splitext(file_name)[0].lower()
        saida_individual = os.path.join(output_dir, base_name)
        os.makedirs(saida_individual, exist_ok=True)

        caminho_arquivo = os.path.join(input_dir, file_name)
        try:
            processar_arquivo(
                caminho_entrada=caminho_arquivo,
                pasta_saida=saida_individual,
                base_nome_saida=base_name,
                largura=largura,
                altura=altura,
                pixel_size=pixel_size,
                frame_names=frame_names[i] if frame_names else shared_frame_names
            )
        except Exception as e:
            print(f"Erro ao processar '{file_name}': {e}")

if __name__ == "__main__":
    main()
