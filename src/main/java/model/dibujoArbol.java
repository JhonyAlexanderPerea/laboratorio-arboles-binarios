package model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class dibujoArbol {

    private arbolBinario arbol;

    public dibujoArbol(arbolBinario arbol) {
        this.arbol = arbol;
    }

    // Método para dibujar el árbol en el Canvas
    public void dibujarArbol(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); // Limpiar el lienzo
        if (arbol.getRaiz() != null) {
            dibujarNodo(gc, arbol.getRaiz(), canvas.getWidth() / 2, 30, canvas.getWidth() / 4);
        }
    }

    // Método recursivo para dibujar los nodos del árbol binario
    private void dibujarNodo(GraphicsContext gc, Nodo nodo, double x, double y, double distancia) {
        if (nodo != null) {
            // Dibujar el nodo
            gc.setFill(Color.LIGHTBLUE);
            gc.fillOval(x - 20, y - 20, 40, 40); // Dibuja un círculo para el nodo
            gc.setFill(Color.BLACK);
            gc.fillText(String.valueOf(nodo.getClave()), x - 10, y + 5); // Escribe la clave del nodo

            // Dibujar las líneas de conexión
            if (nodo.getIzquierdo() != null) {
                gc.strokeLine(x, y, x - distancia, y + 50); // Línea hacia el hijo izquierdo
                dibujarNodo(gc, nodo.getIzquierdo(), x - distancia, y + 50, distancia / 2); // Llamada recursiva para el hijo izquierdo
            }
            if (nodo.getDerecho() != null) {
                gc.strokeLine(x, y, x + distancia, y + 50); // Línea hacia el hijo derecho
                dibujarNodo(gc, nodo.getDerecho(), x + distancia, y + 50, distancia / 2); // Llamada recursiva para el hijo derecho
            }
        }
    }
}
