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
public class Persona extends Thread {

    int id;
    Controlador c;
    int peso;
    int origen;
    int destino;

    public Persona(int id, Controlador c, int peso, int origen, int destino) {
        this.id = id;
        this.c = c;
        this.peso = peso;
        this.origen = origen;
        this.destino = destino;
    }

    public void generarPedido() throws InterruptedException {

        System.out.println("Persona " + id + " solicita ascensor en el piso "
                + origen + " para ir al piso " + destino);

        c.mutexPisos[origen].acquire();

        c.pedidos[origen] = true;
        c.personasEnPiso[origen]++;

        if (origen < destino) {
            c.sentidosSolicitados[origen] = "SUBE";
        } else {
            c.sentidosSolicitados[origen] = "BAJA";
        }

        c.mutexPisos[origen].release();

        c.hayAscensor[origen].acquire();
        
        System.out.println("Persona " + id + " sube al ascensor");
        c.destinos[destino] = true;
        
        c.personasDestino[destino]++;
        c.hayAscensor[destino].acquire();
        System.out.println("Persona " + id + " baja del ascensor");
    }

    @Override
    public void run() {
        try {
            generarPedido();
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
