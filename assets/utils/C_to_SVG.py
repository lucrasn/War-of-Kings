import os
import re

largura = 64
altura = 64
pixels_por_frame = largura * altura
pixel_size = 10

caminho_c = r"assets\peças\C"
saida_svg = r"assets\peças\svg"
os.makedirs(saida_svg, exist_ok=True)

file_name = ["Peão.c", "Bispo.c", "Torre.c", "Cavalo.c", "Rainha.c", "Rei.c"]
name_frame1 = ["PeãoSilhueta.svg", "BispoSilhueta.svg", "TorreSilhueta.svg", "CavaloSilhueta.svg", "RainhaSilhueta.svg", "ReiSilhueta.svg"]
name_frame2 = ["PeãoBase.svg", "BispoBase.svg", "TorreBase.svg", "CavaloBase.svg", "RainhaBase.svg", "ReiBase.svg"]

for i in range(len(file_name)):
    caminho_arquivo_c = os.path.join(caminho_c, file_name[i])
    with open(caminho_arquivo_c, "r") as f:
        conteudo = f.read()

    hexadecimais = re.findall(r'0x[0-9a-fA-F]+', conteudo)

    if len(hexadecimais) < 2 * pixels_por_frame:
        print(f"Erro: foram encontrados apenas {len(hexadecimais)} pixels. Esperado: {2 * pixels_por_frame}.")
        exit()

    pixels = [int(hex_value, 16) for hex_value in hexadecimais]

    # Separa os frames
    frames = [
        pixels[:pixels_por_frame],
        pixels[pixels_por_frame:2*pixels_por_frame]
    ]

    for idx, frame in enumerate(frames):
        svg = [f'<svg xmlns="http://www.w3.org/2000/svg" width="{largura * pixel_size}" height="{altura * pixel_size}">']
        
        for y in range(altura):
            for x in range(largura):
                cor = frame[y * largura + x]
                a = (cor >> 24) & 0xFF
                r = (cor >> 16) & 0xFF
                g = (cor >> 8) & 0xFF
                b = cor & 0xFF

                if a > 0:
                    svg.append(
                        f'<rect x="{x*pixel_size}" y="{y*pixel_size}" width="{pixel_size}" height="{pixel_size}" fill="rgba({r},{g},{b},{a/255:.2f})"/>'
                    )
        
        svg.append("</svg>")

        nome_base = os.path.splitext(file_name[i])[0].lower()
        subpasta_svg = os.path.join(saida_svg, nome_base)
        os.makedirs(subpasta_svg, exist_ok=True)
        saida_nome = os.path.join(subpasta_svg, name_frame1[i] if idx == 0 else name_frame2[i])
        with open(saida_nome, "w") as out:
            out.write("\n".join(svg))
        
        print(f"Gerado: {saida_nome}")
