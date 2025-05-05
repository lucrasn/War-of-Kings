"""
Este script deve ser executado a partir da raiz do projeto com:

    python utils/python/scripts/svg_to_png_converter.py --config utils/python/configs/svg_to_png_config.json

O arquivo de configuração JSON deve conter:
- "svg_base": caminho dos SVGs
- "png_base": caminho de saída dos PNGs
- "name_path": lista de subpastas a processar
- E um dos dois:
    - "tipos": para estruturas baseadas em nome + tipo (ex: PeaoBase)
    - "shared_svg_names": para estruturas com nomes fixos em todas as pastas
"""

import os
import sys
import json
import argparse


sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), "../../..")))

from utils.python.lib.svg_to_png_utils import converter_svg_para_png_em_lote

def main():
    parser = argparse.ArgumentParser(description="Converte SVGs para PNGs com base em um arquivo de configuração JSON.")
    parser.add_argument("--config", required=True, help="Caminho para o arquivo JSON de configuração.")
    args = parser.parse_args()

    with open(args.config, "r", encoding="utf-8") as f:
        config = json.load(f)

    svg_base = config["svg_base"]
    png_base = config["png_base"]
    name_path = config["name_path"]
    tipos = config.get("tipos")
    shared_svg_names = config.get("shared_svg_names")

    converter_svg_para_png_em_lote(
        svg_base=svg_base,
        png_base=png_base,
        nomes=name_path,
        tipos=tipos,
        shared_svg_names=shared_svg_names
    )

if __name__ == "__main__":
    main()
