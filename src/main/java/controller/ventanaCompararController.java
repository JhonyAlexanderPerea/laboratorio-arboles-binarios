package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Nodo;
import model.arbolBinario;

import java.io.IOException;

public class ventanaCompararController {

    @FXML
    private TextField txtArbol1;  // Campo de texto para el primer árbol

    @FXML
    private TextField txtArbol2;  // Campo de texto para el segundo árbol

    @FXML
    private Label lblResultado;  // Label para mostrar el resultado

    public ventanaCompararController() {
        // Constructor vacío si no hay inicialización adicional
    }

    @FXML
    void compararArboles(ActionEvent event) {
        // Obtener los valores de los TextFields
        String arbol1Str = txtArbol1.getText();
        String arbol2Str = txtArbol2.getText();

        // Convertir los valores de las cadenas a arreglos de enteros
        arbolBinario arbol1 = crearArbolDesdeCadena(arbol1Str);
        arbolBinario arbol2 = crearArbolDesdeCadena(arbol2Str);

        // Comparar los árboles
        boolean sonIguales = arbol1.esIgual(arbol2);

        if (arbol1.getRaiz() == null && arbol2.getRaiz() == null) {
            lblResultado.setText("Los arboles no existen");
        }
        else if (arbol1.getRaiz() == null) {
            lblResultado.setText("El primer arbol no existe");
        }
        else if (arbol2.getRaiz() == null) {
            lblResultado.setText("El segundo arbol no existe");
        }
        else if (sonIguales) {
            lblResultado.setText("Los árboles son iguales.");
        } else {
            lblResultado.setText("Los árboles no son iguales.");
        }
    }

    private arbolBinario crearArbolDesdeCadena(String cadena) {
        // Convertir la cadena a un array de enteros y crear el árbol
        String[] valores = cadena.split(",");
        arbolBinario arbol = new arbolBinario(null);
        for (String valor : valores) {
            try {
                int num = Integer.parseInt(valor.trim());
                Nodo nodo = new Nodo(num);
                arbol.agregar(nodo);
            } catch (NumberFormatException e) {
                // Manejo de error si el número no es válido
                System.out.println("Valor no válido: " + valor);
            }
        }
        return arbol;
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
