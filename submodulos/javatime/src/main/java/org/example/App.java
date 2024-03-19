package org.example;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        System.out.println("Fecha actual: " + fechaActual);

        // Obtener la hora actual
        LocalTime horaActual = LocalTime.now();
        System.out.println("Hora actual: " + horaActual);

        // Obtener la fecha y hora actual con zona horaria
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        ZoneId zonaHoraria = ZoneId.of("Europe/Madrid");
        ZonedDateTime fechaHoraActualZonedDateTime = ZonedDateTime.of(fechaHoraActual, zonaHoraria);
        System.out.println("Fecha y hora actual con zona horaria: " + fechaHoraActualZonedDateTime);

        // Formatear la fecha actual en formato dd/MM/yyyy
        String fechaFormateada = fechaActual.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println("Fecha formateada: " + fechaFormateada);

        // Formatear la hora actual en formato HH:mm:ss
        String horaFormateada = horaActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println("Hora formateada: " + horaFormateada);

        // Formatear la fecha y hora actual en formato dd/MM/yyyy HH:mm:ss
        String fechaHoraFormateada = fechaHoraActualZonedDateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        System.out.println("Fecha y hora formateada: " + fechaHoraFormateada);

        // Calcular la diferencia en días entre dos fechas
        LocalDate fecha1 = LocalDate.of(2023, 12, 01);
        LocalDate fecha2 = LocalDate.of(2024, 03, 19);


        // Sumar 10 días a la fecha actual
        LocalDate fechaMas10Dias = fechaActual.plusDays(10);
        System.out.println("Fecha actual + 10 días: " + fechaMas10Dias);

        // Restar 3 meses a la fecha actual
        LocalDate fechaMenos3Meses = fechaActual.minusMonths(3);
        System.out.println("Fecha actual - 3 meses: " + fechaMenos3Meses);

        // Sumar 1 año a la fecha actual
        LocalDate fechaMas1Anio = fechaActual.plusYears(1);
        System.out.println("Fecha actual + 1 año: " + fechaMas1Anio);

        // Convertir una fecha a una cadena de texto
        String fechaCadena = fechaActual.toString();
        System.out.println("Fecha como cadena: " + fechaCadena);

        // Convertir una cadena de texto a una fecha
        LocalDate fechaDesdeCadena = LocalDate.parse("2024-03-19");
        System.out.println("Fecha desde cadena: " + fechaDesdeCadena);

        // Convertir una hora a una cadena de texto
        String horaCadena = horaActual.toString();
        System.out.println("Hora como cadena: " + horaCadena);

        // Convertir una cadena de texto a una hora
        LocalTime horaDesdeCadena = LocalTime.parse("12:00");
        System.out.println("Hora desde cadena: " + horaDesdeCadena);


    }
}
