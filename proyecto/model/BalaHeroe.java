package proyecto.model;

import stdlib.StdDraw;
import base.Circulo;
import base.ObjetoGraficoMovil;
import base.Punto;

public class BalaHeroe extends ObjetoGraficoMovil {

    private static double velocidad = 1.5;
    private static double radio = 2.5;
    private static final String BALA__HEROE_IMAGE = "../Assets/Bullet.png";

    public BalaHeroe(double xHeroe, double yHeroe, double incX, double incY) {
        super(new Circulo(StdDraw.BLACK, new Punto(xHeroe, yHeroe), radio), incX, incY);
        setImage(BALA__HEROE_IMAGE);
    }

    public static double getVelocidad() {
        return velocidad;
    }

    public void avanzar() {
        super.avanzar();
    }

    public static void setRadio(double newRadio) {
        radio = newRadio;
    }

}