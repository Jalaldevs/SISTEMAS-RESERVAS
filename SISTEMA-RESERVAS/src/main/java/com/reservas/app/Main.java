package com.reservas.app;

import com.reservas.controlador.ControladorPrincipal;

/**
 * Punto de entrada de la aplicación de consola
 * <strong>Sistema de Reservas</strong>.
 *
 * <p>Lanza el {@link ControladorPrincipal}, que se encarga de mostrar
 * el menú principal y delegar en los distintos controladores de
 * cada módulo (usuarios, recursos, horarios, disponibilidades,
 * reservas).</p>
 */
public class Main {

    /**
     * Método principal.
     * @param args argumentos de línea de comandos (no se usan).
     */
    public static void main(String[] args) {
        new ControladorPrincipal().iniciar();
    }
}
