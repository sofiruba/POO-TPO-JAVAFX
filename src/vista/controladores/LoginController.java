package vista.controladores;

import controller.UsuariosController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import modelos.usuario.Alumno;
import modelos.usuario.Docente;
import modelos.usuario.Usuario;

/**
 * Controlador para la vista de Login
 */
public class LoginController {
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private Label errorLabel;
    
    @FXML
    private Button loginButton;
    
    @FXML
    private Hyperlink registerLink;
    
    private UsuariosController usuariosController;
    
    public LoginController() {
        this.usuariosController = new UsuariosController();
    }
    
    @FXML
    private void initialize() {
        // Configurar efectos hover en el botón
        loginButton.setOnMouseEntered(e -> 
            loginButton.setStyle("-fx-background-color: #5568d3; -fx-text-fill: white;")
        );
        loginButton.setOnMouseExited(e -> 
            loginButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white;")
        );
    }
    
    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        
        // Validar campos vacíos
        if (email.isEmpty() || password.isEmpty()) {
            showError("⚠️ Por favor, complete todos los campos");
            return;
        }
        
        // Intentar login
        Usuario usuario = usuariosController.login(email, password);
        
        if (usuario != null) {
            System.out.println("✅ Login exitoso: " + usuario.getNombre());
            
            try {
                if (usuario instanceof Alumno) {
                    abrirVistaAlumno((Alumno) usuario);
                } else if (usuario instanceof Docente) {
                    abrirVistaDocente((Docente) usuario);
                }
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error al cargar la vista");
            }
        } else {
            showError("❌ Email o contraseña incorrectos");
        }
    }
    
    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Registro.fxml"));
            Parent root = loader.load();
            
            RegistroController controller = loader.getController();
            controller.setUsuariosController(usuariosController);
            
            Stage stage = (Stage) registerLink.getScene().getWindow();
            Scene scene = new Scene(root);
            
            // Cargar CSS
            try {
                scene.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
            } catch (Exception e) {
                // CSS no disponible
            }
            
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error al cargar la vista de registro");
        }
    }
    
    private void abrirVistaAlumno(Alumno alumno) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/VistaAlumno.fxml"));
        Parent root = loader.load();
        
        AlumnoController controller = loader.getController();
        controller.setAlumno(alumno);
        
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        
        // Cargar CSS
        try {
            scene.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
        } catch (Exception e) {
            // CSS no disponible
        }
        
        stage.setScene(scene);
        stage.setMaximized(true);
    }
    
    private void abrirVistaDocente(Docente docente) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/VistaDocente.fxml"));
        Parent root = loader.load();
        
        DocenteController controller = loader.getController();
        controller.setDocente(docente);
        
        Stage stage = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene(root);
        
        // Cargar CSS
        try {
            scene.getStylesheets().add(getClass().getResource("../css/styles.css").toExternalForm());
        } catch (Exception e) {
            // CSS no disponible
        }
        
        stage.setScene(scene);
        stage.setMaximized(true);
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
