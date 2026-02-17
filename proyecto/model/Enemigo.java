package proyecto.model;

import base.Circulo;
import base.ObjetoGraficoMovil;
import base.Punto;
import proyecto.Proyecto;

import java.awt.Color;

public class Enemigo extends ObjetoGraficoMovil {
    private static double POS_ENEMIGO_INICIAL_X = 100 * Math.random();
    private static double POS_ENEMIGO_INICIAL_Y = 0;
    private static final double VELOCIDAD = 0.5;
    private static final double RADIO = 3;
    private int VIDA = 5;
    private static final int FREC_APARICION = 1000;
    private int frecAtaque;
    private double tiempoAtaqueEnemigo;

    public Enemigo() {
        super(new Circulo(new Color(255, 255, 255), new Punto(POS_ENEMIGO_INICIAL_X, POS_ENEMIGO_INICIAL_Y), RADIO), 0,
                0);

        // Hacer aleatorio el spawnpoint del siguiente enemigo
        double nAleatorio = Math.random();
        if (nAleatorio > 0.75) {
            POS_ENEMIGO_INICIAL_X = 0 - 10 * Math.random();
            POS_ENEMIGO_INICIAL_Y = Math.random() * 100;
        } else if (nAleatorio > 0.5) {
            POS_ENEMIGO_INICIAL_X = 100 + 10 * Math.random();
            POS_ENEMIGO_INICIAL_Y = Math.random() * 100;
        } else if (nAleatorio > 0.25) {
            POS_ENEMIGO_INICIAL_X = Math.random() * 100;
            POS_ENEMIGO_INICIAL_Y = 0 - 10 * Math.random();
        } else {
            POS_ENEMIGO_INICIAL_X = Math.random() * 100;
            POS_ENEMIGO_INICIAL_Y = 100 + 10 * Math.random();
        }

        frecAtaque += 1000 * Math.random();
        frecAtaque += 1000 * Math.random();
    }

    @Override
    public void avanzar() {
        super.avanzar();

        double xEnemigo = getFigura().getCentroide().getX();
        double yEnemigo = getFigura().getCentroide().getY();
        double xHeroe = Proyecto.getHeroe().getFigura().getCentroide().getX();
        double yHeroe = Proyecto.getHeroe().getFigura().getCentroide().getY();
        double distancia = Math.sqrt(Math.pow(xEnemigo - xHeroe, 2) + Math.pow(yEnemigo - yHeroe, 2));

        // Si la distancia entre enemigo y heroe es mayor que 30, el enemigo persigue al
        // heroe
        if (distancia > 30) {
            setIncX(VELOCIDAD * (xHeroe - xEnemigo) / distancia);
            setIncY(VELOCIDAD * (yHeroe - yEnemigo) / distancia);
            // Y si es menor que 20 el enemigo se aleja del heroe
        } else if (distancia < 20) {
            setIncX(-VELOCIDAD * (xHeroe - xEnemigo) / distancia);
            setIncY(-VELOCIDAD * (yHeroe - yEnemigo) / distancia);
            // En otro caso, es decir, si la distancia está entre 20 y 30, se queda en la
            // misma posición
        } else {
            setIncX(0);
            setIncY(0);
        }
    }

    public int getVida() {
        return VIDA;
    }

    public void bajarVida() {
        if (VIDA > 0)
            VIDA -= 1;
        getFigura().setColor(new Color(255, 255 * VIDA / 5, 255 * VIDA / 5));
    }

    public static int getFrecAparicionEnemigo() {
        return FREC_APARICION;
    }

    public int getFrecAtaque() {
        return frecAtaque;
    }

    public double getTiempoAtaqueEnemigo() {
        return tiempoAtaqueEnemigo;
    }

    public void setTiempoAtaqueEnemigo(long ahora) {
        tiempoAtaqueEnemigo = ahora;
    }

}
