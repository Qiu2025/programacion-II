package proyecto.model;

import base.Circulo;
import base.ObjetoGrafico;
import base.Punto;
import stdlib.StdDraw;

public class Banana extends ObjetoGrafico {

    private static final String BANANA_IMAGE = "../Assets/Banana.gif";
    private static final int FREC_APARICION = 11000;

    public Banana() {
        super(new Circulo(StdDraw.GREEN, new Punto(90 * Math.random(), 90 * Math.random()), 5));
        setImage(BANANA_IMAGE);
    }

    public static int getFrecAparicion() {
        return FREC_APARICION;
    }

}
