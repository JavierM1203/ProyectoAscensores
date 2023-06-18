/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Ascensor. Accede a los atributos del controlador para saber la
 * información de los pedidos.
 *
 * @author jmoreno
 */
public class Ascensor extends Thread {

    /**
     * Controlador que contiene la información de los pisos desde los cuales se
     * generan pedidos.
     */
    Controlador c;

    /**
     * Piso actual del ascensor
     */
    int pisoActual;

    /**
     * Cantidad de espacios libres en la cabina
     */
    int espaciosLibres;

    /**
     * Constructor del ascensor
     *
     * @param c Controlador
     * @param pisoActual Piso actual
     * @param espaciosLibres Espacios libre en la cabina
     */
    public Ascensor(Controlador c, int pisoActual, int espaciosLibres) {
        this.c = c;
        this.pisoActual = pisoActual;
        this.espaciosLibres = espaciosLibres;
    }

    /**
     * Atiende las solicitudes para subir, evaluando los pisos de abajo hacia
     * arriba
     *
     * @throws InterruptedException
     */
    public void atenderSubida() throws InterruptedException {

        for (int i = 0; i <= c.pisos; i++) { // Evauo los pisos de abajo hacia arriba

            // Si se esta realizando un solicitud para subir desde el piso que estoy evaluando
            // o si el piso es un piso destino. Me muevo hasta él.
            if (revisarOrigen(i, "SUBE") || revisarDestino(i)) {
                moverAscensor(i); // Muevo el ascensor hacia el piso evaluado
                manejarPersonas(i); // Me encargo de hacer entrar o salir a las personas de la cabina
                sleep(1000); // Simula el tiempo que pasa la puerta abierta mientras las personas entran o salen
            }
        }
    }

    /**
     * Atiende las solicitudes para bajar, evaluando los pisos de arriba hacia
     * abajo
     *
     * @throws InterruptedException
     */
    public void atenderBajada() throws InterruptedException {

        for (int i = c.pisos; i >= 0; i--) {

            if (revisarOrigen(i, "BAJA") || revisarDestino(i)) {
                moverAscensor(i);
                manejarPersonas(i);
                sleep(1000);
            }
        }
    }

    /**
     * Evalua si se esta realizando un pedido desde cierto piso, en la direccion
     * pasado como argumento
     *
     * @param piso Piso a evaluar
     * @param direccion Direccion a evaluar del pedido
     * @return True si se esta realizando una solicitud desde este piso en la
     * direccion especificada. De lo contrario devuelve false.
     * @throws InterruptedException
     */
    public boolean revisarOrigen(int piso, String direccion) throws InterruptedException {

        boolean result = false;

        c.mutexPisos[piso].acquire(); // MUTEX PISOS ACQUIRE
        if (c.pedidosOrigen[piso] && c.sentidosSolicitados[piso].equals(direccion)) {
            result = true;
        }
        c.mutexPisos[piso].release(); // MUTEX PISOS RELEASE

        return result;
    }

    /**
     * Evalua si el piso pasado como argumento es un piso destino
     *
     * @param piso Piso a evaluar
     * @return True si el piso es un piso destino. De lo contrario devuelve
     * False
     */
    public boolean revisarDestino(int piso) {
        return c.pedidosDestino[piso];
    }

    /**
     * Mueve el ascensor hacia el piso indicado
     *
     * @param destino Piso hacia el cual se quiere mover el ascensor
     * @throws InterruptedException
     */
    public void moverAscensor(int destino) throws InterruptedException {

        while (pisoActual != destino) {

            sleep(1000); // Simula el tiempo que tarda el ascensor en moverse entre los pisos

            if (pisoActual < destino) {
                pisoActual++;
                // Mientras me muevo entre los pisos, evaluo si hay una solicitud en el piso
                // por el cual estoy pasando, ya sea para entra o para salir de la cabina
                if (revisarOrigen(pisoActual, "SUBE") || revisarDestino(pisoActual)) {
                    manejarPersonas(pisoActual);
                }

            } else {
                pisoActual--;
                // Mientras me muevo entre los pisos, 
                // evaluo si hay una solicitud en el piso por el cual estoy pasando
                if (revisarOrigen(pisoActual, "BAJA") || revisarDestino(pisoActual)) {
                    manejarPersonas(pisoActual);
                }
            }
            System.out.println("El ascensor está en el piso " + pisoActual);
        }
    }

    /**
     * Se encarga de hacer que las personas entren o salgan de la cabina en el
     * piso indicado
     *
     * @param piso Piso en el cual las personas entran/salen de la cabina
     * @throws InterruptedException
     */
    public void manejarPersonas(int piso) throws InterruptedException {

        // Mientras haya personas que quieren bajar en ese piso, hago release para que puedan
        // salir de la cabina y reduzco la cantidad de personas que quieren salir en ese piso
        while (c.personasEnDestino[piso] > 0) {
            c.puedeSalir[piso].release();
            c.personasEnDestino[piso]--;
            espaciosLibres++; // Se libera un espacio libre
        }
        c.pedidosDestino[piso] = false; // Luego de que salen todas las personas, el piso ya no
                                        // es solicitado como destino.

        c.mutexPisos[piso].acquire(); // MUTEX PISOS ACQUIRE

        // Mientras haya personas que quieran subir en ese piso, hago release para que puedan
        // subir a la cabina y reduzo la cantidad de personas que quiere entrar en ese piso
        while (c.personasEnOrigen[piso] > 0 && espaciosLibres > 0) {
            c.puedeEntrar[piso].release();
            c.personasEnOrigen[piso]--;
            espaciosLibres--; // Hay un espacio libre menos
        }

        // El bucle anterior puede terminar porque no hay mas personas que quieran entrar
        // o porque porque no hay mas espacios libres. En caso de terminar porque no hay mas
        // personas que quieren entrar, indico al controlador que ya no hay personas relizando
        // pedidos desde este piso.
        if (c.personasEnOrigen[piso] == 0) {
            c.pedidosOrigen[piso] = false;
            c.sentidosSolicitados[piso] = "N/A";
        }

        c.mutexPisos[piso].release(); // MUTEX PISOS RELEASE
    }

    @Override
    public void run() {
        try {

            System.out.println("El ascensor está en el piso " + pisoActual);

            while (true) {
                atenderSubida(); // Evaluo todas las solicitudes de subida
                atenderBajada(); // luego todas las de bajada
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(Ascensor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
