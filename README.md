# 🎓 Plataforma de Cursos - Sistema de Gestión Educativa

Sistema de gestión de cursos desarrollado en Java con JavaFX para la interfaz gráfica y MySQL para la persistencia de datos.

## 📋 Requisitos Previos

- **Java JDK 11+** instalado
- **JavaFX SDK 21.0.9** (incluido en el proyecto)
- **MySQL Server** (XAMPP o instalación standalone)
- **Windows PowerShell** o **CMD**

## 🗄️ Configuración de la Base de Datos

### Paso 1: Iniciar MySQL
Inicia XAMPP y asegúrate de que MySQL esté corriendo en el puerto `3306`.

### Paso 2: Importar la Base de Datos
Existen dos opciones para crear la base de datos:

**Opción A - Script Completo:**
```bash
mysql -u root -p < database/script_completo.sql
```

**Opción B - Scripts Individuales:**
```bash
mysql -u root -p < database/01_crear_base_datos.sql
mysql -u root -p < database/02_crear_tablas.sql
mysql -u root -p < database/03_datos_prueba.sql
```

### Paso 3: Verificar Credenciales
Asegúrate de que las credenciales en `src/data/GestorBDDUsuario.java` coincidan con tu configuración:

```java
private static final String URL = "jdbc:mysql://localhost:3306/plataforma_cursos";
private static final String USER = "root";
private static final String PASSWORD = "mysql"; // Cambia si es necesario
```

## 🚀 Ejecución de la Aplicación

### Método 1: Usando run.bat (Recomendado)
```bash
run.bat
```

Este script automáticamente:
1. Compila todos los archivos fuente
2. Copia los recursos FXML y CSS
3. Ejecuta la aplicación con JavaFX

### Método 2: Usando PowerShell

**Compilación:**
```powershell
javac --module-path javafx-sdk-21.0.9\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -encoding UTF-8 -cp "lib\*" -d bin src\vista\MainApplication.java src\vista\controladores\*.java src\controller\*.java src\data\*.java src\modelos\usuario\*.java src\modelos\cursos\*.java src\modelos\inscripcion\*.java src\modelos\pago\*.java src\exception\*.java src\modelos\ConstantesDeNegocio.java
```

**Copiar Recursos:**
```powershell
Copy-Item src\vista\fxml\*.fxml bin\vista\fxml\
Copy-Item src\vista\css\*.css bin\vista\css\
```

**Ejecución:**
```powershell
java --module-path javafx-sdk-21.0.9\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics -cp "bin;lib\*" vista.MainApplication
```

## 👥 Usuarios de Prueba

### Docentes:
| Email | Contraseña | Especialidad |
|-------|-----------|--------------|
| carlos.fernandez@ejemplo.com | pass123 | Programación |
| maria.lopez@ejemplo.com | pass123 | Bases de Datos |
| juan.perez@ejemplo.com | pass123 | Desarrollo Web |

### Alumnos:
| Email | Contraseña | Nombre |
|-------|-----------|--------|
| ana.garcia@estudiante.com | pass123 | Ana García |
| pedro.martinez@estudiante.com | pass123 | Pedro Martínez |
| lucia.rodriguez@estudiante.com | pass123 | Lucía Rodríguez |
| diego.sanchez@estudiante.com | pass123 | Diego Sánchez |
| sofia.torres@estudiante.com | pass123 | Sofía Torres |

## 🎯 Funcionalidades

### Para Alumnos:
- ✅ Registro e inicio de sesión
- 📚 Explorar cursos disponibles
- 📝 Inscribirse en cursos (preinscripción)
- 💳 Realizar pagos (efectivo o servicio)
- 📊 Ver calificaciones
- 🎓 Consultar cursos inscritos

### Para Docentes:
- ✅ Inicio de sesión
- 📚 Ver cursos asignados
- 👥 Consultar alumnos inscritos
- 📊 Gestionar evaluaciones
- ✏️ Asignar calificaciones

## 📁 Estructura del Proyecto

```
POO-Trabajo-Practico-Integrador/
├── bin/                          # Archivos compilados
├── database/                     # Scripts SQL
│   ├── 01_crear_base_datos.sql
│   ├── 02_crear_tablas.sql
│   ├── 03_datos_prueba.sql
│   └── script_completo.sql
├── javafx-sdk-21.0.9/           # JavaFX SDK
├── lib/                         # Librerías externas
│   └── mysql-connector-j-8.0.33.jar
├── src/
│   ├── vista/                   # Capa de presentación
│   │   ├── MainApplication.java
│   │   ├── controladores/       # Controladores FXML
│   │   ├── fxml/               # Archivos FXML
│   │   └── css/                # Estilos CSS
│   ├── controller/             # Lógica de negocio
│   ├── data/                   # Gestores de base de datos
│   ├── modelos/                # Modelos de dominio
│   └── exception/              # Excepciones personalizadas
└── run.bat                     # Script de ejecución
```

## 🎨 Tecnologías Utilizadas

- **Java 11+**: Lenguaje de programación principal
- **JavaFX 21.0.9**: Framework para interfaz gráfica
- **FXML**: Declaración de interfaces
- **CSS**: Estilos de la aplicación
- **MySQL 8.0**: Base de datos relacional
- **JDBC**: Conectividad con base de datos

## 🔧 Solución de Problemas

### Error: "No suitable driver found for jdbc:mysql"
**Solución:** Asegúrate de que `mysql-connector-j-8.0.33.jar` esté en la carpeta `lib/`.

### Error: "javafx.* cannot be resolved"
**Solución:** Verifica que `javafx-sdk-21.0.9` esté en la raíz del proyecto.

### Error: "Access denied for user 'root'"
**Solución:** Verifica las credenciales de MySQL en los archivos `GestorBDD*.java`.

### La aplicación no muestra datos
**Solución:** 
1. Verifica que MySQL esté corriendo
2. Comprueba que la base de datos `plataforma_cursos` exista
3. Asegúrate de haber ejecutado los scripts de datos de prueba

## 📝 Notas Adicionales

- El sistema soporta cursos **presenciales** y **online**
- Los pagos pueden ser en **efectivo** o mediante **servicios** (MercadoPago, PayPal)
- Las inscripciones requieren confirmación mediante pago
- Los cursos tienen límite de cupos

## 👨‍💻 Desarrollo

Para modificar la interfaz gráfica, edita los archivos FXML en `src/vista/fxml/` y los estilos en `src/vista/css/styles.css`.

Para modificar la lógica de negocio, trabaja en los controladores en `src/controller/`.

Para cambios en la base de datos, modifica los gestores en `src/data/`.

---

**Desarrollado con ❤️ para el curso de POO**
