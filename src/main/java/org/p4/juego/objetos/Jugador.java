package org.p4.juego.objetos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.p4.juego.gui.JugadorPanel;

import java.awt.*;
import java.beans.PropertyChangeSupport;

public class Jugador {
    private final Color color;
    private Pieza[] piezas;
    private PropertyChangeSupport observado;
    public Jugador(Color color) {
        this.color = color;
        piezas = crearPiezas();
        observado =  new PropertyChangeSupport(this);
        notificar();
    }

    private Pieza[] crearPiezas() {
        Pieza[] resultado = new Pieza[7];
        int piezasPuestas = 0;
        boolean completo = false;
        while (!completo) {
            int x = (int) (Math.random() * 285);
            int y = (int) (Math.random() * 385);
            int r = 10 + (int) (Math.random() * 10);

            while (!posiblePiezaOk(x, y, resultado)) {
                x = (int) (Math.random() * 285);
                y = (int) (Math.random() * 385);
                r = 10 + (int) (Math.random() * 10);
            }

            resultado[piezasPuestas] = new Pieza(x, y, r, 3);
            piezasPuestas++;
            if (piezasPuestas == 7){
                completo = true;
            }
        }
        return resultado;
    }

    public void addListener(JugadorPanel jugadorPanel){
        observado.addPropertyChangeListener(jugadorPanel);
    }

    private boolean posiblePiezaOk(int x, int y, Pieza[] resultado) {
        for (int i = 0; i < 7; i++) {
            if (resultado[i] == null) {
                return true;
            }
            Pieza p = resultado[i];
            if (Math.abs(p.getX() - x) < 15 && Math.abs(p.getY() - y) < 15)
            {
                return false;
            }
        }
        return true;
    }

    public boolean matoPieza(int x, int y) {
        for (int i = 0; i < 7; i++) {
            if (piezas[i].contiene(x, y)) {
                if (piezas[i].getVidas() > 0) {
                    piezas[i].setVidas(piezas[i].getVidas() - 1);
                }
                if (piezas[i].getVidas() == 0) {
                    piezas[i].matar();
                }
                Batalla.getOrCreate().comprobarGanador(); // Verificar ganador después de matar la pieza
                return true;
            }
        }
        return false;
    }

    public void dibujar(Graphics g){
        for (int i = 0; i < 7; i++) {
            if (piezas[i].getEstado() == 0 && piezas[i].getVidas() == 0) continue;
            piezas[i].dibujar(g);
        }
        notificar();
    }

    public String getPiezasParaRed(){
        StringBuilder resultado = new StringBuilder();
        String separador = "";
        for (int i = 0; i < 7; i++) {

            Pieza actual = piezas[i];
            resultado.append(separador);
            resultado.append(actual.getX())
                    .append(",")
                    .append(actual.getY())
                    .append(",")
                    .append(actual.getTamano())
                    .append(",")
                    .append(actual.getEstado())
                    .append(",")
                    .append(actual.getVidas());
            separador = ",";

        }
        return resultado.toString();
    }

    public void setPosicionPiezaYEstado(int i, int x, int y, int tamano,int vidas, int estado){
        piezas[i].setX(x);
        piezas[i].setY(y);
        piezas[i].setTamano(tamano);
        piezas[i].setVidas(vidas);
        piezas[i].setEstado(estado);
    }

    public void notificar(){
        observado.firePropertyChange("JUEGO", true , false);
    }

    public Color getColor() {
        return color;
    }
    public int contarPiezasVivas(){
        int resultado = 0;
        for (int i = 0; i < 7; i++) {
            if (piezas[i].getEstado() == 1) {
                resultado++;
            }
        }
        return resultado;
    }

    public int getPiezasVivas(){
        return contarPiezasVivas();
    }

    /*
    * public String getPiezasVivasParaRed() {
        return String.valueOf(contarPiezasVivas());
    }*/

}
