package com.reservas.vista;

/**
 * Vista del menú principal de la aplicación.
 */
public class VistaPrincipal {

    /** Muestra el menú principal y devuelve la opción elegida. */
    public int mostrarMenuPrincipal() {
        Utilidades.cabecera("SISTEMA DE RESERVAS - MENÚ PRINCIPAL");
        System.out.println("  1. Gestión de Usuarios");
        System.out.println("  2. Gestión de Recursos");
        System.out.println("  3. Gestión de Horarios");
        System.out.println("  4. Gestión de Disponibilidades");
        System.out.println("  5. Gestión de Reservas");
        System.out.println("  0. Salir");
        return Utilidades.leerInt("\nElige una opción: ");
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println("\n" + mensaje);
    }

    public void mostrarError(String mensaje) {
        System.err.println("\n[ERROR] " + mensaje);
    }
}
