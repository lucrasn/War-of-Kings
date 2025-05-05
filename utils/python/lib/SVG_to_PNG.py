import os
import cairosvg
import xml.etree.ElementTree as ET

BASE_DIR = os.path.abspath(os.path.join(os.path.dirname(__file__), '..', 'pecas'))
SVG_BASE = os.path.join(BASE_DIR, 'svg')
PNG_BASE = os.path.join(BASE_DIR, 'png')

name_path = ["bispo", "cavalo", "peao", "rainha", "rei", "torre"]
tipos = ["Base", "Silhueta"]

def limpar_fundo_svg(caminho_svg):
    try:
        tree = ET.parse(caminho_svg)
        root = tree.getroot()
        for elem in list(root):
            if elem.tag.endswith('rect') and elem.attrib.get('fill', '').lower() == '#ffffff':
                root.remove(elem)
        tree.write(caminho_svg)
        print(f"✔️ Fundo branco removido: {caminho_svg}")
    except Exception as e:
        print(f"Erro ao limpar {caminho_svg}: {e}")

for nome in name_path:
    svg_dir = os.path.join(SVG_BASE, nome)
    png_dir = os.path.join(PNG_BASE, nome)

    os.makedirs(svg_dir, exist_ok=True)
    os.makedirs(png_dir, exist_ok=True)

    for tipo in tipos:
        nome_arquivo = nome.capitalize() + tipo
        svg_path = os.path.join(svg_dir, nome_arquivo + ".svg")
        png_path = os.path.join(png_dir, nome_arquivo + ".png")

        if os.path.exists(svg_path):
            limpar_fundo_svg(svg_path)
            try:
                cairosvg.svg2png(url=svg_path, write_to=png_path)
                print(f"Convertido: {svg_path} -> {png_path}")
            except Exception as e:
                print(f"Erro ao converter {svg_path}: {e}")
        else:
            print(f"Arquivo não encontrado: {svg_path}")
