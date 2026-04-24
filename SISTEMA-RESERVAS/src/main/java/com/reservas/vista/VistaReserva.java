package com.reservas.vista;

import com.reservas.modelo.Reserva;

import java.sql.Date;
import java.util.List;

/**
 * Vista para los menús e impresión de datos de Reservas.
 */
public class VistaReserva {

    public int mostrarMenu() {
        Utilidades.cabecera("GESTIÓN DE RESERVAS");
        System.out.println("  1. Insertar reserva");
        System.out.println("  2. Modificar reserva");
        System.out.println("  3. Eliminar reserva");
        System.out.println("  4. Listar todas las reservas");
        System.out.println("  5. Listar reservas detalladas (JOIN)");
        System.out.println("  6. Buscar reserva por ID");
        System.out.println("  7. Buscar reservas por usuario");
        System.out.println("  8. Buscar reservas por fecha");
        System.out.println("  9. Buscar reservas por estado");
        System.out.println(" 10. Contar reservas");
        System.out.println("  0. Volver");
        return Utilidades.leerInt("\nElige una opción: ");
    }

    public Reserva pedirDatos() {
        int idU  = Utilidades.leerInt("ID del usuario: ");
        int idR  = Utilidades.leerInt("ID del recurso: ");
        int idH  = Utilidades.leerInt("ID del horario: ");
        Date f   = Utilidades.leerFecha("Fecha de la reserva");
        String e = Utilidades.leerString("Estado (PENDIENTE / CONFIRMADA / CANCELADA): ");
        return new Reserva(idU, idR, idH, f, e);
    }

    public void mostrar(Reserva r) {
        System.out.println(r);
    }

    public void mostrarLista(List<Reserva> lista) {
        if (lista.isEmpty()) {
            System.out.println("  (sin resultados)");
            return;
        }
        lista.forEach(System.out::println);
    }

    public void mostrarTextos(List<String> lista) {
        if (lista.isEmpty()) {
            System.out.println("  (sin resultados)");
            return;
        }
        lista.forEach(s -> System.out.println("  " + s));
    }
}
