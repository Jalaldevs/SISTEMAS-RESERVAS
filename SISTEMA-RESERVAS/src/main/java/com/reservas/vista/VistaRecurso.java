package com.reservas.vista;

import com.reservas.modelo.Recurso;

import java.util.List;

/**
 * Vista para los menús e impresión de datos de Recursos.
 */
public class VistaRecurso {

    public int mostrarMenu() {
        Utilidades.cabecera("GESTIÓN DE RECURSOS");
        System.out.println("  1. Insertar recurso");
        System.out.println("  2. Modificar recurso");
        System.out.println("  3. Eliminar recurso");
        System.out.println("  4. Listar todos los recursos");
        System.out.println("  5. Buscar recurso por ID");
        System.out.println("  6. Buscar recursos por tipo");
        System.out.println("  7. Contar recursos por tipo");
        System.out.println("  0. Volver");
        return Utilidades.leerInt("\nElige una opción: ");
    }

    public Recurso pedirDatos() {
        String nombre = Utilidades.leerString("Nombre: ");
        String tipo   = Utilidades.leerString("Tipo (Sala, Equipo, Vehículo...): ");
        String desc   = Utilidades.leerString("Descripción: ");
        String ubic   = Utilidades.leerString("Ubicación: ");
        return new Recurso(nombre, tipo, desc, ubic);
    }

    public void mostrar(Recurso r) {
        System.out.println(r);
    }

    public void mostrarLista(List<Recurso> lista) {
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
