# Arquivos de configuração (.json)

Esta pasta contém arquivos `.json` usados pelos scripts em `/scripts/`.

## Formato esperado para `c_to_svg_converter.py`

Cada arquivo JSON deve conter:

- `"input_dir"`: caminho para os arquivos `.c`
- `"output_dir"`: onde salvar os SVGs
- `"file_names"`: lista dos nomes dos arquivos `.c` a serem processados
- `"frame_names"`: lista com os nomes dos SVGs de saída para cada frame
- `"width"`: largura de cada frame (em pixels)
- `"height"`: altura de cada frame
- `"pixel_size"`: tamanho do pixel no SVG

### Sobre os nomes dos arquivos de saída (frame_names)

Você pode definir os nomes dos arquivos SVG de duas maneiras:

#### 🔸 1. Usar `frame_names` (individual para cada arquivo)

Use esta opção se cada arquivo `.c` gerar SVGs com nomes diferentes.

```json
"frame_names": [
  ["PeaoSilhueta.svg", "PeaoBase.svg"],
  ["BispoSilhueta.svg", "BispoBase.svg"]
]
```

#### 🔸 2. Usar `shared_frame_names` (mesma lista para todos)

Use esta opção se cada arquivo `.c` gerar SVGs com nomes iguais.

```json
"shared_frame_names": [
  "14Grade.svg",
  "14GradeBorda.svg",
  "14TabuleiroBorda.svg",
  "14TabuleiroPintado.svg"
]
```

### Exemplo:

```json
{
  "input_dir": "assets/peças/C",
  "output_dir": "assets/peças/svg",
  "file_names": ["Peão.c", "Bispo.c"],
  "frame_names": [
    ["PeãoSilhueta.svg", "PeãoBase.svg"],
    ["BispoSilhueta.svg", "BispoBase.svg"]
  ],
  "width": 64,
  "height": 64,
  "pixel_size": 10
}
```