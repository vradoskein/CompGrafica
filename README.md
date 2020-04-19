# PaintBrush [![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

Trabalho realizado na disciplina Computação Gráfica 2020-1

## Organização

O projeto esta estruturado da seguinte forma:
* Pasta src
    * Arquivo SwingPaint.java
        * Implementação da interface.

    * Arquivo DrawArea.java
        * Criação da área de desenho
        * Click listener
        * Operações de salvar, abrir, desfazer, limpar a tela
        * Chamada dos métodos para plotar no canvas

     * Pasta plots
        * Operações para desenho dos objetos no canvas

     * Pasta tranforms
        * Operações de translação, rotação, reflexão e escala para os objetos
        
## Application

Inside /Paint/dist is a .JAR file. Just execute it.

## Installation Linux and MacOS

Clone the project with 

```bash
git clone git@github.com:vradoskein/CompGrafica.git
```

Go to /CompGrafica/Paint/src
```bash
cd CompGrafica/Paint/src
```

Use the following line to compile
```bash
javac DrawArea.java SwingPaint.java clipping/*.java plots/*.java obj/*.java transforms/*.java
```

And run with 
```bash
java SwingPaint
```

## Usage

* Save
  * Salva os objetos em um arquivo .txt

* Open
  * Percorre o arquivo restaurando os objetos e redesenhando
* Clear
  * Limpa o canvas
* Ponto
  * Desenha um ponto na tela assim que clicado no canvas
* Retangulo 
  * Desenha um retangulo a partir de dois pontos clicados
* DDA
  * Desenha uma reta com o algoritmo DDA a partir de dois pontos clicados
* Bresenham
  * Desenha uma reta com o algoritmo Bresenham a partir de dois pontos clicados
* Circunferência 
   * Desenha uma circunferência a partir de dois pontos, o primeiro sendo o centro e o segundo o raio
* Translação 
   * Abre uma dialog box para selecionar o objeto a ser transladado, abre uma nova dialog box para inserir os valores de X e Y para a translação e aplica a operação. 
* Rotação
   * Abre uma dialog box para selecionar o objeto a ser rotacionado, abre uma nova dialog box para inserir o grau para rotação
* Escala
   * Abre uma dialog box para selecionar o objeto a ser escalado, abre uma nova dialog box para selecionar o tamanho da escala
* Reflexão
   * Abre uma dialog box para selecionar o objeto a ser rotacionado, abre uma nova dialog box para selecionar em qual eixo refletir X, Y, XY. Aplica a reflexão com base na rotação
* Liang
   * Espera dois clicks no canvas, delimitando a area de exibicao, apos isso eh aplicado o algoritmo de Liang-Barsky
* Cohen
   * Espera dois clicks no canvas, delimitando a area de exibicao, apos isso eh aplicado o algoritmo de Cohen Sutherland
* Flood Fill
   * Espera um click no canvas para começar a preencher a partir do pixel selecionado.
* Remover ultimo objeto
   * Remove o ultimo objeto criado no canvas.


## Contributing
    * Trabalho feito por:
    * Bernado Victor (@bernardo6526)
    * Carlos Nassif (@CarlosNassif)
    * João Rene (@Vradoskein)
    * Pedro Diogenes (@Pdiogenes)

Pull requests são bem-vindos.



## License
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

