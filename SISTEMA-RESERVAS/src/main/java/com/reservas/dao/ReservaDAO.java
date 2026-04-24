package com.reservas.dao;

import com.reservas.modelo.Reserva;
import com.reservas.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla <code>RESERVA</code>.
 */
public class ReservaDAO {

    /** Inserta una reserva y devuelve el id generado. */
    public int insertar(Reserva r) throws SQLException {
        String sql = "INSERT INTO RESERVA (id_usuario, id_recurso, id_horario, fecha, estado) " +
                     "VALUES (?, ?, ?, ?, ?)";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getIdUsuario());
            ps.setInt(2, r.getIdRecurso());
            ps.setInt(3, r.getIdHorario());
            ps.setDate(4, r.getFecha());
            ps.setString(5, r.getEstado());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) r.setId(rs.getInt(1));
            }
        }
        return r.getId();
    }

    /** Modifica una reserva. */
    public boolean modificar(Reserva r) throws SQLException {
        String sql = "UPDATE RESERVA SET id_usuario=?, id_recurso=?, id_horario=?, fecha=?, estado=? " +
                     "WHERE id_reserva=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getIdUsuario());
            ps.setInt(2, r.getIdRecurso());
            ps.setInt(3, r.getIdHorario());
            ps.setDate(4, r.getFecha());
            ps.setString(5, r.getEstado());
            ps.setInt(6, r.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /** Elimina una reserva por su id. */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM RESERVA WHERE id_reserva=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /** Lista todas las reservas. */
    public List<Reserva> listarTodos() throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM RESERVA ORDER BY fecha DESC, id_reserva";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    /** Busca una reserva por id. */
    public Reserva buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM RESERVA WHERE id_reserva=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    /** Lista las reservas de un usuario concreto. */
    public List<Reserva> buscarPorUsuario(int idUsuario) throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM RESERVA WHERE id_usuario=? ORDER BY fecha DESC";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /** Lista las reservas de una fecha concreta. */
    public List<Reserva> buscarPorFecha(Date fecha) throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM RESERVA WHERE fecha=? ORDER BY id_reserva";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, fecha);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /** Lista las reservas filtrando por estado (PENDIENTE, CONFIRMADA, CANCELADA). */
    public List<Reserva> buscarPorEstado(String estado) throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM RESERVA WHERE estado=? ORDER BY fecha DESC";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /**
     * Devuelve un listado detallado uniendo RESERVA, USUARIO, RECURSO y HORARIO.
     */
    public List<String> listarDetalle() throws SQLException {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT r.id_reserva, r.fecha, r.estado, " +
                     "       u.nombre AS usuario, rec.nombre AS recurso, " +
                     "       h.dia_semana, h.hora_inicio, h.hora_fin " +
                     "FROM RESERVA r " +
                     "INNER JOIN USUARIO  u   ON r.id_usuario = u.id_usuario " +
                     "INNER JOIN RECURSO  rec ON r.id_recurso = rec.id_recurso " +
                     "INNER JOIN HORARIO  h   ON r.id_horario = h.id_horario " +
                     "ORDER BY r.fecha DESC, r.id_reserva";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(String.format(
                    "Reserva #%d | %s | %s | Usuario: %s | Recurso: %s | %s %s-%s",
                    rs.getInt("id_reserva"),
                    rs.getDate("fecha"),
                    rs.getString("estado"),
                    rs.getString("usuario"),
                    rs.getString("recurso"),
                    rs.getString("dia_semana"),
                    rs.getTime("hora_inicio"),
                    rs.getTime("hora_fin")));
            }
        }
        return lista;
    }

    /** Total de reservas registradas. */
    public int contar() throws SQLException {
        String sql = "SELECT COUNT(*) FROM RESERVA";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    private Reserva mapear(ResultSet rs) throws SQLException {
        return new Reserva(
            rs.getInt("id_reserva"),
            rs.getInt("id_usuario"),
            rs.getInt("id_recurso"),
            rs.getInt("id_horario"),
            rs.getDate("fecha"),
            rs.getString("estado")
        );
    }
}
