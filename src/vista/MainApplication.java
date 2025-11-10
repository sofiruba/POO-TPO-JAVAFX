package vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Aplicación principal usando FXML
 */
public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar el archivo FXML de login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Login.fxml"));
            Parent root = loader.load();
            
            // Configurar la escena
            Scene scene = new Scene(root);
            
            // Cargar CSS si existe
            try {
                scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
            } catch (Exception e) {
                System.out.println("No se encontró el archivo CSS");
            }
            
            // Configurar el stage
            primaryStage.setTitle("Plataforma de Cursos");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
