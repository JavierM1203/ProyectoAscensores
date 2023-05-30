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
public class Persona extends Thread {
    Controlador c;
    int id;
    int pisoOrigen;
    int pisoDestino;

    public Persona (Controlador c, int id, int pisoOrigen, int pisoDestino) {
        this.c = c;
        this.id = id;
        this.pisoOrigen = pisoOrigen;
        this.pisoDestino = pisoDestino;
    }

    @Override
    public void run() {
        try {
            c.generarPedido(this);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
