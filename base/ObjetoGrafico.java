package base;

import stdlib.StdDraw;
import tads.IList;
import tads.LinkedList;

public abstract class ObjetoGrafico {
    private IList<IFigura> figura = new LinkedList<>();
    private String imageFilename = null;

    public ObjetoGrafico(IFigura figura) {
        this.figura.add(figura);
    }

    public void setImage(String filename) {
        imageFilename = filename;
    }

    // Si la imagen no es nula, pintar la imagen. En otro caso pintar solo la figura
    public void pintar() {
        if (imageFilename != null)
            try {
                double ancho = figura.get(0).getWidth();
                double alto = figura.get(0).getHeight();
                StdDraw.picture(figura.get(0).getCentroide().getX(),
                        figura.get(0).getCentroide().getY(),
                        imageFilename,
                        ancho,
                        alto);
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
                figura.get(0).pintar();
            }
        else {
            figura.get(0).pintar();
        }
    }

    public void colocar(double x, double y) {
        figura.get(0).mover(x, y);
    }

    public boolean hayColision(ObjetoGrafico otraFigura) {
        return ((Figura) getFigura()).colisiona((Figura) otraFigura.getFigura());
    }

    public IFigura getFigura() {
        return figura.get(0);
    }

}
