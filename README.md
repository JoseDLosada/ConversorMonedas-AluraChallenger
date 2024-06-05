# ConversorMonedas-AluraChallenger

ConversorMonedas-AluraChallenger es una aplicación de consola en Java que permite convertir entre diferentes monedas utilizando datos de tasas de cambio en tiempo real obtenidos de la API de ExchangeRate-API. El usuario puede seleccionar entre varias monedas predefinidas y convertir un valor específico de una moneda a otra.

## Estructura del Proyecto

![image](https://github.com/JoseDLosada/ConversorMonedas-AluraChallenger/assets/156674084/183d5ba3-e83b-43ca-bba5-8732154f7e5f)


## Prerrequisitos

- JDK 17 o superior
- Gson

## Uso

A continuación, se muestra un ejemplo de uso del programa.

### Ejemplo de Conversión

1. Al iniciar el programa, se pedirá que seleccione la moneda de origen:

    ```plaintext
    Seleccione la moneda de origen:
    1. ARS
    2. BOB
    3. BRL
    4. CLP
    5. COP
    6. USD
    Ingrese el número de la opción: 6
    ```

2. Luego, se pedirá que seleccione la moneda de destino:

    ```plaintext
    Seleccione la moneda de destino:
    1. ARS
    2. BOB
    3. BRL
    4. CLP
    5. COP
    6. USD
    Ingrese el número de la opción: 1
    ```

3. Finalmente, ingrese el valor a convertir:

    ```plaintext
    Ingrese el valor a convertir: 100
    El valor de 100.00 USD en ARS es: 23000.00
    ```

## Clases Principales

### `Moneda`

Esta clase define la estructura de un objeto moneda utilizando el `record` de Java para almacenar el código de la moneda base y las tasas de conversión.

```java
package com.alura.conversor.modelo;

import java.util.Map;

public record Moneda(String base_code, Map<String, Double> conversion_rates) {
}
```



### ConsultaModena
Esta clase es responsable de realizar la solicitud HTTP a la API para obtener las tasas de conversión de una moneda base.

```java
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
```
### ConversorMoneda
Esta clase contiene la lógica para convertir un valor de una moneda a otra utilizando las tasas de conversión obtenidas.

```java
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

```
### SeleccionadorMoneda
Esta clase maneja la lógica de mostrar las opciones de moneda al usuario y recibir su selección.

```java
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

```
### Principal
Esta es la clase principal que coordina las otras clases. Solicita al usuario la moneda de origen, la moneda de destino y el valor a convertir, y luego muestra el resultado de la conversión.

```java
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


```

