# Base de Datos - Plataforma de Cursos

## 📋 Descripción
Scripts SQL para crear y poblar la base de datos de la plataforma de gestión de cursos en MySQL/XAMPP.

## 🗂️ Estructura de Archivos

1. **01_crear_base_datos.sql** - Crea la base de datos `plataforma_cursos`
2. **02_crear_tablas.sql** - Crea todas las tablas del sistema
3. **03_datos_prueba.sql** - Inserta datos de ejemplo para testing

## 🚀 Instalación en XAMPP

### Opción 1: Usando phpMyAdmin (Recomendado para principiantes)

1. Inicia XAMPP y arranca los servicios **Apache** y **MySQL**
2. Abre tu navegador y ve a: `http://localhost/phpmyadmin`
3. Haz clic en la pestaña **"SQL"** en el menú superior
4. Copia y pega el contenido de **01_crear_base_datos.sql** y ejecuta
5. Repite el paso 4 con **02_crear_tablas.sql**
6. Repite el paso 4 con **03_datos_prueba.sql**

### Opción 2: Desde la línea de comandos

```bash
# Navega a la carpeta de MySQL de XAMPP
cd C:\xampp\mysql\bin

# Ejecuta los scripts en orden
mysql -u root -p < "C:\Users\mfern\Music\POO-Trabajo-Practico-Integrador\database\01_crear_base_datos.sql"
mysql -u root -p < "C:\Users\mfern\Music\POO-Trabajo-Practico-Integrador\database\02_crear_tablas.sql"
mysql -u root -p < "C:\Users\mfern\Music\POO-Trabajo-Practico-Integrador\database\03_datos_prueba.sql"
```

### Opción 3: Ejecutar todo junto

Puedes ejecutar todos los scripts de una vez:

```bash
cd C:\xampp\mysql\bin
mysql -u root -p < "C:\Users\mfern\Music\POO-Trabajo-Practico-Integrador\database\script_completo.sql"
```

## 🗃️ Estructura de la Base de Datos

### Tablas Principales

#### 1. **usuario**
- Almacena tanto alumnos como docentes
- Campo `tipo`: 'ALUMNO' o 'DOCENTE'
- Campos específicos de docente: `especialidad`

#### 2. **curso**
- Información de cursos (online y presenciales)
- Modalidades: 'Online' o 'Presencial'
- Campos específicos por modalidad:
  - Online: `link_plataforma`, `plataforma`
  - Presencial: `aula`, `direccion`

#### 3. **inscripcion**
- Relaciona alumnos con cursos
- Estados: 'pendiente', 'aceptada', 'rechazada'

#### 4. **pago**
- Registra pagos de inscripciones
- Soporta diferentes métodos y cuotas

#### 5. **modulo**
- Contenido del curso dividido en módulos

#### 6. **evaluacion**
- Evaluaciones asociadas a cada módulo

#### 7. **calificacion**
- Notas de alumnos en evaluaciones

## 📊 Diagrama de Relaciones

```
usuario (1) ----< (N) curso [como docente]
usuario (1) ----< (N) inscripcion [como alumno]
curso (1) ----< (N) inscripcion
curso (1) ----< (N) modulo
modulo (1) ----< (N) evaluacion
inscripcion (1) ----< (N) pago
evaluacion (1) ----< (N) calificacion
usuario (1) ----< (N) calificacion [como alumno]
```

## 🔑 Credenciales por Defecto

- **Usuario:** root
- **Contraseña:** mysql (configurable en XAMPP)
- **Base de datos:** plataforma_cursos
- **Puerto:** 3306

## ⚠️ Importante

- Asegúrate de que XAMPP esté corriendo antes de ejecutar los scripts
- La contraseña por defecto en el código Java es `mysql` (puede variar según tu configuración de XAMPP)
- Si tu XAMPP usa otra contraseña, actualiza el archivo `GestorDePersistencia.java` o los gestores BDD

## 🧪 Datos de Prueba

El script incluye:
- **3 docentes** con diferentes especialidades
- **5 alumnos** registrados
- **5 cursos** (3 online, 2 presenciales)
- **9 módulos** distribuidos en los cursos
- **9 evaluaciones** para los módulos
- **7 inscripciones** (6 aceptadas, 1 pendiente)
- **6 pagos** procesados
- **7 calificaciones** de ejemplo

## 🔧 Verificación

Para verificar que todo se instaló correctamente, ejecuta en phpMyAdmin:

```sql
USE plataforma_cursos;
SHOW TABLES;
SELECT COUNT(*) FROM usuario;
SELECT COUNT(*) FROM curso;
```

Deberías ver 7 tablas y múltiples registros en cada una.

## 📝 Notas Adicionales

- El motor de almacenamiento es **InnoDB** para soportar transacciones y claves foráneas
- El charset es **utf8mb4** para soportar emojis y caracteres especiales
- Todas las tablas tienen **AUTO_INCREMENT** en las claves primarias
- Se incluyen **índices** para optimizar las consultas frecuentes
