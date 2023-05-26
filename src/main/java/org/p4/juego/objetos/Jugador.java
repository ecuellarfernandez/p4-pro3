package org.p4.juego.objetos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.p4.juego.gui.JugadorPanel;
import org.p4.juego.objetos.Batalla;
import org.p4.juego.objetos.Pieza;
import org.p4.lista.ListaDoble;

import java.awt.*;
import java.beans.PropertyChangeSupport;
import java.util.Iterator;

public class Jugador {
    private final Color color;
    private ListaDoble<Pieza> piezas;
    private PropertyChangeSupport observado;

    public Jugador(Color color) {
        this.color = color;
        piezas = crearPiezas();
        observado = new PropertyChangeSupport(this);
        notificar();
    }

    private ListaDoble<Pieza> crearPiezas() {
        ListaDoble<Pieza> resultado = new ListaDoble<>();
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

            resultado.insertar(new Pieza(x, y, r, 3));
            piezasPuestas++;
            if (piezasPuestas == 7) {
                completo = true;
            }
        }
        return resultado;
    }

    public void addListener(JugadorPanel jugadorPanel) {
        observado.addPropertyChangeListener(jugadorPanel);
    }

    private boolean posiblePiezaOk(int x, int y, ListaDoble<Pieza> resultado) {
        for (Pieza p : resultado) {
            if (Math.abs(p.getX() - x) < 15 && Math.abs(p.getY() - y) < 15) {
                return false;
            }
        }
        return true;
    }

    public boolean matoPieza(int x, int y) {
        for (Pieza pieza : piezas) {
            if (pieza.contiene(x, y)) {
                if (pieza.getVidas() > 0) {
                    pieza.setVidas(pieza.getVidas() - 1);
                }
                if (pieza.getVidas() == 0) {
                    pieza.matar();
                }
                Batalla.getOrCreate().comprobarGanador(); // Verificar ganador después de matar la pieza
                return true;
            }
        }
        return false;
    }

    public void dibujar(Graphics g) {
        for (Pieza pieza : piezas) {
            if (pieza.getEstado() == 0 && pieza.getVidas() == 0) continue;
            pieza.dibujar(g);
        }
        notificar();
    }

    public String getPiezasParaRed() {
        StringBuilder resultado = new StringBuilder();
        String separador = "";
        for (Pieza pieza : piezas) {
            resultado.append(separador);
            resultado.append(pieza.getX())
                    .append(",")
                    .append(pieza.getY())
                    .append(",")
                    .append(pieza.getTamano())
                    .append(",")
                    .append(pieza.getEstado())
                    .append(",")
                    .append(pieza.getVidas());
            separador = ",";
        }
        return resultado.toString();
    }

    public void setPosicionPiezaYEstado(int i, int x, int y, int tamano, int vidas, int estado) {
        Pieza pieza = piezas.getIndex(i);
        pieza.setX(x);
        pieza.setY(y);
        pieza.setTamano(tamano);
        pieza.setVidas(vidas);
        pieza.setEstado(estado);
    }

    public void notificar() {
        observado.firePropertyChange("JUEGO", true, false);
    }

    public Color getColor() {
        return color;
    }

    public int contarPiezasVivas() {
        int resultado = 0;
        for (Pieza pieza : piezas) {
            if (pieza.getEstado() == 1) {
                resultado++;
            }
        }
        return resultado;
    }

    public int getPiezasVivas() {
        return contarPiezasVivas();
    }

    /*
    * public String getPiezasVivasParaRed() {
        return String.valueOf(contarPiezasVivas());
    }*/

}
