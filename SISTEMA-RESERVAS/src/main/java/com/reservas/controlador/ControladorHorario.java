package com.reservas.controlador;

import com.reservas.dao.HorarioDAO;
import com.reservas.modelo.Horario;
import com.reservas.vista.Utilidades;
import com.reservas.vista.VistaHorario;

import java.sql.SQLException;

/**
 * Controlador del módulo de Horarios.
 */
public class ControladorHorario {

    private final VistaHorario vista = new VistaHorario();
    private final HorarioDAO   dao   = new HorarioDAO();

    public void ejecutar() {
        int op;
        do {
            op = vista.mostrarMenu();
            try {
                switch (op) {
                    case 1 -> insertar();
                    case 2 -> modificar();
                    case 3 -> eliminar();
                    case 4 -> vista.mostrarLista(dao.listarTodos());
                    case 5 -> buscarPorId();
                    case 6 -> buscarPorDia();
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
        Horario h = vista.pedirDatos();
        int id = dao.insertar(h);
        System.out.println("Horario creado con ID = " + id);
    }

    private void modificar() throws SQLException {
        int id = Utilidades.leerInt("ID del horario a modificar: ");
        Horario actual = dao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No existe.");
            return;
        }
        vista.mostrar(actual);
        Horario nuevo = vista.pedirDatos();
        nuevo.setId(id);
        System.out.println(dao.modificar(nuevo) ? "Modificado correctamente." : "No se pudo modificar.");
    }

    private void eliminar() throws SQLException {
        int id = Utilidades.leerInt("ID del horario a eliminar: ");
        System.out.println(dao.eliminar(id) ? "Eliminado correctamente." : "No se encontró el horario.");
    }

    private void buscarPorId() throws SQLException {
        int id = Utilidades.leerInt("ID a buscar: ");
        Horario h = dao.buscarPorId(id);
        if (h != null) vista.mostrar(h);
        else System.out.println("No existe.");
    }

    private void buscarPorDia() throws SQLException {
        String d = Utilidades.leerString("Día (Lunes, Martes...): ");
        vista.mostrarLista(dao.buscarPorDia(d));
    }
}
