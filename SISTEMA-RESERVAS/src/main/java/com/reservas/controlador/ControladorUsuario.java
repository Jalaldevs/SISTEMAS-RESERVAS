package com.reservas.controlador;

import com.reservas.dao.UsuarioDAO;
import com.reservas.modelo.Administrador;
import com.reservas.modelo.Usuario;
import com.reservas.modelo.UsuarioNormal;
import com.reservas.vista.Utilidades;
import com.reservas.vista.VistaUsuario;

import java.sql.SQLException;

/**
 * Controlador que coordina {@link VistaUsuario} con {@link UsuarioDAO}.
 */
public class ControladorUsuario {

    private final VistaUsuario vista = new VistaUsuario();
    private final UsuarioDAO   dao   = new UsuarioDAO();

    /** Bucle principal del menú de usuarios. */
    public void ejecutar() {
        int op;
        do {
            op = vista.mostrarMenu();
            try {
                switch (op) {
                    case 1 -> insertarUsuario();
                    case 2 -> insertarAdministrador();
                    case 3 -> insertarUsuarioNormal();
                    case 4 -> modificar();
                    case 5 -> eliminar();
                    case 6 -> vista.mostrarLista(dao.listarTodos());
                    case 7 -> buscarPorId();
                    case 8 -> buscarPorNombre();
                    case 9 -> vista.mostrarLista(dao.listarAdministradores());
                    case 10 -> vista.mostrarLista(dao.listarUsuariosNormales());
                    case 11 -> System.out.println("Total de usuarios: " + dao.contar());
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } catch (SQLException e) {
                System.err.println("[ERROR SQL] " + e.getMessage());
            }
            if (op != 0) Utilidades.pausar();
        } while (op != 0);
    }

    private void insertarUsuario() throws SQLException {
        Usuario u = vista.pedirDatosUsuario();
        int id = dao.insertar(u);
        System.out.println("Usuario creado con ID = " + id);
    }

    private void insertarAdministrador() throws SQLException {
        Administrador a = vista.pedirDatosAdministrador();
        dao.insertarAdministrador(a);
        System.out.println("Administrador creado con ID = " + a.getId());
    }

    private void insertarUsuarioNormal() throws SQLException {
        UsuarioNormal un = vista.pedirDatosUsuarioNormal();
        dao.insertarUsuarioNormal(un);
        System.out.println("Usuario normal creado con ID = " + un.getId());
    }

    private void modificar() throws SQLException {
        int id = Utilidades.leerInt("ID del usuario a modificar: ");
        Usuario actual = dao.buscarPorId(id);
        if (actual == null) {
            System.out.println("No existe ningún usuario con ese ID.");
            return;
        }
        vista.mostrar(actual);
        System.out.println("Introduce los nuevos datos:");
        Usuario nuevo = vista.pedirDatosUsuario();
        nuevo.setId(id);
        System.out.println(dao.modificar(nuevo) ? "Modificado correctamente." : "No se pudo modificar.");
    }

    private void eliminar() throws SQLException {
        int id = Utilidades.leerInt("ID del usuario a eliminar: ");
        System.out.println(dao.eliminar(id) ? "Eliminado correctamente." : "No se encontró el usuario.");
    }

    private void buscarPorId() throws SQLException {
        int id = Utilidades.leerInt("ID a buscar: ");
        Usuario u = dao.buscarPorId(id);
        if (u != null) vista.mostrar(u);
        else System.out.println("No existe.");
    }

    private void buscarPorNombre() throws SQLException {
        String t = Utilidades.leerString("Texto a buscar en el nombre: ");
        vista.mostrarLista(dao.buscarPorNombre(t));
    }
}
