package base;

import stdlib.StdDraw;

import java.awt.event.KeyEvent;

import proyecto.Proyecto;
import proyecto.model.BalaHeroe;

import java.awt.Font;

public abstract class ObjetoGraficoDeUsuario extends ObjetoGrafico {
    // distancia que se mueve el usuario por acción (WASD)
    public static final double STEP_MOVE = 1;
    // para pausar el juego
    private boolean pausado = false;
    // para guardar el momento exacto en la que el usuario activa las habilidades
    private static double momentoActivacionHabilidad1;
    private static double momentoActivacionHabilidad2;
    // para activar/desactivar la habilidad una vez que pase la duracion/cooldown
    private static boolean habilidad1Activada = false;
    public static boolean habilidad2Activada = false;
    // cooldown para usar la habilidades otra vez
    private static final double cdHabilidad1 = 10000;
    private static final double cdHabilidad2 = 10000;
    // duracion de las habilidades
    private static final double duracionHabilidad1 = 5000;
    private static final double duracionHabilidad2 = 500;

    public ObjetoGraficoDeUsuario(IFigura figura) {
        super(figura);
    }

    public void eventoUsuarioTeclaMover() {
        double incX = 0;
        double incY = 0;

        // Usamos java.util (KeyEvent) para comprobar teclas porque el movimiento es más
        // fluido que StdDraw (nextKeyTyped)
        // Subir
        if (StdDraw.isKeyPressed(KeyEvent.VK_W))
            incY = STEP_MOVE;

        // Bajar
        if (StdDraw.isKeyPressed(KeyEvent.VK_S))
            incY = -STEP_MOVE;

        // Derecha
        if (StdDraw.isKeyPressed(KeyEvent.VK_D)) {
            incX = STEP_MOVE;
            setImage("../Assets/IdleR.png");
        }

        // Izquierda
        if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
            incX = -STEP_MOVE;
            setImage("../Assets/IdleL.png");
        }

        // Comprobar si el usuario ha presionado el teclado (este if es necesario para
        // que StdDraw.nextKeyTyped no dé error)
        if (StdDraw.hasNextKeyTyped()) {
            // Guardamos StdDraw.nextKeyTyped en un char para luego realizar la comprobación
            // porque
            // por algun motivo ejecutar dos veces StdDdraw.nextKeyTyped da error:
            // Exception in thread "main" java.util.NoSuchElementException: your program has
            // already processed all keystrokes
            char key = StdDraw.nextKeyTyped();
            pausado = false;
            // Si la tecla pulsada es p, y el juego no está pausado
            if (key == 'p' && !pausado) {
                // imprimir texto
                StdDraw.clear();
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setFont(new Font("font", Font.CENTER_BASELINE, 30));
                StdDraw.text(50, 80, "Juego pausado. Presiona 'p' para continuar...");
                StdDraw.textLeft(32.5, 65, "Subir:             W");
                StdDraw.textLeft(32.5, 60, "Izquierda:       A");
                StdDraw.textLeft(32.5, 55, "Bajar:              S");
                StdDraw.textLeft(32.5, 50, "Derecha:        D");
                StdDraw.textLeft(32.5, 45, "Habilidad 1:    1");
                StdDraw.textLeft(32.5, 40, "Habilidad 2:    2");
                StdDraw.textLeft(32.5, 35, "Pausar:           p");
                StdDraw.show();
                // pausar el juego hasta que se presione la tecla p otra vez
                while (!pausado) {
                    if (StdDraw.hasNextKeyTyped() && StdDraw.nextKeyTyped() == 'p')
                        pausado = true;
                }
            } else if (key == '1') {
                // Si la habilidad 1 no está activa y la habilidad no está en cooldown,
                // y además se ha presionado la tecla 1 (if de arriba), habilidad 1 pasa a estar
                // activada
                if (!habilidad1Activada && System.currentTimeMillis() > momentoActivacionHabilidad1 + duracionHabilidad1
                        + cdHabilidad1) {
                    habilidad1Activada = true;
                    momentoActivacionHabilidad1 = System.currentTimeMillis();
                }
            } else if (key == '2') {
                // Si la habilidad 2 no está activa y la habilidad no está en cooldown,
                // y además se ha presionado la tecla 2 (if de arriba), habilidad 2 pasa a estar
                // activa
                if (!habilidad2Activada && System.currentTimeMillis() > momentoActivacionHabilidad2 + duracionHabilidad2
                        + cdHabilidad2) {
                    habilidad2Activada = true;
                    BalaHeroe.setRadio(5);
                    momentoActivacionHabilidad2 = System.currentTimeMillis();
                }
            }
        }
        // Si la habilidad 1 está activa y ha pasado el tiempo de duración, la
        // desactivamos
        // (efecto de habilidad 1 es mantener al heroe inmune de ataques, implementada
        // en Proyecto -> comprobarColisiones)
        if (habilidad1Activada && System.currentTimeMillis() > momentoActivacionHabilidad1 + duracionHabilidad1) {
            habilidad1Activada = false;
        }

        // Si la habilidad 2 está activa
        if (habilidad2Activada) {
            // si ha pasado el tiempo de duración, desactivamos la habilidad y cambiamos el
            // radio de la bala a la inicial
            if (System.currentTimeMillis() > momentoActivacionHabilidad2 + duracionHabilidad2) {
                habilidad2Activada = false;
                BalaHeroe.setRadio(2.5);
            }
            double xHeroe = Proyecto.getHeroe().getFigura().getCentroide().getX();
            double yHeroe = Proyecto.getHeroe().getFigura().getCentroide().getY();
            double xMouse = StdDraw.mouseX();
            double yMouse = StdDraw.mouseY();

            double distanciaAMouse = Math.sqrt(Math.pow(xMouse - xHeroe, 2) + Math.pow(yMouse - yHeroe, 2));
            double velocidadBalaHeroe = BalaHeroe.getVelocidad() * 2;

            double incXBalasHeroe = velocidadBalaHeroe * (xMouse - xHeroe) / distanciaAMouse;
            double incYBalasHeroe = velocidadBalaHeroe * (yMouse - yHeroe) / distanciaAMouse;

            Proyecto.getBalasHeroe().add(new BalaHeroe(xHeroe, yHeroe, incXBalasHeroe, incYBalasHeroe));
        }

        efectuarMovimiento(incX, incY);
    }

    public abstract void efectuarMovimiento(double vX, double vY);

    public static boolean habilidad1Activada() {
        return habilidad1Activada;
    }

    public static boolean habilidad2Activada() {
        return habilidad2Activada;
    }

    public static double momentoActivacionHabilidad1() {
        return momentoActivacionHabilidad1;
    }

    public static double momentoActivacionHabilidad2() {
        return momentoActivacionHabilidad2;
    }

    public static double duracionHabilidad1() {
        return duracionHabilidad1;
    }

    public static double duracionHabilidad2() {
        return duracionHabilidad2;
    }

    public static double cdHabilidad1() {
        return cdHabilidad1;
    }

    public static double cdHabilidad2() {
        return cdHabilidad2;
    }

}
