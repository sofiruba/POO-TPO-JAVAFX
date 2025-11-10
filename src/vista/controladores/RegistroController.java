package vista.controladores;

import controller.UsuariosController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import modelos.usuario.Alumno;

import java.util.Date;

/**
 * Controlador para la vista de Registro
 */
public class RegistroController {
    
    @FXML
    private TextField nombreField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private Label messageLabel;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private Button backButton;
    
    private UsuariosController usuariosController;
    
    public void setUsuariosController(UsuariosController controller) {
        this.usuariosController = controller;
    }
    
    @FXML
    private void initialize() {
        // Configurar efectos hover
        registerButton.setOnMouseEntered(e -> 
            registerButton.setStyle("-fx-background-color: #5568d3; -fx-text-fill: white;")
        );
        registerButton.setOnMouseExited(e -> 
            registerButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white;")
        );
    }
    
    @FXML
    private void handleRegister() {
        String nombre = nombreField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        
        // Validaciones
        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showMessage("⚠️ Por favor, complete todos los campos", Color.RED);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showMessage("⚠️ Las contraseñas no coinciden", Color.RED);
            return;
        }
        
        if (password.length() < 4) {
            showMessage("⚠️ La contraseña debe tener al menos 4 caracteres", Color.RED);
            return;
        }
        
        // Crear alumno
        Alumno nuevoAlumno = new Alumno(nombre, email, password, new Date());
        Alumno alumnoRegistrado = usuariosController.registrarAlumno(nuevoAlumno);
        
        if (alumnoRegistrado != null) {
            // Mostrar alerta de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registro Exitoso");
            alert.setHeaderText("¡Bienvenido!");
            alert.setContentText("Tu cuenta ha sido creada exitosamente.\nYa puedes iniciar sesión.");
            alert.showAndWait();
            
            // Volver al login
            handleBack();
        } else {
            showMessage("❌ El email ya está registrado", Color.RED);
        }
    }
    
    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) backButton.getScene().getWindow();
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
        }
    }
    
    private void showMessage(String message, Color color) {
        messageLabel.setText(message);
        messageLabel.setTextFill(color);
        messageLabel.setVisible(true);
    }
}
