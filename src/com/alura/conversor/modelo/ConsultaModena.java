package com.alura.conversor.modelo;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaModena {

    public Moneda buscarMoneda(String moneda){
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/fde19f84014de23bfd6ed179/latest/"+moneda);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();
        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return new Gson().fromJson (response.body(), Moneda.class);
        }catch (Exception e){
            throw new RuntimeException("Moneda no encontrada");
        }

    }
}
