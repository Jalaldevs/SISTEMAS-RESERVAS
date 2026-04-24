package com.reservas.dao;

import com.reservas.modelo.Horario;
import com.reservas.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para la tabla <code>HORARIO</code>.
 */
public class HorarioDAO {

    /** Inserta un horario y devuelve su id generado. */
    public int insertar(Horario h) throws SQLException {
        String sql = "INSERT INTO HORARIO (dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?)";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, h.getDiaSemana());
            ps.setTime(2, h.getHoraInicio());
            ps.setTime(3, h.getHoraFin());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) h.setId(rs.getInt(1));
            }
        }
        return h.getId();
    }

    /** Modifica un horario. */
    public boolean modificar(Horario h) throws SQLException {
        String sql = "UPDATE HORARIO SET dia_semana=?, hora_inicio=?, hora_fin=? WHERE id_horario=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, h.getDiaSemana());
            ps.setTime(2, h.getHoraInicio());
            ps.setTime(3, h.getHoraFin());
            ps.setInt(4, h.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /** Elimina un horario por id. */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM HORARIO WHERE id_horario=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /** Lista todos los horarios ordenados por día y hora. */
    public List<Horario> listarTodos() throws SQLException {
        List<Horario> lista = new ArrayList<>();
        String sql = "SELECT * FROM HORARIO ORDER BY dia_semana, hora_inicio";
        Connection con = ConexionBD.getConexion();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    /** Busca un horario por id. */
    public Horario buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM HORARIO WHERE id_horario=?";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    /** Filtra horarios por día de la semana. */
    public List<Horario> buscarPorDia(String dia) throws SQLException {
        List<Horario> lista = new ArrayList<>();
        String sql = "SELECT * FROM HORARIO WHERE dia_semana=? ORDER BY hora_inicio";
        Connection con = ConexionBD.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dia);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

    private Horario mapear(ResultSet rs) throws SQLException {
        return new Horario(
            rs.getInt("id_horario"),
            rs.getString("dia_semana"),
            rs.getTime("hora_inicio"),
            rs.getTime("hora_fin")
        );
    }
}
