package org.p4.juego.objetos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class Pieza {
    private static Logger logger = LogManager.getRootLogger();
    private int x;
    private int y;
    private int estado;
    private int vidas;
    private int tamano;
    public Pieza(int x, int y, int r, int vidas){
        this.x = x;
        this.y = y;
        this.tamano = r;
        this.estado = 1;
        this.vidas = vidas;
    }
    public void dibujar(Graphics g){
        //añadir color
        Color color;
        if (vidas == 3) {
            color = Color.GREEN;
        } else if (this.getVidas() >= 2) {
            color = Color.YELLOW;
        } else if(vidas >= 1){
            color = Color.BLACK;
        }else{
            color = Color.GRAY;
        }
        g.setColor(color);
        g.fillOval(x,y, tamano, tamano);
        //añadir borde
        //g.setColor(Color.BLACK);
        //g.drawOval(x,y, tamano, tamano);
    }
    public boolean contiene(int x, int y){
        if (x < (this.x + this.tamano)
                && x > (this.x)
                && y < (this.y + this.tamano)
                && y > (this.y)) {
            return true;
        }
        return false;
    }
    public void matar(){
        estado = 0;
    }
    //GETTER Y SETTER
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
    }

    public int getTamano() {
        return this.tamano;
    }
    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
    public int getVidas() {
        return vidas;
    }
    public void setVidas(int vidas) {
        this.vidas = vidas;
    }
    //GETTER Y SETTER
}
