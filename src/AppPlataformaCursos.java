import controller.CursosController;
import controller.UsuariosController;
import javafx.application.Application;
import javafx.stage.Stage;
import modelos.pago.PagoServicioImp;
import vista.LoginView;

/**
 * Aplicación principal de la Plataforma de Cursos
 * Punto de entrada de la aplicación JavaFX
 */
public class AppPlataformaCursos extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Configurar la ventana principal
            primaryStage.setTitle("Plataforma de Cursos - Sistema de Gestión");
            primaryStage.setResizable(false);
            
            // Mostrar la vista de login
            UsuariosController usuariosController = new UsuariosController();
            CursosController cursosController = new CursosController(new PagoServicioImp(), usuariosController);
            usuariosController.setCursosController(cursosController);
            LoginView loginView = new LoginView(primaryStage, usuariosController, cursosController);
            loginView.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
