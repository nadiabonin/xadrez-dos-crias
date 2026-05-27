# Xadrez dos Crias - Interface Gráfica em Pixel Art

## Como Executar

Para iniciar o jogo com a interface gráfica, execute:

```bash
java ui.TelaInicial
```

## Recursos

✨ **Interface Gráfica em Pixel Art**
- Tabuleiro 8x8 com estilo pixel art
- Peças representadas em pixel art único para cada tipo
- Seleção visual de casas com destaque em amarelo

🎮 **Modos de Jogo**
- Humano vs Humano
- Humano vs Bot (Fácil - movimentos aleatórios)
- Humano vs Bot (Difícil - algoritmo Minimax com profundidade 4)

💾 **Salvamento de Partida**
- Pressione `S` ou clique no botão "Salvar Partida"
- Carregue partidas salvas no menu inicial

## Controles

| Ação | Controle |
|------|----------|
| Selecionar peça | Clique na peça |
| Mover peça | Clique no destino |
| Salvar partida | `S` ou botão Salvar |
| Nova partida | `ESC` ou botão Nova Partida |

## Representação das Peças em Pixel Art

- **Rei ♔**: Quadrado com topo em forma de cruz
- **Rainha ♕**: Quadrado com topo em 5 pontos
- **Torre ♖**: Quadrado com almenas (topo quadrado)
- **Bispo ♗**: Formato alongado com ponto no topo
- **Cavalo ♘**: Forma de L com cabeça destacada
- **Peão ♙**: Forma simples e compacta

## Paleta de Cores

- **Tabuleiro**: Marrom claro e escuro (estilo xadrez clássico)
- **Peças Brancas**: Branco com bordas pretas
- **Peças Pretas**: Cinza escuro com bordas brancas
- **Fundo**: Cinza escuro

## Requisitos

- Java 8 ou superior
- Swing (incluído no JDK)
