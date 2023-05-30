/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebas;

import java.util.concurrent.Semaphore;

/**
 *
 * @author jmoreno
 */
public class Controlador {

    /**
     * Garantiza que un solo proceso acceda a los pedidos
     */
    Semaphore mutexPedidos;
    /**
     * Inicia en 0, indica la cantidad de pedidos que hay. El ascensor no
     * empieza a moverse sino hasta que se haga un release.
     */
    Semaphore hayPedidos;
    /**
     * Inicia en 0. Se le hace un release cuando la persona marca un piso para
     * indicarle al ascensor que se mueva.
     */
    Semaphore hayDestino;
    /**
     * Array de semaforos por cada piso. Inician en 0. Cambian a 1 cuando el
     * ascensor pasa por ese piso para indicar que hay ascensor disponible a
     * quien este solicitando ascensor desde ese piso.
     */
    Semaphore[] hayAscensor;
    /**
     * Array de semaforos por piso. Inician en 0. Cambia a 1 cuando el ascensor
     * llega al destino para indicarle a la persona que puede bajar
     */
    Semaphore[] puedeBajar;
    /**
     * Array de booleanos por piso. Indica que en el piso hay una persona
     * esperando por ascensor cuando esta true
     */
    boolean[] pisosOrigenSolicitados;
    /**
     * ESTA PROPIEDAD DEBERIA ESTAR EN EL ASCENSOR. Hay que rediseñar el codigo
     * del metodo generarPedido y AtenderPedido para poder pasar esta propiedad
     * al ascensor. 
     * Array de booleanos por piso. Indica que en piso es un destino
     * solicitado cuando esta en true
     */
    boolean[] destinosSolicitados;

    /**
     * No esta implementado. Array que almacena los sentidos hacia los cuales se
     * desea ir desde los pisos en donde se solicita el ascensor.
     */
    Sentido[] sentidosSolicitados;
    
    public Controlador() {
        this.mutexPedidos = new Semaphore(1);
        this.hayPedidos = new Semaphore(0);
        this.hayDestino = new Semaphore(0);

        this.hayAscensor = new Semaphore[21];
        this.puedeBajar = new Semaphore[21];

        this.pisosOrigenSolicitados = new boolean[21];
        this.destinosSolicitados = new boolean[21];
        this.sentidosSolicitados = new Sentido[21];

        for (int i = 0; i < hayAscensor.length; i++) {
            hayAscensor[i] = new Semaphore(0);
            puedeBajar[i] = new Semaphore(0);
        }

        //this.capacidades = new int[4];
    }

    /**
     * Se encarga de antender pedidos. Se queda en espera hasta que se haga un
     * hayPedidos.release() debido que hay un hayPedidos.acquire() en el metodo
     * pedidoMasCercano()
     * @param a Ascensor
     * @throws InterruptedException
     */
    public void atenderPedido(Ascensor a) throws InterruptedException {

        // COM PEDIDO MAS CERCANO
        int pedidoMasCercano = pedidoMasCercano(a); // Busco pedido mas cercano
        // FIN PEDIDO MAS CERCANO

        // COM MOVER
        moverAscensor(a, pedidoMasCercano); // Me muevo al piso con pedido mas cercano
        // FIN MOVER

        hayAscensor[a.pisoActual].release(); // Indico que en el piso actual hay ascensor

        // COM DESTINO MAS CERCANO
        int destinoMasCercano = destinoMasCercano(a); // Busco el destino mas cercano
        // FIN DESTINO MAS CERCANO

        // COM MOVER
        moverAscensor(a, destinoMasCercano); // Me muevo hacia el destino más cercano
        // FIN MOVER 

        destinosSolicitados[destinoMasCercano] = false;

        puedeBajar[destinoMasCercano].release(); // Le indico a la persona que ha llegado al piso destino
    }

    /**
     * Busca el piso mas cercano desde el cual se hace un pedido
     * @param a Ascensor
     * @return Piso mas cercano desde el cual se hace un pedido
     * @throws InterruptedException
     */
    public int pedidoMasCercano(Ascensor a) throws InterruptedException {
        hayPedidos.acquire(); // Se queda en espera hasta que se haga un relase en generarPedido()
        int pedidoMasCercano = 0;
        int distancia = 20;

        mutexPedidos.acquire(); // MUTEX para acceder a los pisos solicitados

        // Evaluo cual es el piso mas cercano desde el cual se hace un pedido
        for (int i = 0; i <= 20; i++) {
            if (pisosOrigenSolicitados[i] == true
                    && (Math.abs(a.pisoActual - i) < distancia)) {
                distancia = Math.abs(a.pisoActual - i);
                pedidoMasCercano = i;
            }
        }

        pisosOrigenSolicitados[pedidoMasCercano] = false;
        sentidosSolicitados[pedidoMasCercano] = null;

        System.out.println("El ascensor " + a.id + " va por el pedido del piso " + pedidoMasCercano);

        mutexPedidos.release(); // Termina MUTEX

        return pedidoMasCercano;
    }

    /**
     * Se encarga de mover el ascensor hasta el piso indicado
     * @param a Ascensor
     * @param piso piso destino
     * @throws InterruptedException
     */
    public void moverAscensor(Ascensor a, int piso) throws InterruptedException {
        Sentido s;
        
        if (a.pisoActual < piso) {
            s = Sentido.SUBE;
        } else {
            s = Sentido.BAJA;
        }
        
        while (a.pisoActual != piso) {

            mutexPedidos.acquire();
            if (pisosOrigenSolicitados[a.pisoActual] == true 
                    && sentidosSolicitados[a.pisoActual].equals(s)) {
                pisosOrigenSolicitados[a.pisoActual] = false;
                sentidosSolicitados[a.pisoActual] = null;
                hayAscensor[a.pisoActual].release();
            }
            
            mutexPedidos.release();

            a.sleep(1000); // Tiempo que tarda el ascensor para moverse al siguiente piso.

            if (a.pisoActual < piso) {
                a.pisoActual++;
            } else {
                a.pisoActual--;
            }

//            hayAscensor[a.pisoActual].release();
//            puedeBajar[a.pisoActual].release();
            System.out.println("El ascensor " + a.id + " está en el piso " + a.pisoActual);
        }
    }

    /**
     * Busca el destino solicitado más cercano
     *
     * @param a Ascensor
     * @return Destino más cercano
     * @throws InterruptedException
     */
    public int destinoMasCercano(Ascensor a) throws InterruptedException {
        hayDestino.acquire(); // Queda en espera hasta que se haga un release desde generarPedido()
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

    /**
     * Genera un pedido desde un piso aleatorio hasta un piso aletorio
     *
     * @param p Persona que genera el pedido
     * @throws InterruptedException
     */
    public void generarPedido(Persona p) throws InterruptedException {

        Sentido s;
        
        if (p.pisoOrigen < p.pisoDestino) {
            s = Sentido.SUBE;
        } else {
            s = Sentido.BAJA;
        }
            
        mutexPedidos.acquire(); // MUTEX para acceder a los pisos solicitados

//        int origen = (int) (Math.random() * 20.9); // Genero numero aleatorio entre 0 y 20
//        pisosOrigenSolicitados[origen] = true; // Indico que hay pedido desde ese piso

        pisosOrigenSolicitados[p.pisoOrigen] = true; // Indico que hay pedido desde ese piso
        sentidosSolicitados[p.pisoOrigen] = s;
        
        System.out.println("Persona " + p.id + " ha solicitado "
                + "ascensor en el piso " + p.pisoOrigen + " para " + s.toString());

        mutexPedidos.release(); // Termina MUTEX
        hayPedidos.release(); // Indico que hay pedidos

        hayAscensor[p.pisoOrigen].acquire(); // Espero a que haya ascensor en ese piso

//        int destino = (int) (Math.random() * 20.9); // Genero numero aleatorio entre 0 y 20
//        destinosSolicitados[destino] = true; // Indico que quiero ir hasta ese piso

        destinosSolicitados[p.pisoDestino] = true; // Indico que quiero ir hasta ese piso
        System.out.println("Persona " + p.id + " recogida en el piso " + p.pisoOrigen
                + ". Quiere ir al piso " + p.pisoDestino);

        hayDestino.release(); // Indico que hay destino para que el ascensor se mueva
        puedeBajar[p.pisoDestino].acquire(); // Espero a que el ascensor llegue a ese piso

        System.out.println("Persona " + p.id + " baja en el piso " + p.pisoDestino);
    }

}
