# Sistema de Reservas — Proyecto Intermodular (UT11)

Aplicación de consola en **Java** que gestiona la base de datos
`sistema_reservas` mediante **JDBC** y aplicando el patrón
**Modelo-Vista-Controlador (MVC)**.

> **Nivel implementado: 5** — CRUD de toda la base de datos con MVC
> (nota máxima 10).

---

## 1. Requisitos

- **Java 21** (LTS) o superior (`java -version`)
- **Maven 3.8+** (`mvn -version`)
- **MySQL** o **MariaDB** corriendo en `localhost:3306`

## 2. Crear la base de datos

Ejecuta el script SQL incluido. Crea la BD, las tablas y datos de ejemplo:

```bash
mysql -u root -p < sql/sistema_reservas.sql
```

## 3. Configurar la conexión

Por defecto la aplicación se conecta con:

| Variable      | Valor por defecto                                                              |
|---------------|--------------------------------------------------------------------------------|
| `DB_URL`      | `jdbc:mysql://localhost:3306/sistema_reservas?useSSL=false&serverTimezone=UTC` |
| `DB_USER`     | `root`                                                                         |
| `DB_PASSWORD` | *(vacío)*                                                                      |

Puedes sobrescribir estos valores con variables de entorno antes de ejecutar:

```bash
export DB_USER=miUsuario
export DB_PASSWORD=miClave
```

## 4. Compilar y ejecutar

```bash
# Compilar
mvn clean package

# Ejecutar (opción A: vía Maven)
mvn exec:java

# Ejecutar (opción B: el jar generado con todas las dependencias)
java -jar target/sistema-reservas-jar-with-dependencies.jar
```

---

## 5. Estructura del proyecto (MVC)

```
sistema-reservas/
├── pom.xml
├── README.md
├── sql/
│   └── sistema_reservas.sql        # Script de creación + datos
└── src/main/java/com/reservas/
    ├── app/
    │   └── Main.java               # Punto de entrada
    ├── util/
    │   └── ConexionBD.java         # Conexión JDBC (Singleton)
    ├── modelo/                     # Entidades / POJOs
    │   ├── Usuario.java
    │   ├── Administrador.java
    │   ├── UsuarioNormal.java
    │   ├── Recurso.java
    │   ├── Horario.java
    │   ├── DisponibleEn.java
    │   └── Reserva.java
    ├── dao/                        # Capa de acceso a datos (SQL)
    │   ├── UsuarioDAO.java
    │   ├── RecursoDAO.java
    │   ├── HorarioDAO.java
    │   ├── DisponibleEnDAO.java
    │   └── ReservaDAO.java
    ├── vista/                      # Interfaz de consola
    │   ├── Utilidades.java
    │   ├── VistaPrincipal.java
    │   ├── VistaUsuario.java
    │   ├── VistaRecurso.java
    │   ├── VistaHorario.java
    │   ├── VistaDisponibleEn.java
    │   └── VistaReserva.java
    └── controlador/                # Lógica de coordinación
        ├── ControladorPrincipal.java
        ├── ControladorUsuario.java
        ├── ControladorRecurso.java
        ├── ControladorHorario.java
        ├── ControladorDisponibleEn.java
        └── ControladorReserva.java
```

## 6. Funcionalidades

Para **cada tabla** (USUARIO/ADMINISTRADOR/USUARIONORMAL, RECURSO,
HORARIO, DISPONIBLEEN, RESERVA) se implementa:

- **Insertar** (`INSERT INTO ...`)
- **Modificar** (`UPDATE ...`)
- **Eliminar** (`DELETE FROM ...`)
- **Consultar todos** (`SELECT *`)
- **Consultar por condición** (por ID, por nombre, por tipo, por fecha,
  por estado, por día...)
- **Consultas con `JOIN`** entre las tablas relacionadas
  (Reservas detalladas, Disponibilidades detalladas)
- **Conteos / agregados** (`COUNT`, `GROUP BY`)

Los inserts de Administrador y Usuario Normal se ejecutan dentro de una
**transacción** (`commit/rollback`) para mantener la integridad entre
`USUARIO` y su tabla hija.

## 7. Cumplimiento de la rúbrica

| Criterio                               | Puntos | Cumplimiento |
|----------------------------------------|:------:|--------------|
| Funcionamiento CRUD                    |   4    | CRUD completo de **todas** las tablas |
| Patrón MVC                             |   2    | Paquetes `modelo`, `vista`, `controlador`, `dao` |
| Estructura del código                  |   1    | Clases pequeñas y métodos con responsabilidad única |
| Nombrado de variables                  |   1    | Nombres en español, descriptivos |
| Documentación                          |   1    | JavaDoc en todas las clases públicas |
| Manejo de errores                      |   1    | `try/catch` en todos los DAOs y controladores, transacciones con rollback |

## 8. Entrega

1. **Repositorio Git privado** con permisos para
   `e.tellechea@edu.gva.es`.
2. **Vídeo explicativo (3-5 min)** en OneDrive mostrando el
   funcionamiento del menú y comentando el código.
