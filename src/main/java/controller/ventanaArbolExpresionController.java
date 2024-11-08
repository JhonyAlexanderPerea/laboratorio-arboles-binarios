package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Nodo;
import model.NodoString;
import model.dibujoArbol;
import model.arbolBinario;

import java.io.IOException;

public class ventanaArbolExpresionController {

    @FXML
    private Button btnDibujar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnSalir;

    @FXML
    private Canvas canvas;

    @FXML
    private TextField txtExpresion;

    @FXML
    private Label txtTitulo;

    private dibujoArbol dibujo;

    @FXML
    void limpiarCanvas(ActionEvent event) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        txtExpresion.setText("");
    }

    @FXML
    void dibujarArbol(ActionEvent event) {
        String expresion = txtExpresion.getText();

        if (expresion == null || expresion.trim().isEmpty()) {
            System.out.println("Expresión inválida");
            return;
        }

        // Crear una instancia de NodoString y llamar al método construirArbolDesdeExpresion
        NodoString nodoString = new NodoString();
        Nodo<String> raiz = nodoString.construirArbolDesdeExpresion(expresion);

        if (raiz == null) {
            System.out.println("No se pudo construir el árbol");
            return;
        }

        arbolBinario<String> arbol = new arbolBinario<>(raiz); // Crear arbolBinario con el nodo raíz
        dibujo = new dibujoArbol(arbol); // Pasar el arbolBinario al constructor de dibujoArbol
        dibujo.dibujarArbol(canvas); // Dibujar en el canvas
    }

    @FXML
    void salir(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ventanaPrincipal.fxml"));
            Parent root = loader.load();

            ventanaPrincipalController principalController = loader.getController();

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Arboles binarios");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
