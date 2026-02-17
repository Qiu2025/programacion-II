package model.base;

import java.awt.Color;

public abstract class Figura implements IFigura {
    protected Color color;
    protected Punto centroide;
    protected Punto[] boundingBox = new Punto[2];
    protected java.awt.geom.Area area;
    protected static int ESCALA = 1000;

    public Figura(Color color, Punto centroide) {
        this.color = color;
        this.centroide = centroide;
    }

    protected abstract void initArea();

    public abstract void pintar() ;
    
    public void mover(double incX, double incY) {
        centroide.setX(centroide.getX() + incX);
        centroide.setY(centroide.getY() + incY);
    }

    public boolean colisiona(Figura otraFigura){
        if (!(boundingBox[1].getX() < otraFigura.boundingBox[0].getX() || 
              boundingBox[0].getX() > otraFigura.boundingBox[1].getX() || 
              boundingBox[1].getY() < otraFigura.boundingBox[0].getY() || 
              boundingBox[0].getY() > otraFigura.boundingBox[1].getY())){
            java.awt.geom.Area intersection = new java.awt.geom.Area(area);
            intersection.intersect(otraFigura.area);
            return !intersection.isEmpty();
        }
        return false;
    }

    public Punto getCentroide(){
        return centroide;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getWidth(){
        return boundingBox[1].getX()-boundingBox[0].getX();
    }

    public double getHeight(){
        return boundingBox[1].getY()-boundingBox[0].getY();
    }
}
