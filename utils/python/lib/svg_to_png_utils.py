import os
import cairosvg
import xml.etree.ElementTree as ET
from typing import List, Optional


def limpar_fundo_svg(caminho_svg: str) -> None:
    """
    Remove retângulos brancos (fundo branco) de um arquivo SVG.

    Esta função abre o SVG, busca elementos <rect> com atributo fill="#ffffff" (fundo branco)
    e os remove. O arquivo é sobrescrito após a limpeza.

    Parameters
    ----------
    caminho_svg : str
        Caminho completo para o arquivo SVG a ser limpo.
    """
    try:
        tree = ET.parse(caminho_svg)
        root = tree.getroot()
        for elem in list(root):
            if elem.tag.endswith('rect') and elem.attrib.get('fill', '').lower() == '#ffffff':
                root.remove(elem)
        tree.write(caminho_svg)
        print(f"Fundo branco removido: {caminho_svg}")
    except Exception as e:
        print(f"Erro ao limpar {caminho_svg}: {e}")


def converter_svg_para_png(svg_path: str, png_path: str) -> None:
    """
    Converte um arquivo SVG em um arquivo PNG utilizando a biblioteca CairoSVG.

    Parameters
    ----------
    svg_path : str
        Caminho para o arquivo SVG de origem.
    png_path : str
        Caminho de saída para o arquivo PNG gerado.
    """
    try:
        cairosvg.svg2png(url=svg_path, write_to=png_path)
        print(f"Convertido: {svg_path} -> {png_path}")
    except Exception as e:
        print(f"Erro ao converter {svg_path}: {e}")


def converter_svg_para_png_em_lote(svg_base: str, png_base: str,
                                   nomes: List[str], tipos: Optional[List[str]] = None,
                                   shared_svg_names: Optional[list[str]] = None) -> None:
    """
    Converte múltiplos arquivos SVG em PNG, organizados por diretórios e categorias.

    Cada combinação de nome e tipo é usada para montar os caminhos de origem (.svg) e destino (.png).
    A limpeza de fundo branco é realizada antes da conversão.

    Parameters
    ----------
    svg_base : str
        Diretório base contendo as pastas com arquivos SVG.
    png_base : str
        Diretório base onde os PNGs serão salvos.
    nomes : List[str]
        Lista com os nomes das subpastas (ex: nomes de peças).
    tipos : List[str]
        Lista de tipos que compõem os sufixos dos arquivos (ex: "Base", "Silhueta").
    """
    if not tipos and not shared_svg_names:
        raise ValueError("Você deve fornecer 'tipos' ou 'shared_svg_names'.")

    for nome in nomes:
        svg_dir = os.path.join(svg_base, nome)
        png_dir = os.path.join(png_base, nome)

        os.makedirs(svg_dir, exist_ok=True)
        os.makedirs(png_dir, exist_ok=True)

        if tipos:
            arquivos = [nome.capitalize() + tipo + ".svg" for tipo in tipos]
        else:
            arquivos = shared_svg_names

        for arquivo_svg in arquivos:
            svg_path = os.path.join(svg_dir, arquivo_svg)
            nome_sem_extensao = os.path.splitext(arquivo_svg)[0]
            png_path = os.path.join(png_dir, nome_sem_extensao + ".png")

            if os.path.exists(svg_path):
                limpar_fundo_svg(svg_path)
                converter_svg_para_png(svg_path, png_path)
            else:
                print(f"Arquivo não encontrado: {svg_path}")
