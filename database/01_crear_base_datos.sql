-- ====================================================================
-- Script SQL para crear la base de datos de Plataforma de Cursos
-- Compatible con XAMPP/MySQL
-- ====================================================================

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS plataforma_cursos 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Usar la base de datos
USE plataforma_cursos;

-- Mostrar mensaje de confirmación
SELECT 'Base de datos plataforma_cursos creada exitosamente' AS Mensaje;
