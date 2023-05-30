/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pruebas;

import java.util.Random;

/**
 *
 * @author jmoreno
 */
public enum Sentido {
    SUBE,
    BAJA;
    
    static Random random = new Random();
    static Sentido[] sentidos = values();
    
    public static Sentido randomSentido() {
        return sentidos[random.nextInt(values().length)];
    }

    @Override
    public String toString() {
        switch (this.name()) {
            case "SUBE": 
                return "subir";
            case "BAJA":
                return "bajar";
            default:
                return "null";
        }
    }
}
