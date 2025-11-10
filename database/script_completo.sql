-- ====================================================================
-- SCRIPT COMPLETO - Plataforma de Cursos
-- Incluye: creación de BD, tablas y datos de prueba
-- Compatible con XAMPP/MySQL
-- ====================================================================

-- ====================================================================
-- PASO 1: CREAR LA BASE DE DATOS
-- ====================================================================

CREATE DATABASE IF NOT EXISTS plataforma_cursos 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE plataforma_cursos;

SELECT '✓ Base de datos creada correctamente' AS Estado;

-- ====================================================================
-- PASO 2: CREAR LAS TABLAS
-- ====================================================================

-- Tabla USUARIO
CREATE TABLE IF NOT EXISTS usuario (
    idUsuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(255) NOT NULL,
    fecha_registro DATE,
    especialidad VARCHAR(100),
    tipo ENUM('ALUMNO', 'DOCENTE') NOT NULL,
    INDEX idx_email (email),
    INDEX idx_tipo (tipo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla CURSO
CREATE TABLE IF NOT EXISTS curso (
    idCurso INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    cupo INT NOT NULL DEFAULT 0,
    precio DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    estado VARCHAR(50) DEFAULT 'inactivo',
    fecha_inicio DATE,
    fecha_fin DATE,
    modalidad ENUM('Online', 'Presencial') NOT NULL,
    link_plataforma VARCHAR(255),
    plataforma VARCHAR(100),
    aula VARCHAR(100),
    direccion VARCHAR(255),
    idDocente INT NOT NULL,
    INDEX idx_modalidad (modalidad),
    INDEX idx_estado (estado),
    INDEX idx_docente (idDocente),
    FOREIGN KEY (idDocente) REFERENCES usuario(idUsuario) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla INSCRIPCION
CREATE TABLE IF NOT EXISTS inscripcion (
    idInscripcion INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
    idCurso INT NOT NULL,
    fecha DATE NOT NULL,
    estado VARCHAR(50) DEFAULT 'pendiente',
    INDEX idx_usuario (idUsuario),
    INDEX idx_curso (idCurso),
    INDEX idx_estado (estado),
    UNIQUE KEY unique_inscripcion (idUsuario, idCurso),
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario) ON DELETE CASCADE,
    FOREIGN KEY (idCurso) REFERENCES curso(idCurso) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla PAGO
CREATE TABLE IF NOT EXISTS pago (
    idPago INT AUTO_INCREMENT PRIMARY KEY,
    idInscripcion INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    metodo_pago VARCHAR(50) NOT NULL,
    cuotas INT DEFAULT 1,
    fecha_pago DATE NOT NULL,
    INDEX idx_inscripcion (idInscripcion),
    INDEX idx_fecha (fecha_pago),
    FOREIGN KEY (idInscripcion) REFERENCES inscripcion(idInscripcion) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla MODULO
CREATE TABLE IF NOT EXISTS modulo (
    idModulo INT AUTO_INCREMENT PRIMARY KEY,
    idCurso INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    contenido TEXT,
    INDEX idx_curso (idCurso),
    FOREIGN KEY (idCurso) REFERENCES curso(idCurso) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla EVALUACION
CREATE TABLE IF NOT EXISTS evaluacion (
    idEvaluacion INT AUTO_INCREMENT PRIMARY KEY,
    idModulo INT NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    nota_maxima DECIMAL(5,2) NOT NULL DEFAULT 10.00,
    INDEX idx_modulo (idModulo),
    FOREIGN KEY (idModulo) REFERENCES modulo(idModulo) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla CALIFICACION
CREATE TABLE IF NOT EXISTS calificacion (
    idCalificacion INT AUTO_INCREMENT PRIMARY KEY,
    idUsuario INT NOT NULL,
    idCurso INT NOT NULL,
    idEvaluacion INT NOT NULL,
    nota DECIMAL(5,2) NOT NULL,
    comentario TEXT,
    fecha_calificacion DATE NOT NULL,
    INDEX idx_usuario (idUsuario),
    INDEX idx_curso (idCurso),
    INDEX idx_evaluacion (idEvaluacion),
    UNIQUE KEY unique_calificacion (idUsuario, idEvaluacion),
    FOREIGN KEY (idUsuario) REFERENCES usuario(idUsuario) ON DELETE CASCADE,
    FOREIGN KEY (idCurso) REFERENCES curso(idCurso) ON DELETE CASCADE,
    FOREIGN KEY (idEvaluacion) REFERENCES evaluacion(idEvaluacion) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SELECT '✓ Tablas creadas correctamente' AS Estado;

-- ====================================================================
-- PASO 3: INSERTAR DATOS DE PRUEBA
-- ====================================================================

-- Docentes
INSERT INTO usuario (nombre, email, contrasenia, especialidad, tipo) VALUES
('Carlos Fernández', 'carlos.fernandez@ejemplo.com', 'pass123', 'Programación', 'DOCENTE'),
('María González', 'maria.gonzalez@ejemplo.com', 'pass123', 'Diseño Web', 'DOCENTE'),
('Juan Pérez', 'juan.perez@ejemplo.com', 'pass123', 'Base de Datos', 'DOCENTE');

-- Alumnos
INSERT INTO usuario (nombre, email, contrasenia, fecha_registro, tipo) VALUES
('Ana López', 'ana.lopez@ejemplo.com', 'pass123', CURDATE(), 'ALUMNO'),
('Pedro Martínez', 'pedro.martinez@ejemplo.com', 'pass123', CURDATE(), 'ALUMNO'),
('Laura Rodríguez', 'laura.rodriguez@ejemplo.com', 'pass123', CURDATE(), 'ALUMNO'),
('Diego Sánchez', 'diego.sanchez@ejemplo.com', 'pass123', CURDATE(), 'ALUMNO'),
('Sofía García', 'sofia.garcia@ejemplo.com', 'pass123', CURDATE(), 'ALUMNO');

-- Cursos
INSERT INTO curso (nombre, descripcion, cupo, precio, estado, fecha_inicio, fecha_fin, modalidad, link_plataforma, plataforma, aula, direccion, idDocente) VALUES
('Introducción a Java', 'Aprende los fundamentos de programación en Java', 30, 15000.00, 'publicado', '2025-01-15', '2025-03-15', 'Online', 'https://zoom.us/j/123456789', 'Zoom', NULL, NULL, 1),
('Diseño Web Moderno', 'HTML, CSS y JavaScript desde cero', 25, 12000.00, 'publicado', '2025-02-01', '2025-04-01', 'Online', 'https://meet.google.com/abc-defg-hij', 'Google Meet', NULL, NULL, 2),
('Python Avanzado', 'Programación avanzada con Python', 20, 18000.00, 'inactivo', '2025-03-01', '2025-05-01', 'Online', 'https://teams.microsoft.com/l/meetup-join', 'Microsoft Teams', NULL, NULL, 1),
('Base de Datos SQL', 'Diseño y gestión de bases de datos relacionales', 15, 20000.00, 'publicado', '2025-01-20', '2025-04-20', 'Presencial', NULL, NULL, 'Aula 101', 'Av. Siempre Viva 123, CABA', 3),
('Desarrollo Full Stack', 'Desarrollo web completo con React y Node.js', 20, 25000.00, 'inactivo', '2025-02-15', '2025-06-15', 'Presencial', NULL, NULL, 'Aula 202', 'Calle Falsa 456, CABA', 1);

-- Módulos
INSERT INTO modulo (idCurso, titulo, contenido) VALUES
(1, 'Fundamentos de Java', 'Variables, tipos de datos, operadores y estructuras de control'),
(1, 'Programación Orientada a Objetos', 'Clases, objetos, herencia, polimorfismo y encapsulamiento'),
(1, 'Colecciones y Excepciones', 'ArrayList, HashMap, manejo de excepciones con try-catch'),
(2, 'HTML5 y Semántica', 'Estructura de documentos HTML, etiquetas semánticas'),
(2, 'CSS3 y Flexbox', 'Estilos, selectores, diseño responsive con Flexbox y Grid'),
(2, 'JavaScript Básico', 'Variables, funciones, DOM y eventos'),
(4, 'Introducción a SQL', 'Comandos básicos: SELECT, INSERT, UPDATE, DELETE'),
(4, 'Diseño de Bases de Datos', 'Normalización, claves primarias y foráneas'),
(4, 'Consultas Avanzadas', 'JOINs, subconsultas y procedimientos almacenados');

-- Evaluaciones
INSERT INTO evaluacion (idModulo, nombre, descripcion, nota_maxima) VALUES
(1, 'Quiz Fundamentos', 'Evaluación sobre variables y estructuras de control', 10.00),
(2, 'Proyecto POO', 'Implementar un sistema con clases y herencia', 10.00),
(3, 'Examen Final Java', 'Evaluación integral del curso', 10.00),
(4, 'Práctica HTML', 'Crear una página web semántica', 10.00),
(5, 'Proyecto CSS', 'Diseñar un sitio responsive', 10.00),
(6, 'Aplicación JavaScript', 'Crear una aplicación interactiva', 10.00),
(7, 'Consultas Básicas', 'Ejercicios con SELECT, INSERT, UPDATE', 10.00),
(8, 'Diseño de BD', 'Diseñar un modelo entidad-relación', 10.00),
(9, 'Examen Final SQL', 'Evaluación integral de SQL', 10.00);

-- Inscripciones
INSERT INTO inscripcion (idUsuario, idCurso, fecha, estado) VALUES
(4, 1, '2025-01-10', 'aceptada'),
(4, 2, '2025-01-28', 'aceptada'),
(5, 1, '2025-01-11', 'aceptada'),
(5, 4, '2025-01-18', 'aceptada'),
(6, 2, '2025-01-29', 'aceptada'),
(7, 4, '2025-01-19', 'aceptada'),
(8, 1, '2025-01-12', 'pendiente');

-- Pagos
INSERT INTO pago (idInscripcion, monto, metodo_pago, cuotas, fecha_pago) VALUES
(1, 15000.00, 'Tarjeta de Crédito', 3, '2025-01-10'),
(2, 12000.00, 'Efectivo', 1, '2025-01-28'),
(3, 15000.00, 'Tarjeta de Débito', 1, '2025-01-11'),
(4, 20000.00, 'Transferencia', 2, '2025-01-18'),
(5, 12000.00, 'Tarjeta de Crédito', 6, '2025-01-29'),
(6, 20000.00, 'Efectivo', 1, '2025-01-19');

-- Calificaciones
INSERT INTO calificacion (idUsuario, idCurso, idEvaluacion, nota, comentario, fecha_calificacion) VALUES
(4, 1, 1, 9.50, 'Excelente comprensión de los fundamentos', '2025-01-25'),
(4, 1, 2, 8.75, 'Buen trabajo en el proyecto POO', '2025-02-15'),
(5, 1, 1, 7.80, 'Buen desempeño, puede mejorar', '2025-01-26'),
(5, 4, 7, 9.00, 'Excelentes consultas SQL', '2025-02-05'),
(6, 2, 4, 8.50, 'Muy buena estructura HTML', '2025-02-10'),
(7, 4, 7, 9.50, 'Dominio destacado de SQL básico', '2025-02-06'),
(7, 4, 8, 8.80, 'Buen diseño de base de datos', '2025-02-20');

SELECT '✓ Datos de prueba insertados correctamente' AS Estado;

-- ====================================================================
-- VERIFICACIÓN FINAL
-- ====================================================================

SELECT '=====================================' AS '';
SELECT 'RESUMEN DE LA INSTALACIÓN' AS '';
SELECT '=====================================' AS '';
SELECT COUNT(*) AS 'Total Usuarios' FROM usuario;
SELECT COUNT(*) AS 'Total Cursos' FROM curso;
SELECT COUNT(*) AS 'Total Inscripciones' FROM inscripcion;
SELECT COUNT(*) AS 'Total Pagos' FROM pago;
SELECT COUNT(*) AS 'Total Módulos' FROM modulo;
SELECT COUNT(*) AS 'Total Evaluaciones' FROM evaluacion;
SELECT COUNT(*) AS 'Total Calificaciones' FROM calificacion;
SELECT '=====================================' AS '';
SELECT '✓ INSTALACIÓN COMPLETADA CON ÉXITO' AS Estado;
SELECT '=====================================' AS '';
