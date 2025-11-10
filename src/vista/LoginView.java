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
import modelos.usuario.Docente;
import modelos.usuario.Usuario;

/**
 * Vista de Login - Pantalla inicial de la aplicación
 */
public class LoginView {
    
    private Stage stage;
    private UsuariosController usuariosController;

    private CursosController cursosController; // ✅ Nuevo atributo

    // ✅ Modificar el constructor para inyectar ambas dependencias
    public LoginView(Stage stage, UsuariosController uc, CursosController cc) {
        this.stage = stage;
        this.usuariosController = uc; // Usar la instancia inyectada
        this.cursosController = cc;  // Usar la instancia inyectada
    }

    
    public void show() {
        // Panel principal con gradiente de fondo
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea 0%, #764ba2 100%);");
        
        // Panel del formulario de login
        VBox loginPanel = createLoginPanel();
        
        root.getChildren().add(loginPanel);
        
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.show();
    }
    
    private VBox createLoginPanel() {
        VBox panel = new VBox(20);
        panel.setMaxWidth(400);
        panel.setAlignment(Pos.CENTER);
        panel.setPadding(new Insets(40));
        panel.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 15; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);"
        );
        
        // Título
        Label titleLabel = new Label("🎓 Plataforma de Cursos");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.web("#667eea"));
        
        Label subtitleLabel = new Label("Inicia sesión para continuar");
        subtitleLabel.setFont(Font.font("System", 14));
        subtitleLabel.setTextFill(Color.web("#666666"));
        
        // Campos de entrada
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 12; " +
            "-fx-background-radius: 8; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 8;"
        );
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");
        passwordField.setStyle(
            "-fx-font-size: 14px; " +
            "-fx-padding: 12; " +
            "-fx-background-radius: 8; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 8;"
        );
        
        // Botón de login
        Button loginButton = new Button("Iniciar Sesión");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle(
            "-fx-background-color: #667eea; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 16px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 12; " +
            "-fx-background-radius: 8; " +
            "-fx-cursor: hand;"
        );
        
        // Efectos hover
        loginButton.setOnMouseEntered(e -> 
            loginButton.setStyle(
                "-fx-background-color: #5568d3; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 12; " +
                "-fx-background-radius: 8; " +
                "-fx-cursor: hand;"
            )
        );
        
        loginButton.setOnMouseExited(e -> 
            loginButton.setStyle(
                "-fx-background-color: #667eea; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-padding: 12; " +
                "-fx-background-radius: 8; " +
                "-fx-cursor: hand;"
            )
        );
        
        // Label para mensajes de error
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);
        errorLabel.setVisible(false);
        
        // Acción del botón login
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            
            if (email.isEmpty() || password.isEmpty()) {
                errorLabel.setText("⚠️ Por favor, complete todos los campos");
                errorLabel.setVisible(true);
                return;
            }
            
            Usuario usuario = usuariosController.login(email, password);
            
            if (usuario != null) {
                // Login exitoso
                System.out.println("✅ Login exitoso: " + usuario.getNombre());

                if (usuario instanceof Alumno) {

                    AlumnoView alumnoView = new AlumnoView(stage, (Alumno) usuario, usuariosController, cursosController);
                    alumnoView.show();
                } else if (usuario instanceof Docente) {

                    DocenteView docenteView = new DocenteView(stage, (Docente) usuario, usuariosController, cursosController);
                    docenteView.show();
                }
            } else {
                errorLabel.setText("❌ Email o contraseña incorrectos");
                errorLabel.setVisible(true);
            }
        });
        
        // Enter key para login
        passwordField.setOnAction(e -> loginButton.fire());
        
        // Separador
        Separator separator = new Separator();
        separator.setMaxWidth(250);
        
        // Link de registro
        HBox registerBox = new HBox(5);
        registerBox.setAlignment(Pos.CENTER);
        
        Label registerLabel = new Label("¿No tienes cuenta?");
        registerLabel.setTextFill(Color.web("#666666"));
        
        Hyperlink registerLink = new Hyperlink("Regístrate aquí");
        registerLink.setStyle("-fx-text-fill: #667eea; -fx-font-weight: bold;");
        registerLink.setOnAction(e -> {
            RegistroView registroView = new RegistroView(stage, usuariosController, cursosController); // ✅ Inyectar CursosController
            registroView.show();
        });
        
        registerBox.getChildren().addAll(registerLabel, registerLink);
        
        // Agregar todos los elementos al panel
        panel.getChildren().addAll(
            titleLabel,
            subtitleLabel,
            new VBox(10, emailField, passwordField),
            errorLabel,
            loginButton,
            separator,
            registerBox
        );
        
        return panel;
    }
}
