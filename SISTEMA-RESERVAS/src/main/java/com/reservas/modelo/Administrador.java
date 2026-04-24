package com.reservas.modelo;

/**
 * Modelo que representa la tabla <code>ADMINISTRADOR</code>,
 * especialización de {@link Usuario}.
 */
public class Administrador extends Usuario {

    private int nivelAcceso;

    public Administrador() { }

    public Administrador(int id, String nombre, String email, String password,
                         String telefono, int nivelAcceso) {
        super(id, nombre, email, password, telefono);
        this.nivelAcceso = nivelAcceso;
    }

    public int getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(int nivelAcceso) { this.nivelAcceso = nivelAcceso; }

    @Override
    public String toString() {
        return String.format("Administrador [id=%d, nombre=%s, email=%s, nivelAcceso=%d]",
                getId(), getNombre(), getEmail(), nivelAcceso);
    }
}
