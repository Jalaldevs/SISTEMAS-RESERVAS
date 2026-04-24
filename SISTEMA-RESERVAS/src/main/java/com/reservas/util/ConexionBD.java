package com.reservas.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria que gestiona la conexión JDBC con la base de datos
 * MySQL/MariaDB <code>sistema_reservas</code>.
 *
 * <p>Implementa el patrón Singleton: existe una única instancia de
 * {@link Connection} compartida durante todo el ciclo de vida de la
 * aplicación.</p>
 *
 * <p>Los parámetros de conexión (URL, usuario y contraseña) se pueden
 * sobrescribir mediante variables de entorno:</p>
 * <ul>
 *   <li><code>DB_URL</code></li>
 *   <li><code>DB_USER</code></li>
 *   <li><code>DB_PASSWORD</code></li>
 * </ul>
 *
 * @author Proyecto Intermodular
 * @version 1.0
 */
public class ConexionBD {

    /** URL de la base de datos por defecto. */
    private static final String URL_DEFECTO =
            "jdbc:mysql://localhost:3306/sistema_reservas?useSSL=false&serverTimezone=UTC";
    /** Usuario por defecto. */
    private static final String USER_DEFECTO = "admin";
    /** Contraseña por defecto. */
    private static final String PASS_DEFECTO = "admin";

    /** Instancia única de la conexión. */
    private static Connection conexion = null;

    /** Constructor privado para evitar instanciación. */
    private ConexionBD() { }

    /**
     * Devuelve la conexión activa con la base de datos. Si no existe o
     * está cerrada, la crea.
     *
     * @return Conexión JDBC abierta.
     * @throws SQLException si no se puede establecer la conexión.
     */
    public static Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            String url  = System.getenv().getOrDefault("DB_URL",      URL_DEFECTO);
            String user = System.getenv().getOrDefault("DB_USER",     USER_DEFECTO);
            String pass = System.getenv().getOrDefault("DB_PASSWORD", PASS_DEFECTO);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException("No se ha encontrado el driver JDBC de MySQL", e);
            }
            conexion = DriverManager.getConnection(url, user, pass);
        }
        return conexion;
    }

    /**
     * Cierra la conexión si está abierta.
     */
    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
