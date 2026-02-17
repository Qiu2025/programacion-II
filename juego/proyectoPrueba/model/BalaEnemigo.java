package proyectoPrueba.model;

import model.base.Circulo;
import model.base.Punto;
import stdlib.StdDraw;
import model.ObjetoGraficoMovil;

public class BalaEnemigo extends ObjetoGraficoMovil{

    private static final double VELOCIDAD = 0.5;
    private static final double RADIO = 1;

    public BalaEnemigo(double xEnemigo, double yEnemigo, double incX, double incY) {
        super(new Circulo(StdDraw.RED, new Punto(xEnemigo, yEnemigo), RADIO), incX, incY);
    }

    public static double getVelocidad(){
        return VELOCIDAD;
    }

    public void avanzar(){
        super.avanzar();
    }

}
