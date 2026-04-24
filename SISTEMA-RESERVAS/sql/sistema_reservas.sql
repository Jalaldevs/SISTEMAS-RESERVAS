-- =====================================================================
-- Script de creación de la base de datos: sistema_reservas
-- Proyecto Intermodular - UT11 Acceso a Bases de Datos
-- =====================================================================

DROP DATABASE IF EXISTS sistema_reservas;
CREATE DATABASE sistema_reservas CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sistema_reservas;

-- ---------------------------------------------------------------------
-- Tabla USUARIO (superclase)
-- ---------------------------------------------------------------------
CREATE TABLE USUARIO (
    id_usuario   INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100)  NOT NULL,
    email        VARCHAR(150)  NOT NULL UNIQUE,
    password     VARCHAR(100)  NOT NULL,
    telefono     VARCHAR(20)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Tabla ADMINISTRADOR (especialización de USUARIO)
-- ---------------------------------------------------------------------
CREATE TABLE ADMINISTRADOR (
    id_usuario     INT PRIMARY KEY,
    nivel_acceso   INT NOT NULL DEFAULT 1,
    CONSTRAINT fk_admin_usuario
        FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Tabla USUARIONORMAL (especialización de USUARIO)
-- ---------------------------------------------------------------------
CREATE TABLE USUARIONORMAL (
    id_usuario     INT PRIMARY KEY,
    departamento   VARCHAR(100),
    CONSTRAINT fk_unormal_usuario
        FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Tabla RECURSO
-- ---------------------------------------------------------------------
CREATE TABLE RECURSO (
    id_recurso   INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL,
    tipo         VARCHAR(50)  NOT NULL,
    descripcion  VARCHAR(255),
    ubicacion    VARCHAR(100)
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Tabla HORARIO
-- ---------------------------------------------------------------------
CREATE TABLE HORARIO (
    id_horario   INT AUTO_INCREMENT PRIMARY KEY,
    dia_semana   VARCHAR(15) NOT NULL,
    hora_inicio  TIME        NOT NULL,
    hora_fin     TIME        NOT NULL
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Tabla DISPONIBLEEN (relación N:M entre RECURSO y HORARIO)
-- ---------------------------------------------------------------------
CREATE TABLE DISPONIBLEEN (
    id_recurso  INT NOT NULL,
    id_horario  INT NOT NULL,
    PRIMARY KEY (id_recurso, id_horario),
    CONSTRAINT fk_disp_recurso
        FOREIGN KEY (id_recurso) REFERENCES RECURSO(id_recurso)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_disp_horario
        FOREIGN KEY (id_horario) REFERENCES HORARIO(id_horario)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ---------------------------------------------------------------------
-- Tabla RESERVA
-- ---------------------------------------------------------------------
CREATE TABLE RESERVA (
    id_reserva  INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario  INT  NOT NULL,
    id_recurso  INT  NOT NULL,
    id_horario  INT  NOT NULL,
    fecha       DATE NOT NULL,
    estado      VARCHAR(20) NOT NULL DEFAULT 'PENDIENTE',
    CONSTRAINT fk_reserva_usuario
        FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_reserva_recurso
        FOREIGN KEY (id_recurso) REFERENCES RECURSO(id_recurso)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_reserva_horario
        FOREIGN KEY (id_horario) REFERENCES HORARIO(id_horario)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- =====================================================================
-- DATOS DE EJEMPLO
-- =====================================================================

-- Usuarios
INSERT INTO USUARIO (nombre, email, password, telefono) VALUES
('Ana García',     'ana.garcia@empresa.com',    '1234', '600111222'),
('Luis Martínez',  'luis.martinez@empresa.com', '1234', '600333444'),
('María López',    'maria.lopez@empresa.com',   '1234', '600555666'),
('Carlos Ruiz',    'carlos.ruiz@empresa.com',   '1234', '600777888'),
('Elena Fernández','elena.fdez@empresa.com',    '1234', '600999000');

-- Administradores (los dos primeros)
INSERT INTO ADMINISTRADOR (id_usuario, nivel_acceso) VALUES
(1, 3),
(2, 2);

-- Usuarios normales (resto)
INSERT INTO USUARIONORMAL (id_usuario, departamento) VALUES
(3, 'Marketing'),
(4, 'Desarrollo'),
(5, 'Recursos Humanos');

-- Recursos
INSERT INTO RECURSO (nombre, tipo, descripcion, ubicacion) VALUES
('Sala Reuniones A',  'Sala',       'Sala con proyector para 10 personas', 'Planta 1'),
('Sala Reuniones B',  'Sala',       'Sala con TV para 6 personas',         'Planta 2'),
('Proyector Epson',   'Equipo',     'Proyector portátil Full HD',          'Almacén'),
('Coche Empresa 01',  'Vehículo',   'Renault Clio - 1234 ABC',             'Garaje'),
('Portátil Dell',     'Equipo',     'Portátil Dell Latitude 14"',          'Almacén');

-- Horarios
INSERT INTO HORARIO (dia_semana, hora_inicio, hora_fin) VALUES
('Lunes',     '09:00:00', '11:00:00'),
('Lunes',     '11:00:00', '13:00:00'),
('Martes',    '09:00:00', '11:00:00'),
('Miércoles', '16:00:00', '18:00:00'),
('Jueves',    '10:00:00', '12:00:00'),
('Viernes',   '09:00:00', '14:00:00');

-- Disponibilidad
INSERT INTO DISPONIBLEEN (id_recurso, id_horario) VALUES
(1, 1), (1, 2), (1, 5),
(2, 3), (2, 4),
(3, 1), (3, 6),
(4, 6),
(5, 2), (5, 5);

-- Reservas
INSERT INTO RESERVA (id_usuario, id_recurso, id_horario, fecha, estado) VALUES
(3, 1, 1, '2026-05-04', 'CONFIRMADA'),
(4, 2, 3, '2026-05-05', 'PENDIENTE'),
(5, 3, 6, '2026-05-08', 'CONFIRMADA'),
(3, 5, 2, '2026-05-04', 'CANCELADA'),
(4, 1, 5, '2026-05-07', 'PENDIENTE');
