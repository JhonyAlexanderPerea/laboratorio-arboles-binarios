package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class arbolesPersistencia {
    private static final String ARCHIVO = "src/main/resources/persistencia/arbolesBinarios.json";

    public static void guardarArboles(List<arbolBinario> arboles) throws IOException {
        JSONArray jsonArray = new JSONArray();

        for (arbolBinario arbol : arboles) {
            JSONObject jsonObject = new JSONObject();
            // Agregar información del árbol (por ejemplo, claves de los nodos)
            List<Integer> claves = extraerClavesArbol(arbol); // Extraemos las claves correctamente
            jsonObject.put("nodos", claves); // Guardamos las claves como un array en JSON
            jsonArray.put(jsonObject);
        }

        try (FileWriter file = new FileWriter(ARCHIVO)) {
            file.write(jsonArray.toString(4)); // Escribir en el archivo con una indentación de 4 espacios
        }
    }

    // Método para cargar árboles binarios
    public static List<arbolBinario> cargarArboles() throws IOException {
        List<arbolBinario> arboles = new ArrayList<>();
        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            return arboles;
        }

        StringBuilder jsonText = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                jsonText.append(linea);
            }
        }

        JSONArray jsonArray = new JSONArray(jsonText.toString());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            List<Integer> claves = extraerClavesDesdeJson(jsonObject.getJSONArray("nodos"));
            arbolBinario nuevoArbol = construirArbol(claves);
            arboles.add(nuevoArbol);
        }

        return arboles;
    }

    // Método para extraer las claves de un árbol binario (debe implementar este método de acuerdo a tu lógica)
    public static List<Integer> extraerClavesArbol(arbolBinario arbol) {
        List<Integer> claves = new ArrayList<>();
        // Lógica para extraer las claves del árbol
        // Esto depende de cómo desees representar el árbol en JSON
        arbol.recorrerEnPreorden(arbol.raiz, claves);
        return claves;
    }

    // Método para reconstruir un árbol binario desde una lista de claves (esto debe implementar según cómo deseas hacerlo)
    private static arbolBinario construirArbol(List<Integer> claves) {
        arbolBinario nuevoArbol = new arbolBinario(null);
        for (Integer clave : claves) {
            nuevoArbol.agregar(new Nodo(clave));  // Asumiendo que tienes un constructor de Nodo con una clave
        }
        return nuevoArbol;
    }

    // Extraer las claves desde un JSONArray (al cargar desde JSON)
    private static List<Integer> extraerClavesDesdeJson(JSONArray jsonArray) {
        List<Integer> claves = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            claves.add(jsonArray.getInt(i));
        }
        return claves;
    }
}
