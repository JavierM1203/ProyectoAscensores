/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entregafinal;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmoreno
 */
public class Ascensor extends Thread {

    int id;
    Controlador c;
    int pisoActual = 0;
    int espaciosLibres = 5;
    int pesoLibre;

    public Ascensor(int id, Controlador c) {
        this.id = id;
        this.c = c;
    }

    public void atenderSubida() throws InterruptedException {

        for (int i = 0; i <= c.pisos; i++) {

            if (revisarPisoSubida(i) || revisarDestino(i)) {
                moverAscensor(i);
                manejarPersonas(i);
                sleep(1000);
            }
        }
    }

    public void atenderBajada() throws InterruptedException {
        
        for (int i = c.pisos; i >= 0; i--) {
            
            if (revisarPisoBajada(i) || revisarDestino(i)) {
                moverAscensor(i);
                manejarPersonas(i);
                sleep(1000);
            }
        }
    }

    public boolean revisarPisoSubida(int piso) throws InterruptedException {
        boolean result = false;
        c.mutexPisos[piso].acquire();
        if (c.pedidos[piso] && c.sentidosSolicitados[piso].equals("SUBE")) {
            result = true;
        }
        c.mutexPisos[piso].release();
        return result;
    }
    
    public boolean revisarPisoBajada(int piso) throws InterruptedException {
        boolean result = false;
        c.mutexPisos[piso].acquire();
        if (c.pedidos[piso] && c.sentidosSolicitados[piso].equals("BAJA")) {
            result = true;
        }
        c.mutexPisos[piso].release();
        return result;
    }

    public boolean revisarDestino(int piso) {
        if (c.destinos[piso]) {
            return true;
        }
        return false;
    }

    public void moverAscensor(int destino) throws InterruptedException {
        while (pisoActual != destino) {
            sleep(1000);
            
            if (pisoActual < destino) {
                pisoActual++;
                if (revisarPisoSubida(pisoActual)) {
                    manejarPersonas(pisoActual);
                }
                
            } else {
                pisoActual--;
                if (revisarPisoBajada(pisoActual)) {
                    manejarPersonas(pisoActual);
                }
            }
            System.out.println("El ascensor está en el piso " + pisoActual);
        }
    }

    public void manejarPersonas(int piso) throws InterruptedException {

        c.mutexPisos[piso].acquire();

        while (c.personasEnPiso[piso] > 0 && espaciosLibres > 0) {
            c.hayAscensor[piso].release();
            c.personasEnPiso[piso]--;
            espaciosLibres--;
        }

        if (c.personasEnPiso[piso] == 0) {
            c.pedidos[piso] = false;
            c.sentidosSolicitados[piso] = "N/A";
        }

        while (c.personasDestino[piso] > 0) {
            c.hayAscensor[piso].release();
            c.personasDestino[piso]--;
            espaciosLibres++;
        }
        c.destinos[piso] = false;

        c.mutexPisos[piso].release();

    }

    @Override
    public void run() {
        try {

            System.out.println("El ascensor está en el piso " + pisoActual);

            while (true) {
                atenderSubida();
                atenderBajada();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Ascensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
