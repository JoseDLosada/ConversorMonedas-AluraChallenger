package com.alura.conversor.principal;

import com.alura.conversor.modelo.ConsultaModena;
import com.alura.conversor.modelo.Moneda;

public class Principal {

    public static void main(String[] args) {

        ConsultaModena consulta = new ConsultaModena();

        Moneda moneda = consulta.buscarMoneda("USD");
        System.out.println(moneda);

    }
}
