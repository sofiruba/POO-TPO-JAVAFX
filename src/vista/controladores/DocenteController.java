package vista.controladores;

import controller.CursosController;
import controller.UsuariosController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import modelos.cursos.Curso;
import modelos.usuario.Alumno;
import modelos.usuario.Docente;
import modelos.pago.PagoServicioImp;

import java.util.List;

/**
 * Controlador para la vista de Docente usando FXML
 */
public class DocenteController {
    
    @FXML
    private Label userLabel;
    
    @FXML
    private Button logoutButton;
    
    @FXML
    private StackPane contentPane;
    
    private Docente docente;
    private CursosController cursosController;
    private UsuariosController usuariosController;
    
    public void setDocente(Docente docente) {
        this.docente = docente;
        this.usuariosController = new UsuariosController();
        this.cursosController = new CursosController(new PagoServicioImp(), usuariosController);
        this.usuariosController.setCursosController(cursosController);
        
        userLabel.setText("👤 " + docente.getNombre());
        
        // Mostrar mis cursos por defecto
        mostrarMisCursos();
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
    private void mostrarMisCursos() {
        VBox content = new VBox(20);
        
        Label titleLabel = new Label("📚 Mis Cursos");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        List<Curso> misCursos = cursosController.obtenerCursosDocente(docente);
        
        if (misCursos.isEmpty()) {
            Label noCoursesLabel = new Label("No has creado ningún curso todavía.");
            noCoursesLabel.setStyle("-fx-text-fill: #999999;");
            content.getChildren().addAll(titleLabel, noCoursesLabel);
        } else {
            VBox cursosBox = new VBox(15);
            for (Curso curso : misCursos) {
                HBox cursoItem = new HBox(20);
                cursoItem.setStyle("-fx-padding: 10; -fx-background-color: white; -fx-background-radius: 5;");
                
                Label cursoLabel = new Label(curso.getNombre());
                cursoLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
                
                Button verAlumnosBtn = new Button("Ver Alumnos");
                verAlumnosBtn.getStyleClass().add("docente-button");
                verAlumnosBtn.setOnAction(e -> mostrarAlumnos(curso));
                
                cursoItem.getChildren().addAll(cursoLabel, verAlumnosBtn);
                cursosBox.getChildren().add(cursoItem);
            }
            content.getChildren().addAll(titleLabel, cursosBox);
        }
        
        contentPane.getChildren().setAll(content);
    }
    
    private void mostrarAlumnos(Curso curso) {
        List<Alumno> alumnos = cursosController.obtenerAlumnosInscritosEnCurso(curso);
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alumnos Inscritos");
        alert.setHeaderText("Curso: " + curso.getNombre());
        
        if (alumnos.isEmpty()) {
            alert.setContentText("No hay alumnos inscritos.");
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
}
