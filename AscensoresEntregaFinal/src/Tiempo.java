/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase tiempo. Hace que el tiempo avance para que las personas
 * realicen su pedidos segun el tiempo que se les indicó
 * @author jmoreno
 */
public class Tiempo extends Thread {

    /**
     * Indica el tiempo actual
     */
    static int tiempo;
    
    /**
     * Controla que solo un proceso acceda o modifique el tiempo a la vez
     */
    static Semaphore mutexTiempo;
    
    /**
     * Instancia unica de la clase Tiempo
     */
    private static Tiempo t;
    
    /**
     * Constructor
     */
    private Tiempo() {
        tiempo = 0;
        mutexTiempo = new Semaphore(1);
    }
    
    /**
     * Asegura que solo haya una instancia de la clase tiempo.
     * @return Devuelve la instancia de la clase tiempo
     */
    public static Tiempo getInstance() {
        
        if (t == null) {
            t = new Tiempo();
        }
        
        return t;
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                sleep(1000);
                
                mutexTiempo.acquire(); // Me aseguro que el tiempo no esté siendo leido antes de modificarlo
                tiempo++;
                mutexTiempo.release();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Tiempo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
