import os
import re
from typing import List, Tuple


def ler_hexadecimais_do_arquivo(caminho_arquivo: str) -> List[int]:
    """
    Lê um arquivo de texto e extrai todos os valores hexadecimais no formato 0xAARRGGBB.

    Os valores extraídos são convertidos de hexadecimal para inteiros.

    Parameters
    ----------
    caminho_arquivo : str
        Caminho para o arquivo de entrada contendo os valores hexadecimais.

    Returns
    -------
    List[int]
        Lista de inteiros correspondentes aos valores hexadecimais encontrados.
    """
    with open(caminho_arquivo, "r") as arquivo:
        conteudo: str = arquivo.read()

    padrao_hex: str = r'0x[0-9a-fA-F]+'
    encontrados: List[str] = re.findall(padrao_hex, conteudo)

    hexadecimais: List[int] = []
    for valor in encontrados:
        inteiro: int = int(valor, 16)
        hexadecimais.append(inteiro)

    return hexadecimais


def separar_em_frames(pixels: List[int], tamanho_frame: int) -> List[List[int]]:
    """
    Divide uma lista de pixels em múltiplos frames de tamanho fixo.

    Apenas frames completos são retornados; pixels restantes são descartados.

    Parameters
    ----------
    pixels : List[int]
        Lista de valores de pixels inteiros (RGBA).
    tamanho_frame : int
        Quantidade de pixels por frame.

    Returns
    -------
    List[List[int]]
        Lista de frames, onde cada frame é uma lista de pixels.
    """
    frames: List[List[int]] = []
    total_pixels: int = len(pixels)
    indice: int = 0

    while indice + tamanho_frame <= total_pixels:
        frame: List[int] = pixels[indice:indice + tamanho_frame]
        frames.append(frame)
        indice += tamanho_frame

    return frames


def extrair_rgba(pixel: int) -> Tuple[int, int, int, int]:
    """
    Extrai os componentes de cor RGBA de um valor inteiro no formato 0xAARRGGBB.

    Parameters
    ----------
    pixel : int
        Valor inteiro representando a cor no formato ARGB.

    Returns
    -------
    Tuple[int, int, int, int]
        Tupla contendo os componentes (R, G, B, A).
    """
    a = (pixel >> 24) & 0xFF
    b = (pixel >> 16) & 0xFF
    g = (pixel >> 8) & 0xFF
    r = pixel & 0xFF
    return r, g, b, a

def gerar_svg_do_frame(frame: List[int], largura: int, altura: int, pixel_size: int) -> str:
    """
    Gera uma string SVG representando um frame de imagem baseado em pixels RGBA.

    Pixels com alpha igual a 0 são ignorados.

    Parameters
    ----------
    frame : List[int]
        Lista de inteiros representando os pixels no formato ARGB.
    largura : int
        Largura da imagem em pixels.
    altura : int
        Altura da imagem em pixels.
    pixel_size : int
        Tamanho de cada "pixel" no SVG, em unidades do SVG.

    Returns
    -------
    str
        Conteúdo SVG como string.
    """
    svg: List[str] = [
        f'<svg xmlns="http://www.w3.org/2000/svg" width="{largura * pixel_size}" height="{altura * pixel_size}">'
    ]

    for y in range(altura):
        for x in range(largura):
            r, g, b, a = extrair_rgba(frame[y * largura + x])
            if a > 0:
                svg.append(
                    f'<rect x="{x * pixel_size}" y="{y * pixel_size}" width="{pixel_size}" height="{pixel_size}" '
                    f'fill="rgba({r},{g},{b},{a / 255:.2f})"/>'
                )

    svg.append("</svg>")
    return "\n".join(svg)


def salvar_svg(caminho: str, conteudo_svg: str) -> None:
    """
    Salva uma string SVG em um arquivo no disco.

    Cria o diretório de saída se ele não existir.

    Parameters
    ----------
    caminho : str
        Caminho do arquivo de saída.
    conteudo_svg : str
        Conteúdo SVG a ser salvo.
    """
    os.makedirs(os.path.dirname(caminho), exist_ok=True)
    with open(caminho, "w") as f:
        f.write(conteudo_svg)


def processar_arquivo(caminho_entrada: str, pasta_saida: str, base_nome_saida: str, 
                      largura: int, altura: int, pixel_size: int,
                      frame_names: List[str] = None) -> None:
    """
    Processa um arquivo .c contendo múltiplos frames e gera arquivos SVG.

    Parameters
    ----------
    caminho_entrada : str
        Caminho do arquivo de entrada contendo valores hexadecimais.
    pasta_saida : str
        Pasta onde os SVGs gerados serão salvos.
    base_nome_saida : str
        Nome base para os arquivos SVG (usado apenas se frame_names não for fornecido).
    largura : int
        Largura de cada frame, em pixels.
    altura : int
        Altura de cada frame, em pixels.
    pixel_size : int
        Tamanho do pixel no SVG.
    frame_names : List[str], optional
        Lista com nomes de arquivo SVG para cada frame. Se não fornecido, nomes automáticos serão usados.
    """
    pixels: List[int] = ler_hexadecimais_do_arquivo(caminho_entrada)
    tamanho_frame: int = largura * altura

    if len(pixels) < tamanho_frame:
        raise ValueError(f"Pixels insuficientes: {len(pixels)} encontrados, mínimo esperado: {tamanho_frame}.")

    frames: List[List[int]] = separar_em_frames(pixels, tamanho_frame)

    for idx, frame in enumerate(frames):
        svg: str = gerar_svg_do_frame(frame, largura, altura, pixel_size)

        if frame_names and idx < len(frame_names):
            nome_arquivo: str = frame_names[idx]
        else:
            nome_arquivo: str = f"{base_nome_saida}_frame{idx+1}.svg"

        caminho_saida: str = os.path.join(pasta_saida, nome_arquivo)
        salvar_svg(caminho_saida, svg)
        print(f"Gerado: {caminho_saida}")
