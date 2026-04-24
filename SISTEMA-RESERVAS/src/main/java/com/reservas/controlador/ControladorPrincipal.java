package com.reservas.controlador;

import com.reservas.util.ConexionBD;
import com.reservas.vista.Utilidades;
import com.reservas.vista.VistaPrincipal;

import java.sql.SQLException;

/**
 * Controlador principal que coordina los submenús y gestiona el ciclo
 * de vida de la conexión con la base de datos.
 */
public class ControladorPrincipal {

    private final VistaPrincipal vista = new VistaPrincipal();

    /**
     * Inicia la aplicación: comprueba la conexión y muestra el menú
     * principal hasta que el usuario decida salir.
     */
    public void iniciar() {
        try {
            ConexionBD.getConexion();
            vista.mostrarMensaje("Conexión con la base de datos establecida correctamente.");
        } catch (SQLException e) {
            vista.mostrarError("No se pudo conectar a la base de datos: " + e.getMessage());
            vista.mostrarError("Revisa las variables DB_URL, DB_USER y DB_PASSWORD.");
            return;
        }

        int op;
        do {
            op = vista.mostrarMenuPrincipal();
            switch (op) {
                case 1 -> new ControladorUsuario().ejecutar();
                case 2 -> new ControladorRecurso().ejecutar();
                case 3 -> new ControladorHorario().ejecutar();
                case 4 -> new ControladorDisponibleEn().ejecutar();
                case 5 -> new ControladorReserva().ejecutar();
                case 0 -> vista.mostrarMensaje("Saliendo de la aplicación. ¡Hasta pronto!");
                default -> {
                    vista.mostrarError("Opción no válida.");
                    Utilidades.pausar();
                }
            }
        } while (op != 0);

        ConexionBD.cerrarConexion();
    }
}
