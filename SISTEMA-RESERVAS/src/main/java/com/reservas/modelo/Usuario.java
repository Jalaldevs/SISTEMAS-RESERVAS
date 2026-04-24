package com.reservas.modelo;

/**
 * Modelo que representa la tabla <code>USUARIO</code>.
 * Es la superclase de {@link Administrador} y {@link UsuarioNormal}.
 */
public class Usuario {

    private int id;
    private String nombre;
    private String email;
    private String password;
    private String telefono;

    public Usuario() { }

    public Usuario(int id, String nombre, String email, String password, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
    }

    public Usuario(String nombre, String email, String password, String telefono) {
        this(0, nombre, email, password, telefono);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return String.format("Usuario [id=%d, nombre=%s, email=%s, telefono=%s]",
                id, nombre, email, telefono);
    }
}
