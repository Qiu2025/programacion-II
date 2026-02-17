package proyectoPrueba;

import juego.Juego2DBase;
import proyectoPrueba.model.*;
import tads.ArrayList;
import stdlib.StdDraw;
import model.ObjetoGraficoDeUsuario;
import model.base.Punto;
import java.awt.Font;

public class Proyecto extends Juego2DBase {

   private ArrayList<BalaEnemigo> balasEnemigo = new ArrayList<BalaEnemigo>();
   private ArrayList<Enemigo> enemigos = new ArrayList<Enemigo>();
   private static ArrayList<BalaHeroe> balasHeroe = new ArrayList<BalaHeroe>();
   private ArrayList<Banana> bananas = new ArrayList<Banana>();
   private static Heroe heroe = new Heroe();

   // variables que guardan temporalmente el tiempo actual para conseguir un retardo antes de la primera aparición de los objetos
   private double tiempoAtaqueHeroe;
   private double tiempoAparicionEnemigo;
   private double tiempoAparicionBanana;

   private int score = 0;
   private int dificultad = 1;

    public Proyecto(){
        // sumar "n" para hacer que la primera aparición tenga un retardo de "n"segundos
        tiempoAtaqueHeroe = System.currentTimeMillis() + 1000;
        tiempoAparicionEnemigo = System.currentTimeMillis() + 1000;
        tiempoAparicionBanana = System.currentTimeMillis() + 2000;
    }

    public static Heroe getHeroe(){
        return heroe;
    }

    public static ArrayList<BalaHeroe> getBalasHeroe(){
        return balasHeroe;
    }

    @Override
    protected void finalizarJuego() {
        Font font = new Font("Arial", Font.BOLD, 60);
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(font);
        StdDraw.text(50, 60, "Score: " + score);
        StdDraw.text(50, 50, "Dificultad: " + dificultad);
        StdDraw.show();
    }

    @Override
    protected boolean comprobarCondicionesSeguirJugando() {
        return heroe.getVida() <= 0;
    }

    @Override
    protected void pintarObjetos() {

        // Pintar bananas
        for(int i = 0; i<bananas.size(); i++)
            bananas.get(i).pintar();

        // Pintar balasEnemigo
        for(int i = 0; i<balasEnemigo.size(); i++)
            balasEnemigo.get(i).pintar();
            
        // Pintar balasHeroe
        for(int i = 0; i<balasHeroe.size(); i++)
            balasHeroe.get(i).pintar();

        // Pintar enemigos
        for(int j = 0; j<enemigos.size(); j++)
            enemigos.get(j).pintar();
        
        // Pintar heroe
        heroe.pintar();
        
        // Pintar escudo si habilidad1 está activada
        if(ObjetoGraficoDeUsuario.habilidad1Activada())
            StdDraw.picture(heroe.getFigura().getCentroide().getX(), heroe.getFigura().getCentroide().getY(), "./Escudo.png", heroe.getFigura().getWidth(), heroe.getFigura().getWidth());

        // Pintar barra de vida del heroe
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        StdDraw.filledRectangle(20+30*heroe.getVida()/Heroe.getVidaInicial(), 95, 30*heroe.getVida()/Heroe.getVidaInicial(), 3);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(20+30, 95, 30, 3);

        // Pintar texto
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(50, 2, "Pulse 'p' para ver controles");
        Font font = new Font("font", Font.BOLD, 20);
        StdDraw.setFont(font);
        StdDraw.text(10, 95, "Vida: " + heroe.getVida());
        StdDraw.text(90, 95, "Score: " + score);
        StdDraw.text(90, 90, "Dificultad: " + dificultad);

        // Pintar cd de habilidades

            //Habilidad 1
        double ahora = System.currentTimeMillis();
        boolean h1Activada = ObjetoGraficoDeUsuario.habilidad1Activada();
        double mH1 = ObjetoGraficoDeUsuario.momentoActivacionHabilidad1();
        double dH1 = ObjetoGraficoDeUsuario.duracionHabilidad1();
        double cdH1 = ObjetoGraficoDeUsuario.cdHabilidad1();
        StdDraw.setPenColor(StdDraw.GRAY);
        if(!h1Activada && ahora < mH1+dH1+cdH1)
            StdDraw.filledRectangle(5, 11 + 4*((ahora-(mH1 + dH1))/cdH1), 4, 4*(ahora-(mH1 + dH1))/cdH1);
        
        StdDraw.picture(5, 15 , "./Escudo.png", 5, 5);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(5, 15, 4, 4);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(8, 12, "1");

            //Habilidad 2
        boolean h2Activada = ObjetoGraficoDeUsuario.habilidad2Activada();
        double mH2 = ObjetoGraficoDeUsuario.momentoActivacionHabilidad2();
        double dH2 = ObjetoGraficoDeUsuario.duracionHabilidad2();
        double cdH2 = ObjetoGraficoDeUsuario.cdHabilidad2();
        StdDraw.setPenColor(StdDraw.GRAY);
        if(!h2Activada && ahora < mH2+dH2+cdH2)
            StdDraw.filledRectangle(5, 1 + 4*((ahora-(mH2 + dH2))/cdH2), 4, 4*(ahora-(mH2 + dH2))/cdH2);

        StdDraw.picture(5, 5 , "./Bullet.png", 7, 7);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.rectangle(5, 5, 4, 4);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(8, 2, "2");
    }

    @Override
    protected void comprobarColisiones() {
       for(int i = 0 ; i<balasEnemigo.size() ; i++){
            // Si la bala de enemigos sale de la ventana, se borra automáticamente
            if(balasEnemigo.get(i).getFigura().getCentroide().getX() < 0 || balasEnemigo.get(i).getFigura().getCentroide().getX() > 100
            || balasEnemigo.get(i).getFigura().getCentroide().getY() < 0 || balasEnemigo.get(i).getFigura().getCentroide().getY() > 100)
            {
                balasEnemigo.remove(i);
            //Si la bala colisiona con el heroe, se borra y baja la vida del heroe si habilidad 1 no está activa
            }
            else if(balasEnemigo.get(i).hayColision(heroe))
            {
                balasEnemigo.remove(i);
                if(!ObjetoGraficoDeUsuario.habilidad1Activada())
                    heroe.bajarVida();
            }
        }
        
        for(int i = balasHeroe.size()-1 ; i >= 0 ; i--){
            // Si la bala de heroe sale de la ventana, se borra automáticamente
            if(balasHeroe.get(i).getFigura().getCentroide().getX() < 0 || balasHeroe.get(i).getFigura().getCentroide().getX() > 100
            || balasHeroe.get(i).getFigura().getCentroide().getY() < 0 || balasHeroe.get(i).getFigura().getCentroide().getY() > 100)
            {
                balasHeroe.remove(i);

            // Si la bala de heroe colisiona con algun enemigo, borrar la bala y bajar vida del enemigo colisionado
            }
            else{
                boolean balaHeroeEliminado = false;
                boolean enemigoEliminado = false;
                for(int j = enemigos.size()-1 ; j >= 0 && !enemigoEliminado && !balaHeroeEliminado ; j--){
                    if(balasHeroe.get(i).hayColision(enemigos.get(j))){
                        balasHeroe.remove(i);
                        balaHeroeEliminado = true;
                        enemigos.get(j).bajarVida();
                        // Si la vida del enemigo colisionado es cero, eliminar
                        if(enemigos.get(j).getVida() <= 0){
                            enemigos.remove(j);
                            score += 1;
                            enemigoEliminado = true;
                        }
                    }
                }
            }        
        }
        
        // Si heroe colisiona con banana, aumenta su vida en 2
        for(int i = bananas.size()-1; i >= 0; i--){
                if(heroe.hayColision(bananas.get(i))){
                    heroe.aumentarVida(2);
                    if(heroe.getVida() > Heroe.getVidaInicial())
                        heroe.setVida(Heroe.getVidaInicial());
                    bananas.remove(i);
            }
        }
    }

    @Override
    protected void moverObjetos() {
        // Si el usuario presiona una tecla comprobar si es una de las que tiene funcionalidades (WASD y P), si lo es realizar
        // la accion correspondiente. Luego comprobar si el heroe tras la acción se sale del canvas, en cuyo caso moverlo a la
        // esquina contraria
        heroe.eventoUsuarioTeclaMover();
        asegurarDentroDeCanvas();

        // Mover balasEnemigo
        for(int i = 0; i < balasEnemigo.size();i++)
            balasEnemigo.get(i).avanzar();

        // Mover balasHeroe
        for(int i = 0; i < balasHeroe.size(); i++)
            balasHeroe.get(i).avanzar();

        // Mover enemigos
        for(int i = 0; i < enemigos.size(); i++)
            enemigos.get(i).avanzar();
    }

    @Override
    protected void crearObjetos() {

        // crear enemigos si ha pasado el "retardo de aparicion del primer enemigo" establecido (tiempoAparicionEnemigo) 
        // + la frecuencia de aparicion del enemigo (frecAparicionEnemigo)
        if(System.currentTimeMillis() > tiempoAparicionEnemigo + Enemigo.getFrecAparicionEnemigo()){
            if(score > dificultad*2)
                dificultad += 1;

            // Dificultad = numero de enemigos
            if(enemigos.size() < dificultad)
                enemigos.add(new Enemigo());

            tiempoAparicionEnemigo = System.currentTimeMillis();
        }

        double xHeroe = heroe.getFigura().getCentroide().getX();
        double yHeroe = heroe.getFigura().getCentroide().getY();
        double velocidadBalaEnemigo = -BalaEnemigo.getVelocidad();

        for(int i = 0; i<enemigos.size(); i++){
            if(System.currentTimeMillis() > enemigos.get(i).getTiempoAtaqueEnemigo() + enemigos.get(i).getFrecAtaque()){
            double xEnemigo = enemigos.get(i).getFigura().getCentroide().getX();
            double yEnemigo = enemigos.get(i).getFigura().getCentroide().getY();
            double distanciaAEnemigo = Math.sqrt(Math.pow(xEnemigo-xHeroe,2)+ Math.pow(yEnemigo-yHeroe,2));
            double incXEnemigo = velocidadBalaEnemigo*(xEnemigo-xHeroe)/distanciaAEnemigo;
            double incYEnemigo = velocidadBalaEnemigo*(yEnemigo-yHeroe)/distanciaAEnemigo;
            balasEnemigo.add(new BalaEnemigo(xEnemigo,yEnemigo, incXEnemigo, incYEnemigo));  

            enemigos.get(i).setTiempoAtaqueEnemigo(System.currentTimeMillis()); 
            }
        }

        // Crear balaheroe si ha pasado el retardo + frecuencia
        if(System.currentTimeMillis() > tiempoAtaqueHeroe + Heroe.getFrecAtaque()){
            double xMouse = StdDraw.mouseX();
            double yMouse = StdDraw.mouseY();
            double distanciaAMouse = Math.sqrt(Math.pow(xMouse-xHeroe,2)+ Math.pow(yMouse-yHeroe,2));
            double velocidadBalaHeroe = BalaHeroe.getVelocidad();
            double incXHeroe = velocidadBalaHeroe*(xMouse-xHeroe)/distanciaAMouse;
            double incYHeroe = velocidadBalaHeroe*(yMouse-yHeroe)/distanciaAMouse;
            
            balasHeroe.add(new BalaHeroe(xHeroe,yHeroe, incXHeroe, incYHeroe));

            tiempoAtaqueHeroe = System.currentTimeMillis();
        }

        // pintar bananas si ha pasado retardo + frecuencia
        if(System.currentTimeMillis() > tiempoAparicionBanana + Banana.getFrecAparicion()){
            if(bananas.size() < 3)
                bananas.add(new Banana());

            tiempoAparicionBanana = System.currentTimeMillis();
        }
    }

    protected void asegurarDentroDeCanvas(){
        Punto centroHeroe = heroe.getFigura().getCentroide();
        if(centroHeroe.getX() < 2)
            centroHeroe.setX(98);
        else if(centroHeroe.getX() > 98)
            centroHeroe.setX(2);

        if(centroHeroe.getY() < 2)
            centroHeroe.setY(98);
        else if(centroHeroe.getY() > 98)
            centroHeroe.setY(2);
    }

/*
    // Comprobar si la bala sale de la ventana
    public boolean balaFueraDeVentana(ArrayList<Balas> al){
        boolean balaFueraDeVentana = true;
        for(int i = 0; i<al.size() && balaFueraDeVentana;i++){
            balaFueraDeVentana = al.get(i).getFigura().getCentroide().getX() < 0 || al.get(i).getFigura().getCentroide().getX() > ESCALA
            || al.get(i).getFigura().getCentroide().getY() < 0 || al.get(i).getFigura().getCentroide().getY() > ESCALA;
        }
        return balaFueraDeVentana;
    }
*/

}
