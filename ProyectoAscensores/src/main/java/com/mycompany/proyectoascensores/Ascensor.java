/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoascensores;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmoreno
 */
public class Ascensor extends Thread {

    Controlador c;
    int pisoActual;
    boolean pisosDestinoSolicitados;
    

    public Ascensor(Controlador c) {
        this.c = c;
        this.pisoActual = 10;
    }

    public void moverAscensor(int piso) throws InterruptedException {

        while (pisoActual != piso) {
            
            sleep(1000);
            
            if (pisoActual < piso) {
                pisoActual++;
            } else {
                pisoActual--;
            }
            
            System.out.println("El Ascensor está en el piso " + pisoActual);
            
            if (c.revisarPisoActual(pisoActual)) {
                System.out.println("El ascensor ha recogido a la persona del piso " + pisoActual);
            }
        }
        System.out.println("El ascensor ha recogido a la persona del piso " + pisoActual);
    }

    @Override
    public void run() {
        System.out.println("El Ascensor está en el piso " + pisoActual);
        int pisoOrigen;

        while (true) {
            try {

                pisoOrigen = c.atenderPedido();
                if (pisoOrigen != -1) {
                    System.out.println("El ascensor va por el pedido del piso " + pisoOrigen);
                    moverAscensor(pisoOrigen);
                    

                }

            } catch (InterruptedException ex) {
                Logger.getLogger(Ascensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
