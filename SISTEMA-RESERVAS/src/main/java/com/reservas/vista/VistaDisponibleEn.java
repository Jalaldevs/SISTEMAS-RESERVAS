package com.reservas.vista;

import com.reservas.modelo.DisponibleEn;

import java.util.List;

/**
 * Vista para la gestión de la tabla intermedia DISPONIBLEEN.
 */
public class VistaDisponibleEn {

    public int mostrarMenu() {
        Utilidades.cabecera("GESTIÓN DE DISPONIBILIDADES");
        System.out.println("  1. Asociar recurso a horario");
        System.out.println("  2. Eliminar asociación");
        System.out.println("  3. Listar todas las asociaciones");
        System.out.println("  4. Listar asociaciones detalladas (JOIN)");
        System.out.println("  5. Listar horarios disponibles para un recurso");
        System.out.println("  0. Volver");
        return Utilidades.leerInt("\nElige una opción: ");
    }

    public DisponibleEn pedirDatos() {
        int idR = Utilidades.leerInt("ID del recurso: ");
        int idH = Utilidades.leerInt("ID del horario: ");
        return new DisponibleEn(idR, idH);
    }

    public void mostrarLista(List<DisponibleEn> lista) {
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
