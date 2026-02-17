package proyecto.model;

import base.Circulo;
import base.ObjetoGraficoDeUsuario;
import base.Punto;

public class Heroe extends ObjetoGraficoDeUsuario {
    // 50 es la posicion central del canvas
    private static final int POS_HEROE_INICIAL_X = 50;
    private static final int POS_HEROE_INICIAL_Y = 50;
    private static final int VIDA_INICIAL = 10;
    private int VIDA_HEROE = VIDA_INICIAL;
    private static int FREC_ATAQUE = 200;
    // que el heroe mire a la izquierda por defecto
    private static final String HEROE_IMAGE = "../Assets/IdleL.png";

    public Heroe() {
        super(new Circulo(java.awt.Color.CYAN, new Punto(POS_HEROE_INICIAL_X, POS_HEROE_INICIAL_Y), 4));
        setImage(HEROE_IMAGE);
    }

    @Override
    public void efectuarMovimiento(double vX, double vY) {
        colocar(vX, vY);
    }

    public int getVida() {
        return VIDA_HEROE;
    }

    public static int getVidaInicial() {
        return VIDA_INICIAL;
    }

    public void setVida(int newVida) {
        VIDA_HEROE = newVida;
    }

    public void aumentarVida(int multiplo) {
        if (VIDA_HEROE < 100 && multiplo > 0)
            VIDA_HEROE += 1 * multiplo;
    }

    public void bajarVida() {
        if (VIDA_HEROE >= 1)
            VIDA_HEROE -= 1;
    }

    public static int getFrecAtaque() {
        return FREC_ATAQUE;
    }

}
