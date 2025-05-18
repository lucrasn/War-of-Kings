# Arquivos de configuração (.json)

Esta pasta contém arquivos `.json` usados pelos scripts em `/scripts/`.

## Formato esperado para `svg_to_png_converter.py`

Cada arquivo JSON deve conter:

- `"svg_base"`: caminho para as pastas contendo os arquivos `.svg`
- `"png_base"`: caminho de destino onde os `.png` serão salvos
- `"name_path"`: lista de nomes de subpastas a serem processadas

E deve conter **um dos seguintes**:

- `"tipos"`: nomes dos sufixos dos arquivos SVG, combinados com os nomes das subpastas
- `"shared_svg_names"`: lista fixa de nomes de arquivos SVG, usados igualmente em todas as subpastas

---

### Sobre os nomes dos arquivos de entrada

Você pode estruturar os arquivos de duas formas:

#### 🔸 1. Usar `tipos` (estrutura baseada em nome + tipo)

Use esta opção se os nomes dos arquivos forem formados por `NomeTipo.svg`, por exemplo: `PeaoBase.svg`.

```json
"tipos": ["Base", "Silhueta"]
```
#### 🔸 2. Usar `shared_svg_names` (estrutura com nomes fixos)

Use esta opção se todas as subpastas contiverem os mesmos nomes de arquivos SVG.

```json
"shared_svg_names": [
  "14Grade.svg",
  "14GradeBorda.svg",
  "14TabuleiroBorda.svg",
  "14TabuleiroPintado.svg"
]
```

### Exemplo:

#### Usando `tipos`:

```json
{
  "svg_base": "assets/pecas/svg",
  "png_base": "assets/pecas/png",
  "name_path": ["bispo", "cavalo", "peao", "rainha", "rei", "torre"],
  "tipos": ["Base", "Silhueta"]
}
```
#### Usando `shared_svg_names`:

```json
{
  "svg_base": "assets/tabuleiros/svg",
  "png_base": "assets/tabuleiros/png",
  "name_path": ["tabuleirobordafina", "tabuleirobordagrossa", "tabuleirogigante"],
  "shared_svg_names": [
    "14Grade.svg",
    "14GradeBorda.svg",
    "14TabuleiroBorda.svg",
    "14TabuleiroPintado.svg"
  ]
}
```
