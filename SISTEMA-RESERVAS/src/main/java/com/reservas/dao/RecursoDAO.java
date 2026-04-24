package com.reservas.dao;

import com.reservas.modelo.Recurso;
import com.reservas.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla <code>RECURSO</code>.
 */
public class RecursoDAO {

    /** Inserta un recurso y devuelve el id generado. */
    public int insertar(Recurso r) throws SQLException {
        String sql = "INSERT INTO RECURSO (nombre, tipo, descripcion, ubicacion) VALUES (?, ?, ?, ?)";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getTipo());
            ps.setString(3, r.getDescripcion());
            ps.setString(4, r.getUbicacion());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) r.setId(rs.getInt(1));
            }
        }
        return r.getId();
    }

    /** Modifica un recurso existente. */
    public boolean modificar(Recurso r) throws SQLException {
        String sql = "UPDATE RECURSO SET nombre=?, tipo=?, descripcion=?, ubicacion=? WHERE id_recurso=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, r.getNombre());
            ps.setString(2, r.getTipo());
            ps.setString(3, r.getDescripcion());
            ps.setString(4, r.getUbicacion());
            ps.setInt(5, r.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /** Elimina un recurso por su id. */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM RECURSO WHERE id_recurso=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /** Lista todos los recursos. */
    public List<Recurso> listarTodos() throws SQLException {
        List<Recurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM RECURSO ORDER BY id_recurso";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    /** Busca un recurso por id. */
    public Recurso buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM RECURSO WHERE id_recurso=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    /** Busca recursos por tipo. */
    public List<Recurso> buscarPorTipo(String tipo) throws SQLException {
        List<Recurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM RECURSO WHERE tipo LIKE ?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + tipo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /** Cuenta recursos agrupados por tipo. */
    public List<String> contarPorTipo() throws SQLException {
        List<String> resultado = new ArrayList<>();
        String sql = "SELECT tipo, COUNT(*) AS total FROM RECURSO GROUP BY tipo";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                resultado.add(rs.getString("tipo") + " -> " + rs.getInt("total"));
            }
        }
        return resultado;
    }

    private Recurso mapear(ResultSet rs) throws SQLException {
        return new Recurso(
            rs.getInt("id_recurso"),
            rs.getString("nombre"),
            rs.getString("tipo"),
            rs.getString("descripcion"),
            rs.getString("ubicacion")
        );
    }
}
