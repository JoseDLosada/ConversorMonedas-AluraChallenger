package com.alura.conversor.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SeleccionadorMoneda {

    private static final Map<Integer, String> OPCIONES_MONEDAS = new HashMap<>();

    static {
        OPCIONES_MONEDAS.put(1, "ARS");
        OPCIONES_MONEDAS.put(2, "BOB");
        OPCIONES_MONEDAS.put(3, "BRL");
        OPCIONES_MONEDAS.put(4, "CLP");
        OPCIONES_MONEDAS.put(5, "COP");
        OPCIONES_MONEDAS.put(6, "USD");
    }

    public String seleccionarMoneda(Scanner scanner) {
        OPCIONES_MONEDAS.forEach((key, value) -> System.out.println(key + ". " + value));
        int opcion;
        do {
            System.out.print("Ingrese el número de la opción: ");
            opcion = scanner.nextInt();
            if (!OPCIONES_MONEDAS.containsKey(opcion)) {
                System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (!OPCIONES_MONEDAS.containsKey(opcion));
        return OPCIONES_MONEDAS.get(opcion);
    }

}
