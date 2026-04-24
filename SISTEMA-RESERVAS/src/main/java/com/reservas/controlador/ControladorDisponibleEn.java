package com.reservas.controlador;

import com.reservas.dao.DisponibleEnDAO;
import com.reservas.modelo.DisponibleEn;
import com.reservas.vista.Utilidades;
import com.reservas.vista.VistaDisponibleEn;

import java.sql.SQLException;

/**
 * Controlador del módulo de Disponibilidades (DISPONIBLEEN).
 */
public class ControladorDisponibleEn {

    private final VistaDisponibleEn vista = new VistaDisponibleEn();
    private final DisponibleEnDAO   dao   = new DisponibleEnDAO();

    public void ejecutar() {
        int op;
        do {
            op = vista.mostrarMenu();
            try {
                switch (op) {
                    case 1 -> insertar();
                    case 2 -> eliminar();
                    case 3 -> vista.mostrarLista(dao.listarTodos());
                    case 4 -> vista.mostrarTextos(dao.listarDetalle());
                    case 5 -> {
                        int id = Utilidades.leerInt("ID del recurso: ");
                        vista.mostrarLista(dao.buscarPorRecurso(id));
                    }
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException e) {
                System.err.println("[ERROR SQL] " + e.getMessage());
            }
            if (op != 0) Utilidades.pausar();
        } while (op != 0);
    }

    private void insertar() throws SQLException {
        DisponibleEn d = vista.pedirDatos();
        System.out.println(dao.insertar(d) ? "Asociación creada." : "No se pudo crear.");
    }

    private void eliminar() throws SQLException {
        int idR = Utilidades.leerInt("ID del recurso: ");
        int idH = Utilidades.leerInt("ID del horario: ");
        System.out.println(dao.eliminar(idR, idH) ? "Eliminada." : "No se encontró la asociación.");
    }
}
