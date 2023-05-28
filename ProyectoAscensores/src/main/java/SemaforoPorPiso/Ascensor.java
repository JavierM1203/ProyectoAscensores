/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SemaforoPorPiso;

import java.util.concurrent.Semaphore;

/**
 *
 * @author jmoreno
 */
public class Ascensor {

    private Semaphore[] pisos = new Semaphore[20];
    private int pisoActual;

    public Ascensor() {
        this.pisoActual = 0;
        for (int i = 0; i < 20; i++) {
            this.pisos[i] = new Semaphore(1, true);
        }
    }
    
    public void atenderPedido() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            if(this.pisos[i].availablePermits() == 0) {
                while (this.pisoActual != i) {
                    
                }
            }
        }
    }

}
