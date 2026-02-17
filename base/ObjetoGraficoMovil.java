package base;

public abstract class ObjetoGraficoMovil extends ObjetoGrafico {
    private double incX;
    private double incY;

    public ObjetoGraficoMovil(IFigura figura, double incX, double incY) {
        super(figura);
        this.incX = incX;
        this.incY = incY;
    }

    public double getIncX() {
        return incX;
    }

    public void setIncX(double incX) {
        this.incX = incX;
    }

    public double getIncY() {
        return incY;
    }

    public void setIncY(double incY) {
        this.incY = incY;
    }

    public void avanzar() {
        colocar(incX, incY);
    }

}
