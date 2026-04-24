package com.reservas.dao;

import com.reservas.modelo.Administrador;
import com.reservas.modelo.Usuario;
import com.reservas.modelo.UsuarioNormal;
import com.reservas.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object) para las operaciones CRUD sobre las tablas
 * <code>USUARIO</code>, <code>ADMINISTRADOR</code> y
 * <code>USUARIONORMAL</code>.
 */
public class UsuarioDAO {

    /**
     * Inserta un nuevo usuario base en la tabla <code>USUARIO</code>.
     *
     * @param u Usuario a insertar.
     * @return Identificador generado por la base de datos.
     * @throws SQLException si falla la operación.
     */
    public int insertar(Usuario u) throws SQLException {
        String sql = "INSERT INTO USUARIO (nombre, email, password, telefono) VALUES (?, ?, ?, ?)";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getTelefono());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    u.setId(rs.getInt(1));
                }
            }
        }
        return u.getId();
    }

    /**
     * Inserta un administrador (registra el usuario base y la
     * especialización en {@code ADMINISTRADOR}).
     */
    public void insertarAdministrador(Administrador a) throws SQLException {
        Connection con = ConexionBD.getConexion();
        try {
            con.setAutoCommit(false);
            int id = insertar(a);
            String sql = "INSERT INTO ADMINISTRADOR (id_usuario, nivel_acceso) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setInt(2, a.getNivelAcceso());
                ps.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

    /**
     * Inserta un usuario normal (registra el usuario base y la
     * especialización en {@code USUARIONORMAL}).
     */
    public void insertarUsuarioNormal(UsuarioNormal un) throws SQLException {
        Connection con = ConexionBD.getConexion();
        try {
            con.setAutoCommit(false);
            int id = insertar(un);
            String sql = "INSERT INTO USUARIONORMAL (id_usuario, departamento) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, un.getDepartamento());
                ps.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        } finally {
            con.setAutoCommit(true);
        }
    }

    /**
     * Modifica los datos básicos de un usuario.
     */
    public boolean modificar(Usuario u) throws SQLException {
        String sql = "UPDATE USUARIO SET nombre=?, email=?, password=?, telefono=? WHERE id_usuario=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getTelefono());
            ps.setInt(5, u.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un usuario por su identificador.
     * Las eliminaciones en las tablas hijas se hacen en cascada.
     */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM USUARIO WHERE id_usuario=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Recupera todos los usuarios.
     */
    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO ORDER BY id_usuario";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /**
     * Busca un usuario por su identificador.
     */
    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM USUARIO WHERE id_usuario=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }

    /**
     * Busca usuarios cuyo nombre contenga la cadena dada (LIKE).
     */
    public List<Usuario> buscarPorNombre(String texto) throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM USUARIO WHERE nombre LIKE ?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Recupera todos los administradores con sus datos de usuario.
     */
    public List<Administrador> listarAdministradores() throws SQLException {
        List<Administrador> lista = new ArrayList<>();
        String sql = "SELECT u.*, a.nivel_acceso FROM USUARIO u " +
                     "INNER JOIN ADMINISTRADOR a ON u.id_usuario = a.id_usuario";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Administrador(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("telefono"),
                    rs.getInt("nivel_acceso")
                ));
            }
        }
        return lista;
    }

    /**
     * Recupera todos los usuarios normales con sus datos de usuario.
     */
    public List<UsuarioNormal> listarUsuariosNormales() throws SQLException {
        List<UsuarioNormal> lista = new ArrayList<>();
        String sql = "SELECT u.*, n.departamento FROM USUARIO u " +
                     "INNER JOIN USUARIONORMAL n ON u.id_usuario = n.id_usuario";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new UsuarioNormal(
                    rs.getInt("id_usuario"),
                    rs.getString("nombre"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("telefono"),
                    rs.getString("departamento")
                ));
            }
        }
        return lista;
    }

    /** Devuelve el número total de usuarios. */
    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) FROM USUARIO";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    /** Convierte un {@link ResultSet} en un objeto {@link Usuario}. */
    private Usuario mapear(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("id_usuario"),
            rs.getString("nombre"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("telefono")
        );
    }
}
