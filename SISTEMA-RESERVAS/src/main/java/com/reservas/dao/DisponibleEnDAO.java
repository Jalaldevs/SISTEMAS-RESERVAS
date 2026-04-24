package com.reservas.dao;

import com.reservas.modelo.DisponibleEn;
import com.reservas.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla intermedia <code>DISPONIBLEEN</code>
 * (relación N:M entre RECURSO y HORARIO).
 */
public class DisponibleEnDAO {

    /** Inserta una nueva disponibilidad. */
    public boolean insertar(DisponibleEn d) throws SQLException {
        String sql = "INSERT INTO DISPONIBLEEN (id_recurso, id_horario) VALUES (?, ?)";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getIdRecurso());
            ps.setInt(2, d.getIdHorario());
            return ps.executeUpdate() > 0;
        }
    }

    /** Elimina una disponibilidad concreta. */
    public boolean eliminar(int idRecurso, int idHorario) throws SQLException {
        String sql = "DELETE FROM DISPONIBLEEN WHERE id_recurso=? AND id_horario=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRecurso);
            ps.setInt(2, idHorario);
            return ps.executeUpdate() > 0;
        }
    }

    /** Lista todas las disponibilidades. */
    public List<DisponibleEn> listarTodos() throws SQLException {
        List<DisponibleEn> lista = new ArrayList<>();
        String sql = "SELECT * FROM DISPONIBLEEN";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new DisponibleEn(rs.getInt("id_recurso"), rs.getInt("id_horario")));
            }
        }
        return lista;
    }

    /**
     * Devuelve un listado “bonito” con los nombres de recurso y datos
     * de horario unidos mediante INNER JOIN.
     */
    public List<String> listarDetalle() throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT r.nombre AS recurso, h.dia_semana, h.hora_inicio, h.hora_fin " +
                     "FROM DISPONIBLEEN d " +
                     "INNER JOIN RECURSO r ON d.id_recurso = r.id_recurso " +
                     "INNER JOIN HORARIO h ON d.id_horario = h.id_horario " +
                     "ORDER BY r.nombre, h.dia_semana";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(String.format("%s -> %s (%s - %s)",
                    rs.getString("recurso"),
                    rs.getString("dia_semana"),
                    rs.getTime("hora_inicio"),
                    rs.getTime("hora_fin")));
            }
        }
        return lista;
    }

    /** Lista los horarios disponibles para un recurso. */
    public List<DisponibleEn> buscarPorRecurso(int idRecurso) throws SQLException {
        List<DisponibleEn> lista = new ArrayList<>();
        String sql = "SELECT * FROM DISPONIBLEEN WHERE id_recurso=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idRecurso);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new DisponibleEn(rs.getInt("id_recurso"), rs.getInt("id_horario")));
                }
            }
        }
        return lista;
    }
}
