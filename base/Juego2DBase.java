package base;

import java.awt.Color;
import stdlib.StdDraw;

public abstract class Juego2DBase {
    public static final int XMAX = 700;
    public static final int YMAX = 700;
    public static final int ESCALA = 100;
    public static final Color DEFAULT_COLOR = StdDraw.BLACK;

    private boolean haPerdido = false;

    public void iniciarGraficos() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(XMAX, YMAX);
        StdDraw.setScale(0, ESCALA);
        // StdDraw.setPenRadius(5);
        StdDraw.setPenColor(DEFAULT_COLOR);
    }

    public void jugar() {
        iniciarGraficos();
        while (!haPerdido) {
            // clear the screen
            clear();

            // draw the background
            StdDraw.picture(50, 50, "../Assets/Fondo.jpeg", 100, 100);

            // create objects
            crearObjetos();

            // move objects.
            moverObjetos();

            // check interactions
            comprobarColisiones();

            // render the ball and the paddle.
            pintarObjetos();

            // check game over
            haPerdido = comprobarCondicionesSeguirJugando();

            // display and pause for 10 ms
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }

            StdDraw.show();
        }

        // finish game
        finalizarJuego();
    }

    private void clear() {
        StdDraw.clear(StdDraw.LIGHT_GRAY);
    }

    abstract protected void finalizarJuego();

    abstract protected boolean comprobarCondicionesSeguirJugando();

    abstract protected void pintarObjetos();

    abstract protected void comprobarColisiones();

    abstract protected void moverObjetos();

    abstract protected void crearObjetos();
}
