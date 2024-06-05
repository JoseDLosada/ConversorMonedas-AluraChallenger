package com.alura.conversor.modelo;

import java.util.Map;

public class ConversorMoneda {


    public double convertirMoneda(Moneda moneda, String monedaDestino, double valor) {
        Map<String, Double> conversionRates = moneda.conversion_rates();
        if (conversionRates.containsKey(monedaDestino)) {
            double tasaConversion = conversionRates.get(monedaDestino);
            return valor * tasaConversion;
        } else {
            throw new RuntimeException("Moneda de destino no encontrada en las tasas de conversión");
        }
    }
}
