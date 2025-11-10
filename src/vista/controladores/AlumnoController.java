package vista.controladores;

import controller.CursosController;
import controller.UsuariosController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelos.cursos.Calificacion;
import modelos.cursos.Curso;
import modelos.usuario.Alumno;
import modelos.pago.PagoServicioImp;

import java.util.List;

/**
 * Controlador para la vista de Alumno usando FXML
 */
public class AlumnoController {
    
    @FXML
    private Label userLabel;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private Button cursosDisponiblesBtn;
    
    @FXML
    private Button misCursosBtn;
    
    @FXML
    private Button misCalificacionesBtn;
    
    @FXML
    private StackPane contentPane;
    
    private Alumno alumno;
    private CursosController cursosController;
    private UsuariosController usuariosController;
    
    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
        this.usuariosController = new UsuariosController();
        this.cursosController = new CursosController(new PagoServicioImp(), usuariosController);
        
        userLabel.setText("👤 " + alumno.getNombre());
        
        // Mostrar cursos disponibles por defecto
        mostrarCursosDisponibles();
    }
    
    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);
            
            try {
                scene.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
            } catch (Exception e) {
                // CSS no disponible
            }
            
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void mostrarCursosDisponibles() {
        VBox content = new VBox(20);
        
        Label titleLabel = new Label("📚 Cursos Disponibles");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        
        VBox cursosBox = new VBox(15);
        List<Curso> cursos = cursosController.listarCursosDisponibles();
        
        for (Curso curso : cursos) {
            VBox cursoCard = crearCursoCard(curso);
            cursosBox.getChildren().add(cursoCard);
        }
        
        scrollPane.setContent(cursosBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        content.getChildren().addAll(titleLabel, scrollPane);
        contentPane.getChildren().setAll(content);
    }
    
    private VBox crearCursoCard(Curso curso) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 10; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );
        
        Label nombreLabel = new Label(curso.getNombre());
        nombreLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #667eea;");
        
        Label descripcionLabel = new Label(curso.getDescripcion());
        descripcionLabel.setWrapText(true);
        descripcionLabel.setStyle("-fx-text-fill: #666666;");
        
        HBox infoBox = new HBox(20);
        Label precioLabel = new Label(String.format("💰 $%.2f", curso.getPrecio()));
        precioLabel.setStyle("-fx-font-weight: bold;");
        Label cupoLabel = new Label("👥 Cupo: " + curso.getCupo());
        Label modalidadLabel = new Label("📍 " + curso.getClass().getSimpleName());
        
        infoBox.getChildren().addAll(precioLabel, cupoLabel, modalidadLabel);
        
        Button inscribirseBtn = new Button("Inscribirse");
        inscribirseBtn.getStyleClass().add("primary-button");
        
        inscribirseBtn.setOnAction(e -> {
            try {
                cursosController.preinscribirAlumno(alumno, curso);
                mostrarDialogoPago(curso);
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });
        
        card.getChildren().addAll(nombreLabel, descripcionLabel, infoBox, inscribirseBtn);
        return card;
    }
    
    private void mostrarDialogoPago(Curso curso) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Realizar Pago");
        dialog.setHeaderText("Curso: " + curso.getNombre());
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));
        
        ComboBox<String> metodoPagoCombo = new ComboBox<>();
        metodoPagoCombo.getItems().addAll("Efectivo", "Tarjeta", "Transferencia");
        metodoPagoCombo.setValue("Efectivo");
        
        TextField cuotasField = new TextField("1");
        
        grid.add(new Label("Monto:"), 0, 0);
        grid.add(new Label(String.format("$%.2f", curso.getPrecio())), 1, 0);
        grid.add(new Label("Método de Pago:"), 0, 1);
        grid.add(metodoPagoCombo, 1, 1);
        grid.add(new Label("Cuotas:"), 0, 2);
        grid.add(cuotasField, 1, 2);
        
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int cuotas = Integer.parseInt(cuotasField.getText());
                    cursosController.procesarPagoYConfirmarInscripcion(
                        alumno, curso, curso.getPrecio(), metodoPagoCombo.getValue(), cuotas
                    );
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Pago Exitoso");
                    alert.setContentText("¡Inscripción confirmada!");
                    alert.showAndWait();
                    
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            }
        });
    }
    
    @FXML
    private void mostrarMisCursos() {
        VBox content = new VBox(20);
        
        Label titleLabel = new Label("📖 Mis Cursos");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        List<Curso> misCursos = cursosController.obtenerCursosInscritosAlumno(alumno);
        
        if (misCursos.isEmpty()) {
            Label noCoursesLabel = new Label("No estás inscrito en ningún curso todavía.");
            noCoursesLabel.setStyle("-fx-text-fill: #999999;");
            content.getChildren().addAll(titleLabel, noCoursesLabel);
        } else {
            VBox cursosBox = new VBox(15);
            for (Curso curso : misCursos) {
                Label cursoLabel = new Label("• " + curso.getNombre());
                cursoLabel.setStyle("-fx-font-size: 16px;");
                cursosBox.getChildren().add(cursoLabel);
            }
            content.getChildren().addAll(titleLabel, cursosBox);
        }
        
        contentPane.getChildren().setAll(content);
    }
    
    @FXML
    private void mostrarMisCalificaciones() {
        VBox content = new VBox(20);
        
        Label titleLabel = new Label("⭐ Mis Calificaciones");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        TableView<Calificacion> table = new TableView<>();
        
        TableColumn<Calificacion, String> cursoCol = new TableColumn<>("Curso");
        cursoCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(data.getValue().getCurso().getNombre())
        );
        cursoCol.setPrefWidth(250);
        
        TableColumn<Calificacion, Float> notaCol = new TableColumn<>("Nota");
        notaCol.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getNota())
        );
        notaCol.setPrefWidth(100);
        
        table.getColumns().addAll(cursoCol, notaCol);
        
        List<Calificacion> calificaciones = usuariosController.obtenerCalificacionesAlumno(alumno);
        table.getItems().addAll(calificaciones);
        
        content.getChildren().addAll(titleLabel, table);
        contentPane.getChildren().setAll(content);
    }
}
