# 🎓 Plataforma de Cursos - Aplicación JavaFX

## 📋 Descripción
Sistema de gestión de cursos con interfaz gráfica desarrollado en JavaFX 21.0.9. Permite la gestión completa de cursos online y presenciales, inscripciones, pagos y calificaciones.

## ✨ Características

### 🔐 Sistema de Autenticación
- Login con email y contraseña
- Registro de nuevos alumnos
- Sesiones diferenciadas por rol (Alumno/Docente)

### 👨‍🎓 Vista de Alumno
- **Cursos Disponibles**: Explorar y inscribirse en cursos
- **Mis Cursos**: Ver cursos en los que está inscrito
- **Mis Calificaciones**: Consultar notas y comentarios
- **Sistema de Pago**: Procesar pagos con diferentes métodos (Efectivo, Tarjeta, Transferencia)

### 👨‍🏫 Vista de Docente
- **Mis Cursos**: Gestionar cursos creados
- **Crear Curso**: Diseñar nuevos cursos (Online/Presencial)
- **Calificar Alumnos**: Registrar evaluaciones y notas
- **Ver Alumnos Inscritos**: Consultar lista de estudiantes por curso

## 🚀 Requisitos

- **Java JDK 11 o superior**
- **JavaFX SDK 21.0.9** (incluido en la carpeta `javafx-sdk-21.0.9`)
- **MySQL/XAMPP** con la base de datos configurada
- **Windows** (el script `run.bat` está configurado para Windows)

## 📦 Instalación

### 1. Configurar la Base de Datos

```bash
# Cargar los scripts SQL en MySQL a través de phpMyAdmin o línea de comandos
cd database
# Ejecutar en orden:
# - 01_crear_base_datos.sql
# - 02_crear_tablas.sql
# - 03_datos_prueba.sql
# O simplemente ejecutar:
# - script_completo.sql
```

### 2. Verificar JavaFX

El SDK de JavaFX ya está incluido en la carpeta `javafx-sdk-21.0.9`. No es necesario descargarlo.

### 3. Compilar y Ejecutar

**Opción 1: Usar el script batch (Recomendado)**

```bash
# Desde la carpeta raíz del proyecto
run.bat
```

**Opción 2: Línea de comandos manual**

```powershell
# Compilar
javac --module-path javafx-sdk-21.0.9\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -d bin -cp src -sourcepath src src\AppPlataformaCursos.java src\vista\*.java src\controller\*.java src\data\*.java src\modelos\usuario\*.java src\modelos\cursos\*.java src\modelos\inscripcion\*.java src\modelos\pago\*.java src\exception\*.java

# Ejecutar
java --module-path javafx-sdk-21.0.9\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp bin;src AppPlataformaCursos
```

**Opción 3: Desde Eclipse/IntelliJ IDEA**

1. Importar el proyecto
2. Agregar JavaFX SDK como librería externa:
   - Ruta: `javafx-sdk-21.0.9/lib`
   - Agregar todos los archivos `.jar`
3. Configurar VM Arguments:
   ```
   --module-path "javafx-sdk-21.0.9/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics
   ```
4. Ejecutar `AppPlataformaCursos.java`

## 🎯 Uso de la Aplicación

### Iniciar Sesión

La aplicación inicia con la pantalla de login. Puedes usar estas credenciales de prueba:

**Alumnos:**
- Email: `ana.lopez@ejemplo.com` - Contraseña: `pass123`
- Email: `pedro.martinez@ejemplo.com` - Contraseña: `pass123`
- Email: `laura.rodriguez@ejemplo.com` - Contraseña: `pass123`

**Docentes:**
- Email: `carlos.fernandez@ejemplo.com` - Contraseña: `pass123`
- Email: `maria.gonzalez@ejemplo.com` - Contraseña: `pass123`
- Email: `juan.perez@ejemplo.com` - Contraseña: `pass123`

### Registrar Nueva Cuenta

1. Hacer clic en "Regístrate aquí"
2. Completar el formulario con tus datos
3. Hacer clic en "Registrarse"
4. Iniciar sesión con tu nuevo usuario

### Funcionalidades por Rol

#### Como Alumno:
1. **Ver Cursos Disponibles** → Explorar catálogo
2. **Inscribirse en un Curso** → Botón "Inscribirse" → Completar pago
3. **Ver Mis Cursos** → Consultar cursos inscritos
4. **Ver Mis Calificaciones** → Tabla con notas y comentarios

#### Como Docente:
1. **Crear Curso** → Completar formulario → Elegir modalidad (Online/Presencial)
2. **Ver Mis Cursos** → Gestionar cursos creados
3. **Calificar Alumnos** → Seleccionar curso → Seleccionar alumno → Registrar nota

## 🗂️ Estructura del Proyecto

```
POO-Trabajo-Practico-Integrador/
│
├── src/
│   ├── AppPlataformaCursos.java     # Punto de entrada
│   ├── vista/
│   │   ├── LoginView.java           # Pantalla de login
│   │   ├── RegistroView.java        # Pantalla de registro
│   │   ├── AlumnoView.java          # Dashboard del alumno
│   │   └── DocenteView.java         # Dashboard del docente
│   ├── controller/
│   │   ├── UsuariosController.java  # Lógica de usuarios
│   │   └── CursosController.java    # Lógica de cursos
│   ├── data/
│   │   └── GestorBDD*.java          # Gestores de base de datos
│   ├── modelos/
│   │   ├── usuario/                 # Clases Usuario, Alumno, Docente
│   │   ├── cursos/                  # Clases Curso, Modulo, Evaluacion, etc.
│   │   ├── inscripcion/             # Clase Inscripcion
│   │   └── pago/                    # Clases de pago
│   └── exception/                   # Excepciones personalizadas
│
├── database/
│   ├── 01_crear_base_datos.sql
│   ├── 02_crear_tablas.sql
│   ├── 03_datos_prueba.sql
│   └── script_completo.sql
│
├── javafx-sdk-21.0.9/              # SDK de JavaFX
│
└── run.bat                          # Script de ejecución para Windows
```

## 🎨 Características de la Interfaz

- **Diseño moderno** con gradientes y sombras
- **Colores corporativos**: Púrpura (#667eea, #764ba2)
- **Responsive cards** para mostrar cursos
- **Formularios intuitivos** con validación
- **Navegación lateral** con menú de opciones
- **Efectos hover** en botones y enlaces
- **Alertas y diálogos** para feedback al usuario

## 🔧 Configuración de la Base de Datos

Por defecto, la aplicación se conecta a:
- **Host**: localhost
- **Puerto**: 3306
- **Base de datos**: plataforma_cursos
- **Usuario**: root
- **Contraseña**: mysql

Para cambiar estas credenciales, editar los archivos en `src/data/GestorBDD*.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/plataforma_cursos";
private static final String USER = "root";
private static final String PASSWORD = "mysql";
```

## 🐛 Solución de Problemas

### Error: "javafx cannot be resolved"
- Verificar que la ruta en `run.bat` apunta correctamente a `javafx-sdk-21.0.9\lib`
- Asegurarse de usar el comando completo con `--module-path` y `--add-modules`

### Error de conexión a la base de datos
- Verificar que XAMPP/MySQL esté corriendo
- Comprobar las credenciales en los archivos GestorBDD
- Asegurarse de que la base de datos `plataforma_cursos` existe

### La ventana no se muestra
- Verificar que Java JDK esté instalado correctamente
- Comprobar que no hay conflictos de versión de Java
- Revisar los logs en la consola para más detalles

## 📝 Notas Adicionales

- Los datos de prueba incluyen 5 cursos, 8 usuarios y varias inscripciones
- Las contraseñas de prueba son simples (`pass123`) - en producción usar hash
- El sistema soporta cursos online (con link y plataforma) y presenciales (con aula y dirección)
- Los pagos pueden ser en efectivo, tarjeta o transferencia, con soporte para cuotas

## 👥 Contribuidores

Proyecto desarrollado como Trabajo Práctico Integrador de Programación Orientada a Objetos.

## 📄 Licencia

Este proyecto es de uso académico.
