package com.reservas.vista;

import com.reservas.modelo.Horario;

import java.sql.Time;
import java.util.List;

/**
 * Vista para los menús e impresión de datos de Horarios.
 */
public class VistaHorario {

    public int mostrarMenu() {
        Utilidades.cabecera("GESTIÓN DE HORARIOS");
        System.out.println("  1. Insertar horario");
        System.out.println("  2. Modificar horario");
        System.out.println("  3. Eliminar horario");
        System.out.println("  4. Listar todos los horarios");
        System.out.println("  5. Buscar horario por ID");
        System.out.println("  6. Buscar horarios por día de la semana");
        System.out.println("  0. Volver");
        return Utilidades.leerInt("\nElige una opción: ");
    }

    public Horario pedirDatos() {
        String dia = Utilidades.leerString("Día de la semana (Lunes, Martes...): ");
        Time hi    = Utilidades.leerHora("Hora de inicio");
        Time hf    = Utilidades.leerHora("Hora de fin");
        return new Horario(dia, hi, hf);
    }

    public void mostrar(Horario h) {
        System.out.println(h);
    }

    public void mostrarLista(List<Horario> lista) {
        if (lista.isEmpty()) {
            System.out.println("  (sin resultados)");
            return;
        }
        lista.forEach(System.out::println);
    }
}
