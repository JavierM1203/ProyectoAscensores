/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoascensores;

import java.util.concurrent.Semaphore;

/**
 *
 * @author jmoreno
 */
public class Controlador {

    Semaphore mutexPedidos;
    boolean[] pisosOrigenSolicitados;

    public Controlador() {
        this.mutexPedidos = new Semaphore(1);
        this.pisosOrigenSolicitados = new boolean[21];
    }

    public int atenderPedido() throws InterruptedException {
        int pisoOrigen = -1;

        mutexPedidos.acquire();
        for (int i = 0; i <= 20; i++) {
            if (pisosOrigenSolicitados[i] == true) {
                pisosOrigenSolicitados[i] = false;
                pisoOrigen = i;
                break;
            }
        }
        mutexPedidos.release();

        return pisoOrigen;
    }

    public void generarPedido(int id) throws InterruptedException {
        //int origen;

        mutexPedidos.acquire();
        
        int origen = (int) (Math.random() * 20);
        pisosOrigenSolicitados[origen] = true;
        System.out.println("Persona " + id + " ha solicitado ascensor en el piso " + origen);

        mutexPedidos.release();

        //return origen;
    }

    public int pisoDestino() {
        return (int) (Math.random() * 20);
    }

    public boolean revisarPisoActual(int piso) throws InterruptedException {
        boolean hayPedido = false;
        mutexPedidos.acquire();
        if (pisosOrigenSolicitados[piso]) {
            hayPedido = pisosOrigenSolicitados[piso];
            pisosOrigenSolicitados[piso] = false;
        }
        mutexPedidos.release();
        return hayPedido;
    }

}
