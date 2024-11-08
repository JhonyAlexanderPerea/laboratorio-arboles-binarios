package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import model.Nodo;
import model.arbolBinario;
import model.arbolesPersistencia;
import model.dibujoArbol;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class ventanaCrearArbolController {

    @FXML
    private Button btnAgregarNodo;

    @FXML
    private Button btnEliminarNodo;

    @FXML
    private Button btnLimpiarArbol;

    @FXML
    private Button btnSalir;

    @FXML
    private Label txtTitulo;

    @FXML
    private Canvas canvas;


    private arbolBinario arbolBinario;
    private dibujoArbol dibujoArbol;

    public ventanaCrearArbolController() {
        // Inicializamos el árbol vacío
        this.arbolBinario = new arbolBinario(null);
        this.dibujoArbol = new dibujoArbol(arbolBinario);
    }

    @FXML
    void initialize() {
        // Inicializamos el dibujo del árbol al cargar la ventana
        dibujoArbol.dibujarArbol(canvas);
    }

    @FXML
    void agregarNodo(ActionEvent event) {
        Nodo nuevoNodo = new Nodo(Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número que desea agregar: ")));
        arbolBinario.agregar(nuevoNodo);
        dibujoArbol.dibujarArbol(canvas);  // Redibujar el árbol
    }

    @FXML
    void eliminarNodo(ActionEvent event) {
        int clave = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el número que desea eliminar: "));
        arbolBinario.eliminar(clave);
        dibujoArbol.dibujarArbol(canvas);  // Redibujar el árbol
    }

    @FXML
    void limpiarArbol(ActionEvent event) {
        // Limpiar el árbol (hacerlo vacío)
        arbolBinario = new arbolBinario(null);
        dibujoArbol = new dibujoArbol(arbolBinario);  // Crear un nuevo dibujoArbol con el árbol vacío
        dibujoArbol.dibujarArbol(canvas);  // Redibujar el árbol vacío
    }

    @FXML
    void salir(ActionEvent event) {
        try {
            // Cerrar la ventana actual y abrir la principal
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