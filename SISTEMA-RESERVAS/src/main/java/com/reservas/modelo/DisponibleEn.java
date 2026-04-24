package com.reservas.modelo;

/**
 * Modelo que representa la tabla intermedia <code>DISPONIBLEEN</code>,
 * que asocia recursos con los horarios en los que están disponibles
 * (relación N:M).
 */
public class DisponibleEn {

    private int idRecurso;
    private int idHorario;

    public DisponibleEn() { }

    public DisponibleEn(int idRecurso, int idHorario) {
        this.idRecurso = idRecurso;
        this.idHorario = idHorario;
    }

    public int getIdRecurso() { return idRecurso; }
    public void setIdRecurso(int idRecurso) { this.idRecurso = idRecurso; }

    public int getIdHorario() { return idHorario; }
    public void setIdHorario(int idHorario) { this.idHorario = idHorario; }

    @Override
    public String toString() {
        return String.format("DisponibleEn [recurso=%d, horario=%d]", idRecurso, idHorario);
    }
}
