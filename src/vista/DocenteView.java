package vista;

import controller.CursosController;
import controller.UsuariosController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import modelos.cursos.*;
import modelos.usuario.Alumno;
import modelos.usuario.Docente;
import modelos.pago.PagoServicioImp;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Vista principal para docentes
 */
public class DocenteView {
    
    private Stage stage;
    private Docente docente;
    private CursosController cursosController;
    private UsuariosController usuariosController;
    
    public DocenteView(Stage stage, Docente docente, UsuariosController usuariosController, CursosController cursosController) {
        this.stage = stage;
        this.docente = docente;
        this.usuariosController = new UsuariosController();
        this.cursosController = cursosController;
        this.usuariosController = usuariosController;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        HBox topBar = createTopBar();
        root.setTop(topBar);
        
        VBox sideBar = createSideBar();
        root.setLeft(sideBar);
        
        VBox mainContent = createMisCursosView();
        root.setCenter(mainContent);
        
        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
    }
    
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setStyle("-fx-background-color: #764ba2;");
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
        
        Label titleLabel = new Label("👨‍🏫 Panel de Docente");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITE);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label userLabel = new Label("👤 " + docente.getNombre());
        userLabel.setFont(Font.font("System", FontWeight.NORMAL, 14));
        userLabel.setTextFill(Color.WHITE);
        
        Button logoutButton = new Button("Cerrar Sesión");
        logoutButton.setStyle(
            "-fx-background-color: rgba(255,255,255,0.2); " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8 15; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        
        logoutButton.setOnAction(e -> {
            LoginView loginView = new LoginView(stage, usuariosController, cursosController);
            loginView.show();
        });
        Button saveFileButton = new Button("💾 Guardar Cursos (Archivo)");
        saveFileButton.setOnAction(e -> handleGuardarEnArchivo());
        saveFileButton.setStyle(
                "-fx-background-color: #38b000; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 8 15; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand;"
        );
        
        topBar.getChildren().addAll(titleLabel, spacer, userLabel, logoutButton, saveFileButton);
        return topBar;
    }
    private void handleGuardarEnArchivo() {

        cursosController.guardarDatosEnArchivo();

        // Muestra una alerta de confirmación
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Exportación Exitosa");
        alert.setHeaderText(null);
        alert.setContentText("✅ ¡Éxito! Los datos de los cursos han sido guardados en el archivo 'cursos.csv'.");
        alert.showAndWait();
    }
    
    private VBox createSideBar() {
        VBox sideBar = new VBox(10);
        sideBar.setPadding(new Insets(20));
        sideBar.setStyle("-fx-background-color: white;");
        sideBar.setPrefWidth(250);
        
        Label menuLabel = new Label("MENÚ");
        menuLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        menuLabel.setTextFill(Color.web("#666666"));
        
        Button misCursosBtn = createMenuButton("📚 Mis Cursos");
        Button crearCursoBtn = createMenuButton("➕ Crear Curso");
        Button calificarBtn = createMenuButton("✏️ Calificar Alumnos");
        
        misCursosBtn.setOnAction(e -> {
            BorderPane root = (BorderPane) stage.getScene().getRoot();
            root.setCenter(createMisCursosView());
        });

        
        crearCursoBtn.setOnAction(e -> {
            BorderPane root = (BorderPane) stage.getScene().getRoot();
            root.setCenter(createCrearCursoView());
        });
        
        calificarBtn.setOnAction(e -> {
            BorderPane root = (BorderPane) stage.getScene().getRoot();
            root.setCenter(createCalificarView());
        });
        
        sideBar.getChildren().addAll(
            menuLabel,
            new Separator(),
            misCursosBtn,
            crearCursoBtn,
            calificarBtn
        );
        
        return sideBar;
    }
    
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-text-fill: #333; " +
            "-fx-font-size: 14px; " +
            "-fx-padding: 12; " +
            "-fx-cursor: hand;"
        );
        
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: #f0f0f0; " +
                "-fx-text-fill: #764ba2; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 12; " +
                "-fx-cursor: hand;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: transparent; " +
                "-fx-text-fill: #333; " +
                "-fx-font-size: 14px; " +
                "-fx-padding: 12; " +
                "-fx-cursor: hand;"
            )
        );
        
        return button;
    }
    
    private VBox createMisCursosView() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        
        Label titleLabel = new Label("📚 Mis Cursos");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        List<Curso> misCursos = cursosController.obtenerCursosDocente(docente);
        
        if (misCursos.isEmpty()) {
            Label noCoursesLabel = new Label("No has creado ningún curso todavía.");
            noCoursesLabel.setTextFill(Color.web("#999999"));
            content.getChildren().addAll(titleLabel, noCoursesLabel);
        } else {
            VBox cursosBox = new VBox(15);
            for (Curso curso : misCursos) {
                VBox cursoCard = createCursoDocenteCard(curso);
                cursosBox.getChildren().add(cursoCard);
            }
            
            ScrollPane scrollPane = new ScrollPane(cursosBox);
            scrollPane.setFitToWidth(true);
            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            
            content.getChildren().addAll(titleLabel, scrollPane);
        }
        
        return content;
    }
    
    private VBox createCursoDocenteCard(Curso curso) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );
        
        Label nombreLabel = new Label(curso.getNombre());
        nombreLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        nombreLabel.setTextFill(Color.web("#764ba2"));
        
        Label descripcionLabel = new Label(curso.getDescripcion());
        descripcionLabel.setWrapText(true);
        
        HBox infoBox = new HBox(20);
        Label cupoLabel = new Label("👥 Cupo total: " + curso.getCupo());


        Button verAlumnosBtn = new Button("Ver Alumnos");
        styleButton(verAlumnosBtn);
        verAlumnosBtn.setOnAction(e -> mostrarAlumnosInscritos(curso));

        Button gestionarContenidoBtn = new Button("Gestionar Contenido");
        styleButton(gestionarContenidoBtn);

        gestionarContenidoBtn.setOnAction(e -> {
            // Cargar la nueva vista de gestión
            BorderPane root = (BorderPane) stage.getScene().getRoot();
            root.setCenter(createGestionContenidoView(curso));
        });

        HBox actionBox = new HBox(10, verAlumnosBtn, gestionarContenidoBtn);

        card.getChildren().addAll(nombreLabel, descripcionLabel, infoBox, actionBox);

        return card;
    }
    // Archivo: DocenteView.java (Agregar este método)

    private VBox createGestionContenidoView(Curso curso) {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        Label titleLabel = new Label("Gestión de Contenido: " + curso.getNombre());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#764ba2"));

        // 1. Cargar Módulos y Evaluaciones
        // Usamos el método de carga que hidrata los módulos con sus evaluaciones
        List<Modulo> modulos = cursosController.obtenerModulosDeCurso(curso);

        // 2. ComboBox para seleccionar el Módulo al que se añadirá la Evaluación
        ComboBox<Modulo> moduloCombo = new ComboBox<>();
        moduloCombo.setPromptText("Seleccionar Módulo para Evaluación");
        moduloCombo.getItems().addAll(modulos);
        moduloCombo.setMaxWidth(400);

        VBox evaluacionForm = createEvaluacionForm(moduloCombo, curso); // Formulario de creación

        // 3. Área de visualización de módulos (listado y estructura)
        VBox estructuraBox = new VBox(15);
        ScrollPane scrollPane = new ScrollPane(estructuraBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        // Mostrar la estructura actual
        if (modulos.isEmpty()) {
            estructuraBox.getChildren().add(new Label("Este curso aún no tiene módulos."));
        } else {
            for (Modulo modulo : modulos) {
                estructuraBox.getChildren().add(createModuloStructureCard(modulo));
            }
        }

        content.getChildren().addAll(titleLabel, moduloCombo, evaluacionForm, new Separator(), new Label("Estructura Actual:"), scrollPane);
        return content;
    }

    // Auxiliar: Crea la tarjeta de la estructura de Módulos/Evaluaciones
    private VBox createModuloStructureCard(Modulo modulo) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #f0f0f0; -fx-background-radius: 5;");

        Label moduloTitle = new Label("📘 MÓDULO: " + modulo.getTitulo());
        moduloTitle.setFont(Font.font("System", FontWeight.BOLD, 14));

        VBox evalsBox = new VBox(3);
        if (modulo.getEvaluaciones().isEmpty()) {
            evalsBox.getChildren().add(new Label("   (No tiene evaluaciones)"));
        } else {
            for (Evaluacion eval : modulo.getEvaluaciones()) {
                evalsBox.getChildren().add(new Label("   📝 " + eval.getNombre() + " (Max: " + eval.getNotaMaxima() + ")"));
            }
        }

        card.getChildren().addAll(moduloTitle, evalsBox);
        return card;
    }

    // Auxiliar: Crea el formulario para añadir una evaluación
    private VBox createEvaluacionForm(ComboBox<Modulo> moduloCombo, Curso curso) {
        VBox form = new VBox(10);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: #764ba2; -fx-border-radius: 10; -fx-border-width: 1px;");

        Label header = new Label("Añadir Nueva Evaluación");
        header.setFont(Font.font("System", FontWeight.BOLD, 16));

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre de la Evaluación");

        TextField notaField = new TextField();
        notaField.setPromptText("Nota Máxima");

        TextArea descripcionArea = new TextArea();
        descripcionArea.setPromptText("Descripción");
        descripcionArea.setPrefRowCount(2);

        Label messageLabel = new Label();

        Button addBtn = new Button("Añadir Evaluación");
        styleButton(addBtn);

        addBtn.setOnAction(e -> {
            try {
                Modulo moduloSeleccionado = moduloCombo.getValue();
                String nombre = nombreField.getText().trim();
                float notaMax = Float.parseFloat(notaField.getText().trim());

                if (moduloSeleccionado == null || nombre.isEmpty()) {
                    throw new IllegalArgumentException("Seleccione un Módulo y complete el nombre.");
                }

                // Llama al controlador para crear y persistir la evaluación
                cursosController.agregarEvaluacion(
                        moduloSeleccionado,
                        nombre,
                        notaMax,
                        descripcionArea.getText().trim()
                );

                messageLabel.setText("✅ Evaluación '" + nombre + "' agregada con éxito.");
                messageLabel.setTextFill(Color.GREEN);

                // Recargar la vista para actualizar la lista de módulos
                BorderPane root = (BorderPane) stage.getScene().getRoot();
                root.setCenter(createGestionContenidoView(curso));

            } catch (NumberFormatException ex) {
                messageLabel.setText("⚠️ Nota máxima debe ser un número válido.");
                messageLabel.setTextFill(Color.RED);
            } catch (IllegalArgumentException ex) {
                messageLabel.setText("⚠️ " + ex.getMessage());
                messageLabel.setTextFill(Color.RED);
            } catch (Exception ex) {
                messageLabel.setText("❌ Error al guardar: " + ex.getMessage());
                messageLabel.setTextFill(Color.RED);
            }
        });

        form.getChildren().addAll(header, moduloCombo, nombreField, notaField, descripcionArea, messageLabel, addBtn);
        return form;
    }


    
    private void mostrarAlumnosInscritos(Curso curso) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alumnos Inscritos");
        alert.setHeaderText("Curso: " + curso.getNombre());
        
        List<Alumno> alumnos = cursosController.obtenerAlumnosInscritosEnCurso(curso);
        
        if (alumnos.isEmpty()) {
            alert.setContentText("No hay alumnos inscritos en este curso.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Alumno alumno : alumnos) {
                sb.append("• ").append(alumno.getNombre())
                  .append(" (").append(alumno.getEmail()).append(")\n");
            }
            alert.setContentText(sb.toString());
        }
        
        alert.showAndWait();
    }
    
    private VBox createCrearCursoView() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        content.setMaxWidth(700);
        
        Label titleLabel = new Label("➕ Crear Nuevo Curso");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        VBox formPanel = new VBox(15);
        formPanel.setPadding(new Insets(20));
        formPanel.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );
        
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre del curso");
        
        TextArea descripcionArea = new TextArea();
        descripcionArea.setPromptText("Descripción del curso");
        descripcionArea.setPrefRowCount(3);
        
        TextField cupoField = new TextField();
        cupoField.setPromptText("Cupo total: (número de alumnos)");
        
        TextField precioField = new TextField();
        precioField.setPromptText("Precio");
        
        ComboBox<String> modalidadCombo = new ComboBox<>();
        modalidadCombo.getItems().addAll("Online", "Presencial");
        modalidadCombo.setPromptText("Seleccionar modalidad");
        
        // Campos específicos de modalidad
        VBox camposEspecificos = new VBox(10);
        
        modalidadCombo.setOnAction(e -> {
            camposEspecificos.getChildren().clear();
            
            if ("Online".equals(modalidadCombo.getValue())) {
                TextField linkField = new TextField();
                linkField.setPromptText("Link de la plataforma");
                linkField.setId("linkField");
                
                TextField plataformaField = new TextField();
                plataformaField.setPromptText("Nombre de la plataforma (Zoom, Meet, etc.)");
                plataformaField.setId("plataformaField");
                
                camposEspecificos.getChildren().addAll(linkField, plataformaField);
            } else if ("Presencial".equals(modalidadCombo.getValue())) {
                TextField aulaField = new TextField();
                aulaField.setPromptText("Aula");
                aulaField.setId("aulaField");
                
                TextField direccionField = new TextField();
                direccionField.setPromptText("Dirección");
                direccionField.setId("direccionField");
                
                camposEspecificos.getChildren().addAll(aulaField, direccionField);
            }
        });
        
        Button crearBtn = new Button("Crear Curso");
        styleButton(crearBtn);
        
        Label messageLabel = new Label();
        messageLabel.setVisible(false);
        
        crearBtn.setOnAction(e -> {
            try {
                String nombre = nombreField.getText().trim();
                String descripcion = descripcionArea.getText().trim();
                int cupo = Integer.parseInt(cupoField.getText().trim());
                float precio = Float.parseFloat(precioField.getText().trim());
                String modalidad = modalidadCombo.getValue();
                
                if (nombre.isEmpty() || descripcion.isEmpty() || modalidad == null) {
                    throw new IllegalArgumentException("Complete todos los campos");
                }
                
                Curso nuevoCurso;
                
                if ("Online".equals(modalidad)) {
                    TextField linkField = (TextField) camposEspecificos.lookup("#linkField");
                    TextField plataformaField = (TextField) camposEspecificos.lookup("#plataformaField");
                    
                    nuevoCurso = new CursoOnline(
                        nombre, descripcion, cupo, precio,
                        linkField.getText().trim(),
                        plataformaField.getText().trim()
                    );
                } else {
                    TextField aulaField = (TextField) camposEspecificos.lookup("#aulaField");
                    TextField direccionField = (TextField) camposEspecificos.lookup("#direccionField");
                    
                    nuevoCurso = new CursoPresencial(
                        nombre, descripcion, cupo, precio,
                        aulaField.getText().trim(),
                        direccionField.getText().trim()
                    );
                }
                
                cursosController.crearCurso(nuevoCurso, docente);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Curso Creado");
                alert.setHeaderText("¡Éxito!");
                alert.setContentText("El curso ha sido creado correctamente.");
                alert.showAndWait();
                
                // Limpiar campos
                nombreField.clear();
                descripcionArea.clear();
                cupoField.clear();
                precioField.clear();
                modalidadCombo.setValue(null);
                camposEspecificos.getChildren().clear();
                
            } catch (NumberFormatException ex) {
                messageLabel.setText("⚠️ Cupo y precio deben ser números válidos");
                messageLabel.setTextFill(Color.RED);
                messageLabel.setVisible(true);
            } catch (Exception ex) {
                messageLabel.setText("⚠️ " + ex.getMessage());
                messageLabel.setTextFill(Color.RED);
                messageLabel.setVisible(true);
            }
        });
        
        formPanel.getChildren().addAll(
            new Label("Nombre:"), nombreField,
            new Label("Descripción:"), descripcionArea,
            new Label("Cupo:"), cupoField,
            new Label("Precio:"), precioField,
            new Label("Modalidad:"), modalidadCombo,
            camposEspecificos,
            messageLabel,
            crearBtn
        );
        
        content.getChildren().addAll(titleLabel, formPanel);
        return content;
    }

    private VBox createCalificarView() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        Label titleLabel = new Label("✏️ Calificar Alumnos");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));

        Label infoLabel = new Label("Selecciona un curso para calificar a los alumnos");
        infoLabel.setTextFill(Color.web("#666666"));

        // Asumimos que cursosController.obtenerCursosDocente(docente) existe
        List<Curso> misCursos = cursosController.obtenerCursosDocente(docente);

        if (misCursos.isEmpty()) {
            Label noCoursesLabel = new Label("No tienes cursos para calificar.");
            content.getChildren().addAll(titleLabel, noCoursesLabel);
            return content;
        }

        ComboBox<Curso> cursosCombo = new ComboBox<>();
        cursosCombo.setPromptText("Seleccionar curso");
        cursosCombo.getItems().addAll(misCursos);
        cursosCombo.setMaxWidth(400);

        // Panel dinámico para el formulario de calificación
        VBox calificacionPanel = new VBox(15);
        calificacionPanel.setVisible(false);

        // ComboBox de Alumno y Evaluación (definidos fuera del setOnAction para persistencia)
        ComboBox<Alumno> alumnoCombo = new ComboBox<>();
        ComboBox<Evaluacion> evaluacionCombo = new ComboBox<>();

        // Etiqueta de mensaje para errores
        Label messageLabel = new Label();
        messageLabel.setVisible(false);

        // Botón de Calificar
        Button calificarBtn = new Button("Registrar Calificación");
        styleButton(calificarBtn);

        // Campos de Nota y Comentario
        TextField notaField = new TextField();
        notaField.setPromptText("Nota (0-10)");
        TextArea comentarioArea = new TextArea();
        comentarioArea.setPromptText("Comentario");
        comentarioArea.setPrefRowCount(2);


        // 💡 ACCIÓN CLAVE: Al seleccionar un CURSO, cargamos ALUMNOS y EVALUACIONES
        cursosCombo.setOnAction(e -> {
            Curso cursoSeleccionado = cursosCombo.getValue();
            if (cursoSeleccionado != null) {
                calificacionPanel.getChildren().clear();
                calificacionPanel.setVisible(true);

                // 1. Cargar ALUMNOS inscritos
                List<Alumno> alumnos = cursosController.obtenerAlumnosInscritos(cursoSeleccionado.getIdCurso());

                List<String> nombres = cursosController.obtenerNombres(alumnos);

                // 2. Cargar TODAS las EVALUACIONES del curso
                List<Modulo> modulos = cursosController.obtenerModulosDeCurso(cursoSeleccionado);
                ObservableList<Evaluacion> evaluacionesData = FXCollections.observableArrayList();

                for (Modulo m : modulos) {
                    // Asumimos que Modulo.getEvaluaciones() ya está hidratado por GestorBDDModulo
                    evaluacionesData.addAll(m.getEvaluaciones());
                }

                if (alumnos.isEmpty() || evaluacionesData.isEmpty()) {
                    String msg = alumnos.isEmpty() ? "No hay alumnos inscritos." : "No hay evaluaciones cargadas.";
                    calificacionPanel.getChildren().add(new Label(msg));
                } else {
                    // 3. Rellenar ComboBoxes
                    alumnoCombo.setItems(FXCollections.observableArrayList(alumnos));
                    alumnoCombo.setPromptText("Seleccionar alumno");

                    evaluacionCombo.setItems(evaluacionesData);
                    evaluacionCombo.setPromptText("Seleccionar evaluación");

                    // 4. Construir Formulario Final
                    calificacionPanel.getChildren().addAll(
                            new Label("Alumno:"), alumnoCombo,
                            new Label("Evaluación:"), evaluacionCombo, // ✅ Ahora es un ComboBox
                            new Label("Nota:"), notaField,
                            new Label("Comentario:"), comentarioArea,
                            messageLabel,
                            calificarBtn
                    );
                }
            }
        });

        // 🎯 ACCIÓN DEL BOTÓN CALIFICAR
        calificarBtn.setOnAction(ev -> {
            try {
                Alumno alumno = alumnoCombo.getValue();
                Curso curso = cursosCombo.getValue();
                Evaluacion evaluacion = evaluacionCombo.getValue(); // ✅ Objeto Evaluación real
                float nota = Float.parseFloat(notaField.getText().trim());
                String comentario = comentarioArea.getText().trim();

                if (alumno == null || evaluacion == null) {
                    throw new IllegalArgumentException("⚠️ Seleccione alumno y evaluación.");
                }

                // Lógica POO: Crear objeto Calificacion con entidades sincronizadas
                Calificacion calificacion = new Calificacion(
                        alumno, curso, evaluacion, nota, comentario // ✅ Todos los objetos son reales de la BDD
                );

                // Registrar y sincronizar en BDD
                cursosController.registrarCalificacion(calificacion);

                messageLabel.setText("✅ Calificación registrada correctamente.");
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setVisible(true);

                // Limpieza
                notaField.clear();
                comentarioArea.clear();

            } catch (NumberFormatException ex) {
                messageLabel.setText("⚠️ La nota debe ser un número válido (ej: 8.5)");
                messageLabel.setTextFill(Color.RED);
                messageLabel.setVisible(true);
            } catch (Exception ex) {
                messageLabel.setText("⚠️ Error: " + ex.getMessage());
                messageLabel.setTextFill(Color.RED);
                messageLabel.setVisible(true);
            }
        });

        // Contenido final de la vista
        content.getChildren().addAll(titleLabel, infoLabel, cursosCombo, calificacionPanel);
        return content;
    }
    
    private void styleButton(Button button) {
        button.setStyle(
            "-fx-background-color: #764ba2; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10 20; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        
        button.setOnMouseEntered(e -> 
            button.setStyle(
                "-fx-background-color: #5e3a82; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;"
            )
        );
        
        button.setOnMouseExited(e -> 
            button.setStyle(
                "-fx-background-color: #764ba2; " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 10 20; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;"
            )
        );
    }
}
