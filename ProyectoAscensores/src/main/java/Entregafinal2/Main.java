/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entregafinal2;

/**
 *
 * @author jmoreno
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        Tiempo t = Tiempo.getInstance();
        Controlador c = new Controlador(10);
        Ascensor a = new Ascensor(c, 0, 5);
        Persona p;
        String[] personas = ManejadorArchivosGenerico.leerArchivo(".\\src\\main\\java\\Entregafinal\\Personas.txt");
        

        for (String linea : personas) {
            
            String[] persona = linea.split(",");
            
            p = new Persona(Integer.parseInt(persona[0].trim()),
                    c,
                    Integer.parseInt(persona[1].trim()),
                    Integer.parseInt(persona[2].trim()),
                    Integer.parseInt(persona[3].trim()));
            p.start();
        }
        t.start();

        Thread.sleep(500);
        a.start();
    }
}
