/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.correcto;

/**
 *
 * @author jmoreno
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Controlador c = new Controlador();
        Ascensor asc1 = new Ascensor(c, 1);
//        Ascensor asc2 = new Ascensor(c, 2);

        for (int i = 0; i < 5; i++) {
            Persona p = new Persona(c, i + 1);
            p.start();
        }

        asc1.start();
//        asc2.start();

    }
}
