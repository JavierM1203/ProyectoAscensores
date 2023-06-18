/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entregafinal2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Persona. Extiende la clase Thread.
 * Genera los pedidos, accienedo y modificando los atributos del controlador
 * @author jmoreno
 */
public class Persona extends Thread {

    /**
     * ID para difereciar a las personas al imprimir la informacion
     * por consola
     */
    int id;
    
    /**
     * Controlador al cual se accede para realizar los pedidos
     */
    Controlador c;
    
    /**
     * Piso desde el cual se realiza el pedido
     */
    int origen;
    
    /**
     * Piso hacia el cual se quiere ir.
     */
    int destino;
    
    /**
     * Unidad de tiempo a partir de la cual se debe generar el pedido
     */
    int tiempoComienzo;

    /**
     * Constructor de persona
     * @param id ID de la persona
     * @param c Controlador
     * @param origen Piso origen
     * @param destino Piso destino
     * @param tiempo Unidad de tiempo a partir de la cual se genera el pedido
     */
    public Persona(int id, Controlador c, int origen, int destino, int tiempo) {
        this.id = id;
        this.c = c;
        this.origen = origen;
        this.destino = destino;
        this.tiempoComienzo = tiempo;
    }

    /**
     * Accede y modifica a la información del controlador para indicar
     * que se esta realizando un pedido
     * @throws InterruptedException 
     */
    public void generarPedido() throws InterruptedException {

        System.out.println("Persona " + id + " solicita ascensor en el piso "
                + origen + " para ir al piso " + destino);

        c.mutexPisos[origen].acquire(); // MUTEX PISOS ACQUIRE

        c.pedidosOrigen[origen] = true; // Indico que se esta solictiando ascensor desde el piso origen
        c.personasEnOrigen[origen]++; // Se le suma 1 a la cantidad de personas esperando en el piso
        c.sentidosSolicitados[origen] = origen < destino ? "SUBE" : "BAJA"; // Se asigna un sentido

        c.mutexPisos[origen].release(); // MUTEX PISOS RELEASE

        c.puedeEntrar[origen].acquire(); // Espero a que el ascensor haga un release en el piso origen para poder entrar
        System.out.println("Persona " + id + " sube al ascensor");

        c.pedidosDestino[destino] = true; // Indico el destino hacia el cual quiero ir
        c.personasEnDestino[destino]++; // Se le suma 1 a la cantidad de personas que quieren ir a ese piso

        c.puedeSalir[destino].acquire(); // Espero a que el ascensor haga un release en el piso destino para poder salir
        System.out.println("Persona " + id + " baja del ascensor");
    }

    @Override
    public void run() {
        try {
            int tiempo;
            
            while (true) {
                
                Tiempo.mutexTiempo.acquire(); // Me aseguro que el tiempo no este siendo modificado antes de leerlo
                tiempo = Tiempo.tiempo;
                Tiempo.mutexTiempo.release();
                
                if (tiempoComienzo <= tiempo) { // Evaluo el tiempo para saber si debo generar le pedido o no
                    generarPedido();
                    break;
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Persona.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
