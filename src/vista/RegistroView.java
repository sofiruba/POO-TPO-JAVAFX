package vista;

import controller.CursosController;
import controller.UsuariosController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import modelos.usuario.Alumno;

import java.util.Date;

/**
 * Vista de Registro - Permite a nuevos usuarios registrarse como alumnos
 */
public class RegistroView {
    
    private Stage stage;
    private UsuariosController usuariosController;
    private CursosController cursosController;
    
    public RegistroView(Stage stage, UsuariosController usuariosController, CursosController cursosController) {
        this.stage = stage;
        this.usuariosController = usuariosController;
        this.cursosController = cursosController;
    }
    
    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea 0%, #764ba2 100%);");
        
        VBox registerPanel = createRegisterPanel();
        root.getChildren().add(registerPanel);
        
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
    }
    
    private VBox createRegisterPanel() {
        VBox panel = new VBox(15);
        panel.setMaxWidth(400);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(40));
        panel.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 15; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);"
        );
        
        // Título
        Label titleLabel = new Label("📝 Crear Cuenta");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.web("#667eea"));
        
        Label subtitleLabel = new Label("Regístrate como alumno");
        subtitleLabel.setFont(Font.font("System", 14));
        subtitleLabel.setTextFill(Color.web("#666666"));
        
        // Campos de entrada
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre completo");
        styleTextField(nombreField);
        
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        styleTextField(emailField);
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        styleTextField(passwordField);
        
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirmar contraseña");
        styleTextField(confirmPasswordField);
        
        // Label para mensajes
        Label messageLabel = new Label();
        messageLabel.setWrapText(true);
        messageLabel.setVisible(false);
        messageLabel.setMaxWidth(350);
        
        // Botón de registro
        Button registerButton = new Button("Registrarse");
        registerButton.setMaxWidth(Double.MAX_VALUE);
        styleButton(registerButton, "#667eea", "#5568d3");
        
        registerButton.setOnAction(e -> {
            String nombre = nombreField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();
            
            // Validaciones
            if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showMessage(messageLabel, "⚠️ Por favor, complete todos los campos", Color.RED);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                showMessage(messageLabel, "⚠️ Las contraseñas no coinciden", Color.RED);
                return;
            }
            
            if (password.length() < 4) {
                showMessage(messageLabel, "⚠️ La contraseña debe tener al menos 4 caracteres", Color.RED);
                return;
            }
            
            // Crear alumno
            Alumno nuevoAlumno = new Alumno(nombre, email, password, new Date());
            Alumno alumnoRegistrado = usuariosController.registrarAlumno(nuevoAlumno);
            
            if (alumnoRegistrado != null) {
                // Registro exitoso
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro Exitoso");
                alert.setHeaderText("¡Bienvenido!");
                alert.setContentText("Tu cuenta ha sido creada exitosamente.\nYa puedes iniciar sesión.");
                alert.showAndWait();
                
                // Volver al login
                LoginView loginView = new LoginView(stage, usuariosController, cursosController);
                loginView.show();
            } else {
                showMessage(messageLabel, "❌ El email ya está registrado", Color.RED);
            }
        });
        
        // Botón volver
        Button backButton = new Button("Volver al Login");
        backButton.setMaxWidth(Double.MAX_VALUE);
        styleButton(backButton, "#95a5a6", "#7f8c8d");
        
        backButton.setOnAction(e -> {
            LoginView loginView = new LoginView(stage, usuariosController, cursosController);
            loginView.show();
        });
        
        // Agregar elementos
        panel.getChildren().addAll(
            titleLabel,
            subtitleLabel,
            new VBox(10, nombreField, emailField, passwordField, confirmPasswordField),
            messageLabel,
            registerButton,
            backButton
        );
        
        return panel;
    }
    
    private void styleTextField(TextField field) {
        field.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 12; " +
            "-fx-background-radius: 8; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 8;"
        );
    }
    
    private void styleButton(Button button, String normalColor, String hoverColor) {
        String normalStyle = String.format(
            "-fx-background-color: %s; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;",
            normalColor
        );
        
        String hoverStyle = String.format(
            "-fx-background-color: %s; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;",
            hoverColor
        );
        
        button.setStyle(normalStyle);
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(normalStyle));
    }
    
    private void showMessage(Label label, String message, Color color) {
        label.setText(message);
        label.setTextFill(color);
        label.setVisible(true);
    }
}
