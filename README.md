![model-picture](https://github.com/coslatte/JTetris/blob/master/model-looklike/Modelo%20Tetris%201.3.svg?sanitize=true)

# The Game
We are coding this Tetris clone in Java for the willing of learning and practice the language, aswell as having fun building it.

# Challenge
Code the entire game only using native Java components such as `javax.swing` or `java.awt`. We want to build this using non-generated code from tools like **NetBeans** Interface Desing features, we really want to build it _'by hand'_.

# Our tiny team
We are software engineering students who don't know much, but what we know is that we are coding this up!
We really hope the best LMAO. Hope this proyect turns a completely success. Cheers! 🍷

---

# 2026-10-01 - Juego terminado (slop hecho con IA)

> **Aviso de transparencia:** lo que sigue lo escribió y programó una IA. El proyecto estaba abandonado
> desde 2021 con el juego sin poder jugarse, y alguien lo terminó de un tirón para poder jugarlo. El código
> que hay hoy en `src/` es en su mayor parte **slop generado con IA**, no el trabajo original del equipo.
> Sirve para jugar y de base para seguir, pero no lo confused con el trabajo de los autores originales.

## Por qué estaba abandonado

En el estado en que quedó, el juego compilaba pero **no se podía jugar**: se abría el menú, se tocaba PLAY
y la pantalla se quedaba en un tablero vacío e inservible. Las razones concretas eran:

- **El bucle del juego no podía terminar nunca.** La condición del `while` era `while (gameThread != null)`,
  y `gameThread` no se ponía nunca en `null`. El hilo entraba en un bucle infinito quemando un núcleo de CPU.
- **La rotación estaba rota.** El giro antihorario copiaba filas enteras en vez de transponer la matriz, y
  `rotatedShape` se calculaba pero nunca se volvía a asignar a `actualShape`: es decir, rotar no cambiaba
  nada. Las tablas del SRS existían documentadas pero no las usaba nadie.
- **Faltaban las piezas.** No había nada que generara tetrominos, así que el tablero no tenía nada que
  dibujar.
- **Los controles estaban vacíos.** `moveToTheLeft()`, `moveToTheRight()`, `clearRow()`,
  `insertTetromino()` y `update()` estaban escritos como métodos vacíos. No había ni un solo `KeyListener`.
- **Dos matrices que no terminaban devez en cuando.** `fieldArray` y `checkArray` quedaban a medio
  sincronizar, y `endLine` no se limpiaba nunca.
- **El panel de puntaje era un adorno.** Dibujaba dos recuadros y nada más: no mostraba puntos, ni líneas,
  ni nivel.

## Qué se agregó y qué se corrigió

### Correcciones sobre lo que ya estaba

- **Rotación de verdad**: se arregló la transposición antihoraria y ahora la rotación se aplica recién cuando
  el tablero confirma que es legal. Si no hay lugar, se prueban los tests del SRS y, si ninguno sirve, la
  pieza se queda como estaba sin corromper su estado interno (`applyRotation` / `cancelRotation`).
- **`typeId` por tetromino**: el color era un campo `static` compartido, así que todas las piezas tomaban el
  color de la última creada. Ahora cada tetromino tiene su propio identificador y color.
- **Bucle del juego que se puede terminar y que no quema la CPU**: el `while` ahora mira una bandera
  interrumpible, y entre fotograma y fotograma **espera lo que le falta** en vez de recalcular a toda
  velocidad. También se sacaron los `System.out.println` que escribían "FPS: ..." cada segundo.
- **`GamePanel` no pinta dos veces lo mismo**: el `paintComponent` dibujaba a los hijos *y* después los
  volvía a pintar a mano encima.
- **`MenuButton`**: el texto se posicionaba con coordenadas que el propio botón se asignaba a sí mismo
  durante el pintado del borde, lo que dejaba el texto descolocado. Ahora el botón usa su propio tamaño real.
- **`MainMenuPanel`**: los botones se posicionaban dentro de `paintComponent`, algo que dibuja a los hijos
  con la posición del fotograma anterior. Ahora se posicionan una vez en el constructor.
- **Tamaños que no se pisan**: `GridPanel` (380 px) + `ScorePanel` (300 px) no entraban en los 650 px de la
  ventana, así que los paneles se superponían y el de la derecha quedaba cortado. Ahora suman exacto y la
  ventana usa `pack()`.
- **El ícono ya no tira el programa**: `ImageIO.read` sobre un archivo inexistente reventaba con
  `RuntimeException`. Ahora avisa por consola y sigue con el ícono por defecto.
- **Las filas ocultas del tablero**: el dibujo arrancaba en la fila 4 pero se indexaba con `celdas[i - 3]`,
  lo que dejaba una fila sin dibujar y desfasaba el resto. Se corrigió la cuenta y el marco ahora abraza
  exactamente la parte visible.

### Lo nuevo

- **`TetrominoFactory`** (`src/Components/Tetros/TetrominoFactory.java`): generador de piezas con el
  **7-bag randomizer** de las Tetris Guidelines. Baraja las 7 piezas y las reparte todas antes de barajar
  otra vez, para que no se repitan cinco iguales seguidas.
- **Tablero completo**: detección de colisiones, movimiento izquierda/derecha, *soft drop*, *hard drop*,
  gravedad con velocidad creciente por nivel (fórmula de las Guidelines), *lock delay* de 500 ms para poder
  mover una pieza ya apoyada, borrado de filas completas, puntaje (100/300/500/800 × nivel, más 1 por celda
  de *soft drop* y 2 por celda de *hard drop*), subida de nivel cada 10 líneas y final de partida.
- **Rotación contra el techo y la pared** usando los tests del SRS que ya estaban escritos en el código
  original: se prueban en orden y gana el primero que no choca.
- **Teclado completo**: flechas para mover, abajo para bajar, espacio para soltar, arriba o `X` para rotar,
  `Z` para rotar al revés, `P` o `Esc` para pausar y `R` para reiniciar.
- **Movimiento con tecla mantenida** (DAS/ARR), para poder llevar la pieza al borde rápido y después
  colocarla en la columna exacta.
- **Hilos bien separados**: las teclas llegan en el hilo de Swing, así que se encolan y el hilo del juego las
  aplica en su paso. El estado del tablero se protege con un lock para que el hilo de Swing nunca dibuje una
  matriz a medio actualizar.
- **Pieza fantasma**: muestra dónde se apoyaría la pieza si la soltaras ahora.
- **Puntaje, líneas y nivel** en pantalla, más la **vista previa** de la próxima pieza.
- **Pantalla de opciones real**: elegir el nivel inicial (de 1 a 20, y de verdad afecta a la partida) y ver
  la lista de teclas. Antes era un panel vacío sin botón para volver.
- **Avisos de pausa y de fin de partida**, y reinicio con `R`.

## Cómo se verificó

No hay framework de tests en el proyecto, así que se escribió un banco de pruebas aparte, fuera de `src/`,
que corre la lógica sin pantalla y dibuja cada pantalla en un PNG en memoria para revisarla:

- La rotación de las 7 piezas cuatro veces vuelve exactamente a la forma original.
- Rotar a la derecha equivale a rotar tres veces a la izquierda.
- Cada grupo de 7 piezas trae los 7 tetrominos distintos.
- La tabla SRS responde en rango y sin desbordar ante valores raros.
- `clearRow()` borra las filas completas, deja las incompletas donde estaban y baja lo que había arriba.
- Un Tetris de 4 líneas playthrough real suma 4 líneas y 800 puntos.
- La gravedad apoya sola la pieza; apilando sin borrar la partida termina.
- En sesiones largas de juego al azar ningún bloque se sale del tablero y siempre se llega al final.
- Los tres paneles (menú, opciones y partida) se dibujan sin excepciones.

Las tres pantallas se revisaron visualmente y se corrigieron tres cosas que solo se veían al mirarlas: el
marco del tablero se salía de la ventana, el texto de ayuda quedaba cortado, y la pieza fantasma era
invisible porque la línea gris de la celda la tapaba justo encima.

## Cómo jugarlo

Con JDK 21 y desde la raíz del proyecto:

```bash
javac -encoding UTF-8 -d out/classes $(find src -name "*.java")
java -cp out/classes GUI.UI
```

El ícono se busca en `icon/jtetris-icon.png`, así que hay que correrlo desde la raíz del repo.
