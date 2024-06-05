package com.alura.conversor.principal;

import com.alura.conversor.modelo.ConsultaModena;
import com.alura.conversor.modelo.ConversorMoneda;
import com.alura.conversor.modelo.Moneda;
import com.alura.conversor.menu.SeleccionadorMoneda;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        ConsultaModena consulta = new ConsultaModena();
        ConversorMoneda conversor = new ConversorMoneda();
        SeleccionadorMoneda seleccionador = new SeleccionadorMoneda();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Seleccione la moneda de origen:");
            String monedaOrigen = seleccionador.seleccionarMoneda(scanner);

            System.out.println("Seleccione la moneda de destino:");
            String monedaDestino = seleccionador.seleccionarMoneda(scanner);

            System.out.print("Ingrese el valor a convertir: ");
            double valor = scanner.nextDouble();

            Moneda moneda = consulta.buscarMoneda(monedaOrigen);
            double resultado = conversor.convertirMoneda(moneda, monedaDestino, valor);

            System.out.printf("El valor de %.2f %s en %s es: %.2f\n", valor, monedaOrigen, monedaDestino, resultado);
        } catch (RuntimeException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

