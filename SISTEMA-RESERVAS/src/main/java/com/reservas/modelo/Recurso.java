package com.reservas.modelo;

/**
 * Modelo que representa la tabla <code>RECURSO</code>.
 */
public class Recurso {

    private int id;
    private String nombre;
    private String tipo;
    private String descripcion;
    private String ubicacion;

    public Recurso() { }

    public Recurso(int id, String nombre, String tipo, String descripcion, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public Recurso(String nombre, String tipo, String descripcion, String ubicacion) {
        this(0, nombre, tipo, descripcion, ubicacion);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    @Override
    public String toString() {
        return String.format("Recurso [id=%d, nombre=%s, tipo=%s, ubicacion=%s, descripcion=%s]",
                id, nombre, tipo, ubicacion, descripcion);
    }
}
