/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.pruebas;

/**
 *
 * @author jmoreno
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Controlador c = new Controlador();
        Ascensor asc1 = new Ascensor(c, 1);

        Persona p1 = new Persona(c, 1, 4, 9);
        Persona p2 = new Persona(c, 2, 7, 13);
        Persona p3 = new Persona(c,3, 8, 0);
        Persona p4 = new Persona(c, 4, 11, 5);
        
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        
        Thread.sleep(1000); // Espero 1 seg antes de correr el ascensor para
                                 // garantizar que se ingresen todos los pedidos
        
        asc1.start();

    }
}
