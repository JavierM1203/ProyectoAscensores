/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entregafinal;

/**
 *
 * @author jmoreno
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        Controlador c = new Controlador();
        Persona p1 = new Persona(1, c, 80, 0, 10);
        Persona p2 = new Persona(2, c, 80, 5, 10);

//        Persona p3 = new Persona(3, c, 80, 0, 10);
//        Persona p4 = new Persona(4, c, 80, 0, 10);
//        Persona p5 = new Persona(5, c, 80, 0, 10);
//        Persona p6 = new Persona(6, c, 80, 0, 10);
        Ascensor a = new Ascensor(1, c);

        p1.start();
//        p3.start();
//        p4.start();
//        p5.start();
//        p6.start();

        a.start();
        Thread.sleep(5000);

        p2.start();

    }
}
