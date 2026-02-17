package model.base;

public interface IFigura {
    public void pintar();

    public void mover(double incX, double incY);

    public Punto getCentroide();

    public java.awt.Color getColor();

    public void setColor(java.awt.Color c);

    public double getWidth();

    public double getHeight();
}
