/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebas;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jmoreno
 */
public class Ascensor extends Thread {

    Controlador c;
    int id; // id del ascensor para diferenciarlos en caso de que haya mas que uno
    int pisoActual;
    int capacidad;
    int pasajeros;
//    boolean[] destinosSolicitados;

    public Ascensor(Controlador c, int id) {
        this.c = c;
        this.id = id;
        this.pisoActual = 10;
//        c.hayAscensor[pisoActual].release();
        this.capacidad = 5;
        this.pasajeros = 0;
//        this.destinosSolicitados = new boolean[21];
    }

    

    @Override
    public void run() {

        System.out.println("El Ascensor "+ id +" está en el piso " + pisoActual);
        
        while (true) {
            try {

                c.atenderPedido(this);

            } catch (InterruptedException ex) {
                Logger.getLogger(Ascensor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
