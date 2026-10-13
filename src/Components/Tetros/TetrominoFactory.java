package Components.Tetros;

import Components.Tetros.Primitives.Tetromino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Saca tetrominos en el orden en que los va a necesitar el tablero.
 * <p>
 * Se usa el <b>7-bag randomizer</b>: se mezclan los siete tetrominos, se entregan todos en ese orden y recién
 * entonces se vuelve a mezclar una bolsa nueva. Es el criterio que usan las Guideline Tetris, y sirve
 * justamente para que no aparezcan cinco tetrominos iguales seguidos, que es lo que vuelve injugable un
 * Tetris generado con números realmente aleatorios.
 */
public class TetrominoFactory {

    /**
     * Cantidad de tetrominos que tiene una bolsa completa.
     */
    private static final int BAG_SIZE = 7;

    /**
     * Tetrominos que aún no salieron de la bolsa actual.
     */
    private final List<Byte> bag = new ArrayList<>(BAG_SIZE);

    /**
     * Devuelve el próximo tetromino, reponiendo la bolsa cuando se vacía.
     * <p>
     * Cada llamada entrega una pieza <strong>nueva</strong>, porque la rotación y la posición son estado
     * propio de cada tetromino que está en juego. Compartir una misma instancia entre dos piezas haría que
     * al rotar una se rotara también la otra.
     */
    public Tetromino next() {
        if (bag.isEmpty()) {
            refillBag();
        }
        return create(bag.remove(bag.size() - 1));
    }

    /**
     * Mira la próxima pieza sin sacarla de la bolsa, para poder dibujarla como vista previa.
     * Si la bolsa está vacía la repone antes de mirar, de modo que el tablero siempre tenga una.
     */
    public Tetromino peek() {
        if (bag.isEmpty()) {
            refillBag();
        }
        return create(bag.get(bag.size() - 1));
    }

    /**
     * Descarta la bolsa en curso. El tablero la usa al reiniciar la partida para no continuar con el
     * reparto que había quedado a medias.
     */
    public void reset() {
        bag.clear();
    }

    /**
     * Crea un tetromino del tipo pedido, a partir del {@code typeId} que sus celdas llevan escrito.
     */
    private static Tetromino create(byte typeId) {
        switch (typeId) {
            case 1:
                return new Tetromino_I();
            case 2:
                return new Tetromino_J();
            case 3:
                return new Tetromino_L();
            case 4:
                return new Tetromino_O();
            case 5:
                return new Tetromino_S();
            case 6:
                return new Tetromino_T();
            case 7:
                return new Tetromino_Z();
            default:
                throw new IllegalArgumentException("No existe un tetromino con typeId " + typeId);
        }
    }

    /**
     * Llena la bolsa con un tetromino de cada tipo y la baraja.
     */
    private void refillBag() {
        bag.clear();
        for (byte id = 1; id <= 7; id++) {
            bag.add(id);
        }
        Collections.shuffle(bag);
    }
}
