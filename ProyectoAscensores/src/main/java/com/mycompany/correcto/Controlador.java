/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.correcto;

import java.util.concurrent.Semaphore;

/**
 *
 * @author jmoreno
 */
public class Controlador {

    Semaphore mutexPedidos; // Garantizar que un solo proceso acceda a los pedidos
    Semaphore hayPedidos; // Inicia en 0, indica la cantidad de pedidos que hay
    Semaphore hayDestino;  //

    Semaphore[] hayAscensor;
    Semaphore[] puedeBajar;

    boolean[] pisosOrigenSolicitados;
    boolean[] destinosSolicitados;

//    Sentido[] sentidosSolicitados;
    public Controlador() {
        this.mutexPedidos = new Semaphore(1);
        this.hayPedidos = new Semaphore(0);
        this.hayDestino = new Semaphore(0);

        this.hayAscensor = new Semaphore[21];
        this.puedeBajar = new Semaphore[21];

        this.pisosOrigenSolicitados = new boolean[21];
        this.destinosSolicitados = new boolean[21];
//        this.sentidosSolicitados = new Sentido[21];

        for (int i = 0; i < hayAscensor.length; i++) {
            hayAscensor[i] = new Semaphore(0);
            puedeBajar[i] = new Semaphore(0);
        }

        //this.capacidades = new int[4];
    }

    public void atenderPedido(Ascensor a) throws InterruptedException {

        // COM PEDIDO MAS CERCANO
        int pedidoMasCercano = pedidoMasCercano(a);
        // FIN PEDIDO MAS CERCANO

        // COM MOVER
        moverAscensor(a, pedidoMasCercano);
        // FIN MOVER

        hayAscensor[a.pisoActual].release();

        // COM DESTINO MAS CERCANO
        int destinoMasCercano = destinoMasCercano(a);
        // FIN DESTINO MAS CERCANO

        // COM MOVER
        moverAscensor(a, destinoMasCercano);
        // FIN MOVER 

        destinosSolicitados[destinoMasCercano] = false;
        puedeBajar[destinoMasCercano].release();

    }

    public int pedidoMasCercano(Ascensor a) throws InterruptedException {
        hayPedidos.acquire();
        int pedidoMasCercano = 0;
        int distancia = 20;

        mutexPedidos.acquire();

        for (int i = 0; i <= 20; i++) {
            if (pisosOrigenSolicitados[i] == true
                    && (Math.abs(a.pisoActual - i) < distancia)) {
                distancia = Math.abs(a.pisoActual - i);
                pedidoMasCercano = i;
            }
        }

        pisosOrigenSolicitados[pedidoMasCercano] = false;
        System.out.println("El ascensor " + a.id + " va por el pedido del piso " + pedidoMasCercano);
        mutexPedidos.release();
        return pedidoMasCercano;
    }

    public void moverAscensor(Ascensor a, int piso) throws InterruptedException {
        while (a.pisoActual != piso) {

//            if (hayAscensor[a.pisoActual].availablePermits() == 1) {
//                hayAscensor[a.pisoActual].acquire();
//            }
            a.sleep(1000);

            if (a.pisoActual < piso) {
                a.pisoActual++;
            } else {
                a.pisoActual--;
            }

//            hayAscensor[a.pisoActual].release();
//            puedeBajar[a.pisoActual].release();
            System.out.println("El ascensor "+a.id+" está en el piso " + a.pisoActual);
        }
    }

    public int destinoMasCercano(Ascensor a) throws InterruptedException {
        hayDestino.acquire();
        int destinoMasCercano = 0;
        int distancia = 20;

        for (int i = 0; i <= 20; i++) {
            if (destinosSolicitados[i] == true
                    && (Math.abs(a.pisoActual - i) < distancia)) {
                distancia = Math.abs(a.pisoActual - i);
                destinoMasCercano = i;
            }
        }

        return destinoMasCercano;
    }

    public void generarPedido(int personaID) throws InterruptedException {

        mutexPedidos.acquire();

        int origen = (int) (Math.random() * 20);

        pisosOrigenSolicitados[origen] = true;

        System.out.println("Persona " + personaID + " ha solicitado "
                + "ascensor en el piso " + origen);

        mutexPedidos.release();
        hayPedidos.release();

        hayAscensor[origen].acquire();

        int destino = (int) (Math.random() * 20);
        System.out.println("Persona " + personaID + " recogida en el piso " + origen
                + ". Quiere ir al piso " + destino);

        destinosSolicitados[destino] = true;

        hayDestino.release();

        puedeBajar[destino].acquire();
        System.out.println("Persona " + personaID + " baja en el piso " + destino);
    }

}
