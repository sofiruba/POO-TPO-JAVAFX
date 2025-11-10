-- ====================================================================
-- Script SQL con datos de prueba para Plataforma de Cursos
-- Compatible con XAMPP/MySQL
-- ====================================================================

USE plataforma_cursos;

-- ====================================================================
-- INSERTAR USUARIOS DE PRUEBA
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

-- ====================================================================
-- INSERTAR CURSOS DE PRUEBA
-- ====================================================================

-- Cursos Online
INSERT INTO curso (nombre, descripcion, cupo, precio, estado, fecha_inicio, fecha_fin, modalidad, link_plataforma, plataforma, aula, direccion, idDocente) VALUES
('Introducción a Java', 'Aprende los fundamentos de programación en Java', 30, 15000.00, 'publicado', '2025-01-15', '2025-03-15', 'Online', 'https://zoom.us/j/123456789', 'Zoom', NULL, NULL, 1),
('Diseño Web Moderno', 'HTML, CSS y JavaScript desde cero', 25, 12000.00, 'publicado', '2025-02-01', '2025-04-01', 'Online', 'https://meet.google.com/abc-defg-hij', 'Google Meet', NULL, NULL, 2),
('Python Avanzado', 'Programación avanzada con Python', 20, 18000.00, 'inactivo', '2025-03-01', '2025-05-01', 'Online', 'https://teams.microsoft.com/l/meetup-join', 'Microsoft Teams', NULL, NULL, 1);

-- Cursos Presenciales
INSERT INTO curso (nombre, descripcion, cupo, precio, estado, fecha_inicio, fecha_fin, modalidad, link_plataforma, plataforma, aula, direccion, idDocente) VALUES
('Base de Datos SQL', 'Diseño y gestión de bases de datos relacionales', 15, 20000.00, 'publicado', '2025-01-20', '2025-04-20', 'Presencial', NULL, NULL, 'Aula 101', 'Av. Siempre Viva 123, CABA', 3),
('Desarrollo Full Stack', 'Desarrollo web completo con React y Node.js', 20, 25000.00, 'inactivo', '2025-02-15', '2025-06-15', 'Presencial', NULL, NULL, 'Aula 202', 'Calle Falsa 456, CABA', 1);

-- ====================================================================
-- INSERTAR MÓDULOS DE PRUEBA
-- ====================================================================

-- Módulos para "Introducción a Java" (idCurso = 1)
INSERT INTO modulo (idCurso, titulo, contenido) VALUES
(1, 'Fundamentos de Java', 'Variables, tipos de datos, operadores y estructuras de control'),
(1, 'Programación Orientada a Objetos', 'Clases, objetos, herencia, polimorfismo y encapsulamiento'),
(1, 'Colecciones y Excepciones', 'ArrayList, HashMap, manejo de excepciones con try-catch');

-- Módulos para "Diseño Web Moderno" (idCurso = 2)
INSERT INTO modulo (idCurso, titulo, contenido) VALUES
(2, 'HTML5 y Semántica', 'Estructura de documentos HTML, etiquetas semánticas'),
(2, 'CSS3 y Flexbox', 'Estilos, selectores, diseño responsive con Flexbox y Grid'),
(2, 'JavaScript Básico', 'Variables, funciones, DOM y eventos');

-- Módulos para "Base de Datos SQL" (idCurso = 4)
INSERT INTO modulo (idCurso, titulo, contenido) VALUES
(4, 'Introducción a SQL', 'Comandos básicos: SELECT, INSERT, UPDATE, DELETE'),
(4, 'Diseño de Bases de Datos', 'Normalización, claves primarias y foráneas'),
(4, 'Consultas Avanzadas', 'JOINs, subconsultas y procedimientos almacenados');

-- ====================================================================
-- INSERTAR EVALUACIONES DE PRUEBA
-- ====================================================================

-- Evaluaciones para módulos de "Introducción a Java"
INSERT INTO evaluacion (idModulo, nombre, descripcion, nota_maxima) VALUES
(1, 'Quiz Fundamentos', 'Evaluación sobre variables y estructuras de control', 10.00),
(2, 'Proyecto POO', 'Implementar un sistema con clases y herencia', 10.00),
(3, 'Examen Final Java', 'Evaluación integral del curso', 10.00);

-- Evaluaciones para módulos de "Diseño Web Moderno"
INSERT INTO evaluacion (idModulo, nombre, descripcion, nota_maxima) VALUES
(4, 'Práctica HTML', 'Crear una página web semántica', 10.00),
(5, 'Proyecto CSS', 'Diseñar un sitio responsive', 10.00),
(6, 'Aplicación JavaScript', 'Crear una aplicación interactiva', 10.00);

-- Evaluaciones para módulos de "Base de Datos SQL"
INSERT INTO evaluacion (idModulo, nombre, descripcion, nota_maxima) VALUES
(7, 'Consultas Básicas', 'Ejercicios con SELECT, INSERT, UPDATE', 10.00),
(8, 'Diseño de BD', 'Diseñar un modelo entidad-relación', 10.00),
(9, 'Examen Final SQL', 'Evaluación integral de SQL', 10.00);

-- ====================================================================
-- INSERTAR INSCRIPCIONES DE PRUEBA
-- ====================================================================

INSERT INTO inscripcion (idUsuario, idCurso, fecha, estado) VALUES
-- Ana López inscrita en Java y Diseño Web
(4, 1, '2025-01-10', 'aceptada'),
(4, 2, '2025-01-28', 'aceptada'),

-- Pedro Martínez inscrito en Java y SQL
(5, 1, '2025-01-11', 'aceptada'),
(5, 4, '2025-01-18', 'aceptada'),

-- Laura Rodríguez inscrita en Diseño Web
(6, 2, '2025-01-29', 'aceptada'),

-- Diego Sánchez inscrito en SQL
(7, 4, '2025-01-19', 'aceptada'),

-- Sofía García inscrita en Java (pendiente)
(8, 1, '2025-01-12', 'pendiente');

-- ====================================================================
-- INSERTAR PAGOS DE PRUEBA
-- ====================================================================

INSERT INTO pago (idInscripcion, monto, metodo_pago, cuotas, fecha_pago) VALUES
(1, 15000.00, 'Tarjeta de Crédito', 3, '2025-01-10'),
(2, 12000.00, 'Efectivo', 1, '2025-01-28'),
(3, 15000.00, 'Tarjeta de Débito', 1, '2025-01-11'),
(4, 20000.00, 'Transferencia', 2, '2025-01-18'),
(5, 12000.00, 'Tarjeta de Crédito', 6, '2025-01-29'),
(6, 20000.00, 'Efectivo', 1, '2025-01-19');

-- ====================================================================
-- INSERTAR CALIFICACIONES DE PRUEBA
-- ====================================================================

INSERT INTO calificacion (idUsuario, idCurso, idEvaluacion, nota, comentario, fecha_calificacion) VALUES
-- Calificaciones de Ana López
(4, 1, 1, 9.50, 'Excelente comprensión de los fundamentos', '2025-01-25'),
(4, 1, 2, 8.75, 'Buen trabajo en el proyecto POO', '2025-02-15'),

-- Calificaciones de Pedro Martínez
(5, 1, 1, 7.80, 'Buen desempeño, puede mejorar', '2025-01-26'),
(5, 4, 7, 9.00, 'Excelentes consultas SQL', '2025-02-05'),

-- Calificaciones de Laura Rodríguez
(6, 2, 4, 8.50, 'Muy buena estructura HTML', '2025-02-10'),

-- Calificaciones de Diego Sánchez
(7, 4, 7, 9.50, 'Dominio destacado de SQL básico', '2025-02-06'),
(7, 4, 8, 8.80, 'Buen diseño de base de datos', '2025-02-20');

-- Mostrar mensaje de confirmación
SELECT 'Datos de prueba insertados exitosamente' AS Mensaje;
