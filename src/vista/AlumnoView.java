package vista;

import controller.CursosController;
import controller.UsuariosController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import modelos.cursos.Calificacion;
import modelos.cursos.Curso;
import modelos.cursos.Evaluacion;
import modelos.cursos.Modulo;
import modelos.pago.Recibo;
import modelos.usuario.Alumno;
import modelos.pago.PagoServicioImp;

import java.util.List;

/**
 * Vista principal para estudiantes
 */
public class AlumnoView {
    
    private Stage stage;
    private Alumno alumno;
    private CursosController cursosController;
    private UsuariosController usuariosController;
    
    public AlumnoView(Stage stage, Alumno alumno, UsuariosController usuariosController, CursosController cursosController) {
        this.stage = stage;
        this.alumno = alumno;
        this.usuariosController = usuariosController;
        this.cursosController = cursosController;
    }
    
    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");
        
        // Barra superior
        HBox topBar = createTopBar();
        root.setTop(topBar);
        
        // Panel lateral
        VBox sideBar = createSideBar();
        root.setLeft(sideBar);
        
        // Contenido principal (inicialmente cursos disponibles)
        VBox mainContent = createCursosDisponiblesView();
        root.setCenter(mainContent);
        
        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
    }
    
    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setStyle("-fx-background-color: #667eea;");
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(20);
        
        Label titleLabel = new Label("🎓 Plataforma de Cursos");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITE);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label userLabel = new Label("👤 " + alumno.getNombre());
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
        
        topBar.getChildren().addAll(titleLabel, spacer, userLabel, logoutButton);
        return topBar;
    }
    
    private VBox createSideBar() {
        VBox sideBar = new VBox(10);
        sideBar.setPadding(new Insets(20));
        sideBar.setStyle("-fx-background-color: white;");
        sideBar.setPrefWidth(250);
        
        Label menuLabel = new Label("MENÚ");
        menuLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        menuLabel.setTextFill(Color.web("#666666"));
        
        Button cursosDisponiblesBtn = createMenuButton("📚 Cursos Disponibles");
        Button misCursosBtn = createMenuButton("📖 Mis Cursos");
        Button misCalificacionesBtn = createMenuButton("⭐ Mis Calificaciones");
        
        cursosDisponiblesBtn.setOnAction(e -> {
            BorderPane root = (BorderPane) stage.getScene().getRoot();
            root.setCenter(createCursosDisponiblesView());
        });
        
        misCursosBtn.setOnAction(e -> {
            BorderPane root = (BorderPane) stage.getScene().getRoot();
            root.setCenter(createMisCursosView());
        });
        
        misCalificacionesBtn.setOnAction(e -> {
            BorderPane root = (BorderPane) stage.getScene().getRoot();
            root.setCenter(createMisCalificacionesView());
        });
        
        sideBar.getChildren().addAll(
            menuLabel,
            new Separator(),
            cursosDisponiblesBtn,
            misCursosBtn,
            misCalificacionesBtn
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
                "-fx-text-fill: #667eea; " +
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
    
    private VBox createCursosDisponiblesView() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        
        Label titleLabel = new Label("📚 Cursos Disponibles");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        // Obtener cursos disponibles
        List<Curso> cursosDisponibles = cursosController.listarCursosDisponibles();
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        
        VBox cursosBox = new VBox(15);
        
        for (Curso curso : cursosDisponibles) {
            VBox cursoCard = createCursoCard(curso);
            cursosBox.getChildren().add(cursoCard);
        }
        
        scrollPane.setContent(cursosBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        content.getChildren().addAll(titleLabel, scrollPane);
        return content;
    }
    
    private VBox createCursoCard(Curso curso) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );
        
        Label nombreLabel = new Label(curso.getNombre());
        nombreLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        nombreLabel.setTextFill(Color.web("#667eea"));
        
        Label descripcionLabel = new Label(curso.getDescripcion());
        descripcionLabel.setWrapText(true);
        descripcionLabel.setTextFill(Color.web("#666666"));
        
        HBox infoBox = new HBox(20);
        Label precioLabel = new Label("💰 $" + String.format("%.2f", curso.getPrecio()));
        precioLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        Label cupoLabel = new Label("👥 Cupo: " + curso.getCupo());
        Label modalidadLabel = new Label("📍 " + curso.getClass().getSimpleName());
        
        infoBox.getChildren().addAll(precioLabel, cupoLabel, modalidadLabel);
        
        Button inscribirseBtn = new Button("Inscribirse");
        inscribirseBtn.setStyle(
            "-fx-background-color: #667eea; " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10 20; " +
            "-fx-background-radius: 5; " +
            "-fx-cursor: hand;"
        );
        
        inscribirseBtn.setOnAction(e -> {
            try {

                mostrarDialogoPago(curso);
                
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No se pudo realizar la inscripción");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });
        
        card.getChildren().addAll(nombreLabel, descripcionLabel, infoBox, inscribirseBtn);
        return card;
    }
    
    private void mostrarDialogoPago(Curso curso) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Realizar Pago");
        dialog.setHeaderText("Pago del curso: " + curso.getNombre());
        
        ButtonType pagarButtonType = new ButtonType("Pagar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(pagarButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        ComboBox<String> metodoPagoCombo = new ComboBox<>();
        metodoPagoCombo.getItems().addAll("Efectivo", "Tarjeta", "Transferencia");
        metodoPagoCombo.setValue("Efectivo");
        
        TextField cuotasField = new TextField("1");
        
        grid.add(new Label("Monto:"), 0, 0);
        grid.add(new Label("$" + String.format("%.2f", curso.getPrecio())), 1, 0);
        grid.add(new Label("Método de Pago:"), 0, 1);
        grid.add(metodoPagoCombo, 1, 1);
        grid.add(new Label("Cuotas:"), 0, 2);
        grid.add(cuotasField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == pagarButtonType) {
                // Devolvemos el método de pago y las cuotas
                return metodoPagoCombo.getValue() + ":" + cuotasField.getText(); // Combinamos ambos valores
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            try {
                String[] parts = result.split(":");
                String metodoPago = parts[0];
                int cuotas = Integer.parseInt(parts[1]);
                float monto = curso.getPrecio();

                // 💡 CLAVE: Usamos la función robusta que maneja Inscripción + Pago + Confirmación
                Recibo recibo = cursosController.inscribirYPagar(alumno, curso, monto, metodoPago, cuotas);

                if (recibo != null) {
                    // El curso debe ser agregado al objeto Alumno para que aparezca en "Mis Cursos"
                    // Nota: La lógica de POO en CursosController.inscribirAlumno debe hacer esto,
                    // pero lo confirmamos aquí por seguridad.

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pago Exitoso");
                    alert.setHeaderText("¡Inscripción confirmada!");
                    alert.setContentText("El pago se procesó correctamente.\nYa estás inscrito en el curso.");
                    alert.showAndWait();

                    // ⚠️ Necesitas actualizar la vista (navegar a Mis Cursos)
                    BorderPane root = (BorderPane) stage.getScene().getRoot();
                    root.setCenter(createMisCursosView());

                } else {
                    throw new Exception("El pago falló o ya estás inscrito en este curso.");
                }

            } catch (NumberFormatException ex) {
                mostrarAlertaError("Entrada Inválida", "Cuotas debe ser un número.");
            } catch (Exception ex) {
                mostrarAlertaError("Error en la Inscripción/Pago", ex.getMessage());
            }
        });
    }
    private void mostrarAlertaError(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText("Proceso Fallido");
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    private VBox createMisCursosView() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        
        Label titleLabel = new Label("📖 Mis Cursos");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        List<Curso> misCursos = alumno.getCursos();
        
        if (misCursos.isEmpty()) {
            Label noCoursesLabel = new Label("No estás inscrito en ningún curso todavía.");
            noCoursesLabel.setTextFill(Color.web("#999999"));
            content.getChildren().addAll(titleLabel, noCoursesLabel);
        } else {
            VBox cursosBox = new VBox(15);
            for (Curso curso : misCursos) {
                VBox cursoCard = createMiCursoCard(curso);
                cursosBox.getChildren().add(cursoCard);
            }
            
            ScrollPane scrollPane = new ScrollPane(cursosBox);
            scrollPane.setFitToWidth(true);
            VBox.setVgrow(scrollPane, Priority.ALWAYS);
            
            content.getChildren().addAll(titleLabel, scrollPane);
        }
        
        return content;
    }
    
    private VBox createMiCursoCard(Curso curso) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );
        
        Label nombreLabel = new Label(curso.getNombre());
        nombreLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        nombreLabel.setTextFill(Color.web("#667eea"));
        
        Label descripcionLabel = new Label(curso.getDescripcion());
        descripcionLabel.setWrapText(true);
        Button verContenidoBtn = new Button("Ver Contenido");
        styleButton(verContenidoBtn); // Usar un estilo de botón consistente

        verContenidoBtn.setOnAction(e -> {
            // Al hacer clic, navega a la vista de contenido
            BorderPane root = (BorderPane) stage.getScene().getRoot();
            root.setCenter(createContenidoCursoAlumnoView(curso));
        });
        
        card.getChildren().addAll(nombreLabel, descripcionLabel, verContenidoBtn);
        return card;
    }
    private VBox createContenidoCursoAlumnoView(Curso curso) {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        Label titleLabel = new Label("Contenido del Curso: " + curso.getNombre());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#667eea"));

        // Cargar Módulos y Evaluaciones (Hidratación)
        // Usa el método del controlador que carga los módulos desde la BDD
        List<Modulo> modulos = cursosController.obtenerModulosDeCurso(curso);

        VBox estructuraBox = new VBox(15);

        if (modulos.isEmpty()) {
            estructuraBox.getChildren().add(new Label("El docente aún no ha cargado módulos para este curso."));
        } else {
            for (Modulo modulo : modulos) {
                estructuraBox.getChildren().add(createModuloStructureCard(modulo));
            }
        }

        ScrollPane scrollPane = new ScrollPane(estructuraBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // Botón para volver al menú de Mis Cursos
        Button backButton = new Button("← Volver a Mis Cursos");
        backButton.setOnAction(e -> {
            BorderPane root = (BorderPane) stage.getScene().getRoot();
            root.setCenter(createMisCursosView()); // Navega de vuelta
        });

        content.getChildren().addAll(titleLabel, scrollPane, backButton);
        return content;
    }

    // Auxiliar: Muestra la estructura de Módulo y sus Evaluaciones
    private VBox createModuloStructureCard(Modulo modulo) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: #e8e8ff; -fx-background-radius: 5;");

        Label moduloTitle = new Label("📘 MÓDULO: " + modulo.getTitulo());
        moduloTitle.setFont(Font.font("System", FontWeight.BOLD, 16));
        moduloTitle.setTextFill(Color.web("#333399"));

        VBox evalsBox = new VBox(3);
        List<Evaluacion> evaluaciones = modulo.getEvaluaciones();

        if (evaluaciones.isEmpty()) {
            evalsBox.getChildren().add(new Label("   (No hay evaluaciones programadas)"));
        } else {
            for (Evaluacion eval : evaluaciones) {
                evalsBox.getChildren().add(new Label("   📝 Evaluación: " + eval.getNombre() + " (Máx: " + eval.getNotaMaxima() + ")"));
            }
        }

        card.getChildren().addAll(moduloTitle, evalsBox);
        return card;
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
    private VBox createMisCalificacionesView() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30));
        
        Label titleLabel = new Label("⭐ Mis Calificaciones");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        
        // Tabla de calificaciones
        TableView<Calificacion> table = new TableView<>();
        table.setStyle("-fx-background-color: white;");
        
        TableColumn<Calificacion, String> cursoCol = new TableColumn<>("Curso");
        cursoCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getCurso().getNombre())
        );
        cursoCol.setPrefWidth(250);
        
        TableColumn<Calificacion, String> evaluacionCol = new TableColumn<>("Evaluación");
        evaluacionCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getEvaluacion().getNombre())
        );
        evaluacionCol.setPrefWidth(200);
        
        TableColumn<Calificacion, Float> notaCol = new TableColumn<>("Nota");
        notaCol.setCellValueFactory(new PropertyValueFactory<>("nota"));
        notaCol.setPrefWidth(100);
        
        TableColumn<Calificacion, String> comentarioCol = new TableColumn<>("Comentario");
        comentarioCol.setCellValueFactory(new PropertyValueFactory<>("comentario"));
        comentarioCol.setPrefWidth(300);
        
        table.getColumns().addAll(cursoCol, evaluacionCol, notaCol, comentarioCol);
        
        // Cargar calificaciones
        List<Calificacion> calificaciones = usuariosController.obtenerCalificacionesAlumno(alumno);
        ObservableList<Calificacion> calificacionesData = FXCollections.observableArrayList(calificaciones);
        table.setItems(calificacionesData);
        
        if (calificaciones.isEmpty()) {
            Label noDataLabel = new Label("No tienes calificaciones registradas todavía.");
            noDataLabel.setTextFill(Color.web("#999999"));
            content.getChildren().addAll(titleLabel, noDataLabel);
        } else {
            VBox.setVgrow(table, Priority.ALWAYS);
            content.getChildren().addAll(titleLabel, table);
        }
        
        return content;
    }
}
