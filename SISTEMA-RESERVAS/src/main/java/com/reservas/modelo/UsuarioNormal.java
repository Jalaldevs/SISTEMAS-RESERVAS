package com.reservas.modelo;

/**
 * Modelo que representa la tabla <code>USUARIONORMAL</code>,
 * especialización de {@link Usuario}.
 */
public class UsuarioNormal extends Usuario {

    private String departamento;

    public UsuarioNormal() { }

    public UsuarioNormal(int id, String nombre, String email, String password,
                         String telefono, String departamento) {
        super(id, nombre, email, password, telefono);
        this.departamento = departamento;
    }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    @Override
    public String toString() {
        return String.format("UsuarioNormal [id=%d, nombre=%s, email=%s, departamento=%s]",
                getId(), getNombre(), getEmail(), departamento);
    }
}
