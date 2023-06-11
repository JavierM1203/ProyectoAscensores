/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entregafinal;

import java.util.concurrent.Semaphore;

/**
 *
 * @author jmoreno
 */
public class Controlador {

    int pisos = 10;

    Semaphore[] mutexPisos = new Semaphore[pisos + 1];
    Semaphore[] hayAscensor = new Semaphore[pisos + 1];

    boolean[] pedidos = new boolean[pisos + 1];
    int[] personasEnPiso = new int[pisos + 1];
    String[] sentidosSolicitados = new String[pisos + 1];
    
    boolean[] destinos = new boolean[pisos + 1];
    int[] personasDestino = new int[pisos + 1];
            
    public Controlador() {
        for (int i = 0; i <= pisos; i++) {
            mutexPisos[i] = new Semaphore(1);
            hayAscensor[i] = new Semaphore(0);
            sentidosSolicitados[i] = "N/A";
        }
    }
}
