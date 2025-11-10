-- ====================================================================
-- Script SQL para crear las tablas de Plataforma de Cursos
-- Compatible con XAMPP/MySQL
-- ====================================================================

USE plataforma_cursos;

-- ====================================================================
-- 1. TABLA USUARIO (Alumnos y Docentes)
-- ====================================================================
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

-- ====================================================================
-- 2. TABLA CURSO
-- ====================================================================
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
    
    -- Campos para cursos online
    link_plataforma VARCHAR(255),
    plataforma VARCHAR(100),
    
    -- Campos para cursos presenciales
    aula VARCHAR(100),
    direccion VARCHAR(255),
    
    -- Relación con docente
    idDocente INT NOT NULL,
    
    INDEX idx_modalidad (modalidad),
    INDEX idx_estado (estado),
    INDEX idx_docente (idDocente),
    FOREIGN KEY (idDocente) REFERENCES usuario(idUsuario) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================================
-- 3. TABLA INSCRIPCION
-- ====================================================================
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

-- ====================================================================
-- 4. TABLA PAGO
-- ====================================================================
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

-- ====================================================================
-- 5. TABLA MODULO
-- ====================================================================
CREATE TABLE IF NOT EXISTS modulo (
    idModulo INT AUTO_INCREMENT PRIMARY KEY,
    idCurso INT NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    contenido TEXT,
    
    INDEX idx_curso (idCurso),
    FOREIGN KEY (idCurso) REFERENCES curso(idCurso) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================================
-- 6. TABLA EVALUACION
-- ====================================================================
CREATE TABLE IF NOT EXISTS evaluacion (
    idEvaluacion INT AUTO_INCREMENT PRIMARY KEY,
    idModulo INT NOT NULL,
    nombre VARCHAR(200) NOT NULL,
    descripcion TEXT,
    nota_maxima DECIMAL(5,2) NOT NULL DEFAULT 10.00,
    
    INDEX idx_modulo (idModulo),
    FOREIGN KEY (idModulo) REFERENCES modulo(idModulo) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ====================================================================
-- 7. TABLA CALIFICACION
-- ====================================================================
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

-- Mostrar mensaje de confirmación
SELECT 'Todas las tablas han sido creadas exitosamente' AS Mensaje;
