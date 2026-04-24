package com.reservas.modelo;

import java.sql.Time;

/**
 * Modelo que representa la tabla <code>HORARIO</code>.
 */
public class Horario {

    private int id;
    private String diaSemana;
    private Time horaInicio;
    private Time horaFin;

    public Horario() { }

    public Horario(int id, String diaSemana, Time horaInicio, Time horaFin) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Horario(String diaSemana, Time horaInicio, Time horaFin) {
        this(0, diaSemana, horaInicio, horaFin);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    public Time getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Time horaInicio) { this.horaInicio = horaInicio; }

    public Time getHoraFin() { return horaFin; }
    public void setHoraFin(Time horaFin) { this.horaFin = horaFin; }

    @Override
    public String toString() {
        return String.format("Horario [id=%d, día=%s, %s - %s]",
                id, diaSemana, horaInicio, horaFin);
    }
}
