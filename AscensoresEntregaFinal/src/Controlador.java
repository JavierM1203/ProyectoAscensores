/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.concurrent.Semaphore;

/**
 * Clase Controlador. Contiene la cantidad de pisos del edificio,
 * los pisos desde los cuales se realizan pedidos, los sentidos de
 * los pedidos realizados, etc.
 * Funciona como intermediario entre las personas y el ascensor.
 * @author jmoreno
 */
public class Controlador {

    /**
     * Cantidad de pisos del edificio
     */
    int pisos;

    /**
     * Array de semaforos por piso. 
     * Controla que un solo proceso acceda y modifique la informacion
     * de cada piso. Se inicializan todos en 1.
     */
    Semaphore[] mutexPisos;

    /**
     * Array de semaforos por piso. 
     * Cuando esta en 1, le indica a la persona que esperando afuera
     * que puede entrar al ascensor. Se inicializan todos en 0.
     */
    Semaphore[] puedeEntrar;

    /**
     * Array de semaforos por piso. 
     * Cuando esta en 1, le indica a la persona dentro del ascensor
     * que puede salir de la cabina. Se inicializan todos en 0.
     */
    Semaphore[] puedeSalir;

    /**
     * Array de booleanos por piso.
     * Cuando el elemento es true, indica que hay personas solicitando
     * el ascensor en ese piso.
     */
    boolean[] pedidosOrigen;

    /**
     * Array de booleanos por piso.
     * Cuando el elemento es true, indica que hay personas que quieren
     * ir hacia ese piso.
     */
    boolean[] pedidosDestino;

    /**
     * Array de enteros por piso.
     * Cada elemento indica la cantidad de personas esperando 
     * en ese piso.
     */
    int[] personasEnOrigen;

    /**
     * Array de enteros por piso.
     * Cada elemento indica la cantidad de personas que quieren
     * ir hacia ese piso
     */
    int[] personasEnDestino;

    /**
     * Array de Strings por piso.
     * Cada elemento indica el sentido solicitado en el piso desde
     * el cual se realiza el pedido.
     */
    String[] sentidosSolicitados;
    
    /**
     * Contructor del controlador
     * @param pisos cantidad de pisos del edificio 
     */
    public Controlador(int pisos) {

        this.pisos = pisos;

        this.mutexPisos = new Semaphore[pisos + 1]; // Se le suma 1 a la cantidad de pisos 
                                                    // para incluir la planta baja

        this.puedeEntrar = new Semaphore[pisos + 1];

        this.puedeSalir = new Semaphore[pisos + 1];

        this.pedidosOrigen = new boolean[pisos + 1];

        this.pedidosDestino = new boolean[pisos + 1];

        this.personasEnOrigen = new int[pisos + 1];

        this.personasEnDestino = new int[pisos + 1];

        this.sentidosSolicitados = new String[pisos + 1];
             
        for (int i = 0; i <= pisos; i++) {
            this.mutexPisos[i] = new Semaphore(1);
            this.puedeEntrar[i] = new Semaphore(0);
            this.puedeSalir[i] = new Semaphore(0);
            this.sentidosSolicitados[i] = "N/A";
        }
    }
    
}
