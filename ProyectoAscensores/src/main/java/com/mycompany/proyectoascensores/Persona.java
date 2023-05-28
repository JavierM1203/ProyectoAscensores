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
public class Persona extends Thread {
    Controlador c;
    int id;
    int pisoDestino;

    public Persona(Controlador c, int id) {
        this.c = c;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            c.generarPedido(id);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
}
