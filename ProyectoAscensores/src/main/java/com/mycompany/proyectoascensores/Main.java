/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectoascensores;

/**
 *
 * @author jmoreno
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Controlador c = new Controlador();
        Ascensor asc = new Ascensor(c);
        
        for (int i = 0; i < 5; i++) {
            Persona p = new Persona(c, i + 1);
            p.start();
        }

        asc.start();

    }
}
