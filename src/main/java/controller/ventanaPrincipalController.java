package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class ventanaPrincipalController {

    @FXML
    private Button btnComparar;

    @FXML
    private Button btnCrearArbol;

    @FXML
    private Button btnExpresion;

    @FXML
    private Button btnSalir;

    @FXML
    private Label txtTitulo;

    private void cambiarVentana(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            if (fxmlPath.equals("/view/ventanaComparar.fxml")) {
                ventanaCompararController compararController = loader.getController();
            } else if (fxmlPath.equals("/view/ventanaEliminar.fxml")) {
                ventanaCrearArbolController crearArbolController = loader.getController();
            } else if (fxmlPath.equals("/view/ventanaActualizar.fxml")) {
                ventanaArbolExpresionController arbolExpresioController = loader.getController();
            }

            Stage stage = new Stage();
            stage.setTitle(title);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void abrirVentanaComparar(ActionEvent event) {
        cambiarVentana(event, "/view/ventanaComparar.fxml", "Comparar");
    }

    @FXML
    void abrirVentanaCrearArbol(ActionEvent event) {
        cambiarVentana(event, "/view/ventanaCrearArbol.fxml", "Crear Arbol");
    }

    @FXML
    void abrirVentanaExpresion(ActionEvent event) {
        cambiarVentana(event, "/view/ventanaArbolExpresion.fxml", "Expresion");
    }

    @FXML
    void cerrarVentana(ActionEvent event) {
        System.exit(0);
    }

}
