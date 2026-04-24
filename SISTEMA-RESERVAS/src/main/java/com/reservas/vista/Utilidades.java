package com.reservas.vista;

import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;

/**
 * Utilidades de E/S por consola compartidas por todas las vistas.
 * Encapsula la lectura segura de datos del usuario.
 */
public class Utilidades {

    private static final Scanner SC = new Scanner(System.in);

    private Utilidades() { }

    /** Lee una cadena no vacía. */
    public static String leerString(String prompt) {
        System.out.print(prompt);
        return SC.nextLine().trim();
    }

    /** Lee un entero, repitiendo si la entrada no es válida. */
    public static int leerInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(SC.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  > Valor numérico no válido, inténtalo de nuevo.");
            }
        }
    }

    /** Lee una fecha en formato AAAA-MM-DD. */
    public static Date leerFecha(String prompt) {
        while (true) {
            System.out.print(prompt + " (AAAA-MM-DD): ");
            try {
                return Date.valueOf(SC.nextLine().trim());
            } catch (IllegalArgumentException e) {
                System.out.println("  > Fecha no válida.");
            }
        }
    }

    /** Lee una hora en formato HH:MM:SS. */
    public static Time leerHora(String prompt) {
        while (true) {
            System.out.print(prompt + " (HH:MM:SS): ");
            try {
                return Time.valueOf(SC.nextLine().trim());
            } catch (IllegalArgumentException e) {
                System.out.println("  > Hora no válida.");
            }
        }
    }

    /** Pausa la ejecución hasta que el usuario pulse INTRO. */
    public static void pausar() {
        System.out.print("\nPulsa INTRO para continuar...");
        SC.nextLine();
    }

    /** Imprime una cabecera decorativa. */
    public static void cabecera(String titulo) {
        String linea = "=".repeat(60);
        System.out.println("\n" + linea);
        System.out.println("  " + titulo);
        System.out.println(linea);
    }
}
