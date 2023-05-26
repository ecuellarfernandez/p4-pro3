package org.p4.juego.objetos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.p4.juego.red.Protocolo;
import javax.swing.*;
import java.awt.*;
import static org.p4.juego.red.Protocolo.FINALIZAR;
import static org.p4.juego.red.Protocolo.PIEZAS;

public class Batalla {
    private static Logger logger = LogManager.getRootLogger();
    private static Batalla instancia;
    private Jugador local;
    private Jugador remoto;
    private Protocolo protocolo;
    public static Batalla getOrCreate(){
        if(instancia==null){
            instancia = new Batalla();
        }
        return instancia;
    }
    private Batalla() {
        local = new Jugador(Color.blue);
        remoto = new Jugador(Color.red);
        protocolo = null;
    }

    public void comprobarGanador() {
        logger.info("============================================ Compruebo ganador ============================================");
        int piezasVivasRemoto = remoto.getPiezasVivas();
        int piezasVivasLocal = local.getPiezasVivas();

        logger.info("LOCAL: " + piezasVivasLocal);
        logger.info("REMOTAS: " + piezasVivasRemoto);

        if (piezasVivasRemoto == 0) {
            logger.info("PERDISTE");
            JOptionPane.showMessageDialog(null, "LOCAL: PERDISTE");
            protocolo.enviarMensaje(FINALIZAR, "Ganaste");
        } else if (piezasVivasLocal == 0) {
            protocolo.enviarMensaje(FINALIZAR, "Perdiste");
            JOptionPane.showMessageDialog(null, "LOCAL: GANASTE");
            logger.info("GANASTE");
        }
    }

    public void enviarPiezasInicial(){
        protocolo.enviarMensaje(PIEZAS, local.getPiezasParaRed());
    }
    public Protocolo getProtocolo() {
        return protocolo;
    }
    public Jugador getLocal() {
        return local;
    }
    public void setLocal(Jugador local) {
        this.local = local;
    }
    public Jugador getRemoto() {
        return remoto;
    }
    public void setProtocolo(Protocolo protocolo) {
        this.protocolo = protocolo;
    }
}
