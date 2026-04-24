package com.reservas.modelo;

import java.sql.Date;

/**
 * Modelo que representa la tabla <code>RESERVA</code>.
 */
public class Reserva {

    private int id;
    private int idUsuario;
    private int idRecurso;
    private int idHorario;
    private Date fecha;
    private String estado;

    public Reserva() { }

    public Reserva(int id, int idUsuario, int idRecurso, int idHorario,
                   Date fecha, String estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idRecurso = idRecurso;
        this.idHorario = idHorario;
        this.fecha = fecha;
        this.estado = estado;
    }

    public Reserva(int idUsuario, int idRecurso, int idHorario,
                   Date fecha, String estado) {
        this(0, idUsuario, idRecurso, idHorario, fecha, estado);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public int getIdRecurso() { return idRecurso; }
    public void setIdRecurso(int idRecurso) { this.idRecurso = idRecurso; }

    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return String.format("Reserva [id=%d, usuario=%d, recurso=%d, horario=%d, fecha=%s, estado=%s]",
                id, idUsuario, idRecurso, idHorario, fecha, estado);
    }
}
