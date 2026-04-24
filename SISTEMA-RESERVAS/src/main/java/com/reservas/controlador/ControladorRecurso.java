package com.reservas.controlador;

import com.reservas.dao.RecursoDAO;
import com.reservas.modelo.Recurso;
import com.reservas.vista.Utilidades;
import com.reservas.vista.VistaRecurso;

import java.sql.SQLException;

/**
 * Controlador del módulo de Recursos.
 */
public class ControladorRecurso {

    private final VistaRecurso vista = new VistaRecurso();
    private final RecursoDAO   dao   = new RecursoDAO();

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
                    case 6 -> buscarPorTipo();
                    case 7 -> vista.mostrarTextos(dao.contarPorTipo());
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
        Recurso r = vista.pedirDatos();
        int id = dao.insertar(r);
        System.out.println("Recurso creado con ID = " + id);
    }

    private void modificar() throws SQLException {
        int id = Utilidades.leerInt("ID del recurso a modificar: ");
        Recurso actual = dao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No existe.");
            return;
        }
        vista.mostrar(actual);
        Recurso nuevo = vista.pedirDatos();
        nuevo.setId(id);
        System.out.println(dao.modificar(nuevo) ? "Modificado correctamente." : "No se pudo modificar.");
    }

    private void eliminar() throws SQLException {
        int id = Utilidades.leerInt("ID del recurso a eliminar: ");
        System.out.println(dao.eliminar(id) ? "Eliminado correctamente." : "No se encontró el recurso.");
    }

    private void buscarPorId() throws SQLException {
        int id = Utilidades.leerInt("ID a buscar: ");
        Recurso r = dao.buscarPorId(id);
        if (r != null) vista.mostrar(r);
        else System.out.println("No existe.");
    }

    private void buscarPorTipo() throws SQLException {
        String t = Utilidades.leerString("Tipo a buscar: ");
        vista.mostrarLista(dao.buscarPorTipo(t));
    }
}
