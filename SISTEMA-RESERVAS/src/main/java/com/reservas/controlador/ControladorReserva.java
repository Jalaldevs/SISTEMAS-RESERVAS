package com.reservas.controlador;

import com.reservas.dao.ReservaDAO;
import com.reservas.modelo.Reserva;
import com.reservas.vista.Utilidades;
import com.reservas.vista.VistaReserva;

import java.sql.Date;
import java.sql.SQLException;

/**
 * Controlador del módulo de Reservas.
 */
public class ControladorReserva {

    private final VistaReserva vista = new VistaReserva();
    private final ReservaDAO   dao   = new ReservaDAO();

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
                    case 5 -> vista.mostrarTextos(dao.listarDetalle());
                    case 6 -> buscarPorId();
                    case 7 -> {
                        int id = Utilidades.leerInt("ID del usuario: ");
                        vista.mostrarLista(dao.buscarPorUsuario(id));
                    }
                    case 8 -> {
                        Date f = Utilidades.leerFecha("Fecha");
                        vista.mostrarLista(dao.buscarPorFecha(f));
                    }
                    case 9 -> {
                        String e = Utilidades.leerString("Estado (PENDIENTE/CONFIRMADA/CANCELADA): ");
                        vista.mostrarLista(dao.buscarPorEstado(e));
                    }
                    case 10 -> System.out.println("Total de reservas: " + dao.contar());
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
        Reserva r = vista.pedirDatos();
        int id = dao.insertar(r);
        System.out.println("Reserva creada con ID = " + id);
    }

    private void modificar() throws SQLException {
        int id = Utilidades.leerInt("ID de la reserva a modificar: ");
        Reserva actual = dao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No existe.");
            return;
        }
        vista.mostrar(actual);
        Reserva nueva = vista.pedirDatos();
        nueva.setId(id);
        System.out.println(dao.modificar(nueva) ? "Modificada correctamente." : "No se pudo modificar.");
    }

    private void eliminar() throws SQLException {
        int id = Utilidades.leerInt("ID de la reserva a eliminar: ");
        System.out.println(dao.eliminar(id) ? "Eliminada correctamente." : "No se encontró la reserva.");
    }

    private void buscarPorId() throws SQLException {
        int id = Utilidades.leerInt("ID a buscar: ");
        Reserva r = dao.buscarPorId(id);
        if (r != null) vista.mostrar(r);
        else System.out.println("No existe.");
    }
}
