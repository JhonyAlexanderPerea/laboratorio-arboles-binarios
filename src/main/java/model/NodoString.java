package model;

import java.util.*;

public class NodoString extends Nodo<String> {

    public NodoString() {
        super(null);
    }

    public NodoString(String valor) {
        super(valor);
    }

    public NodoString(String valor, Nodo<String> izquierdo, Nodo<String> derecho) {
        super(valor);
        this.setIzquierdo(izquierdo);
        this.setDerecho(derecho);
    }

    public NodoString construirArbolDesdeExpresion(String expresion) {
        expresion = expresion.replaceAll("\\s", ""); // Elimina los espacios en blanco
        Queue<String> postfija = infijaAPostfija(expresion); // Convertimos la expresión a postfija
        return construirArbolDesdePostfija(postfija);
    }

    private NodoString construirArbolDesdePostfija(Queue<String> postfija) {
        Stack<NodoString> pila = new Stack<>();

        while (!postfija.isEmpty()) {
            String token = postfija.poll();
            if (esOperando(token)) {
                pila.push(new NodoString(token));
            } else if (esOperador(token)) {
                if (pila.size() < 2) {
                    throw new IllegalArgumentException("Expresión inválida: no hay suficientes operandos para el operador " + token);
                }
                NodoString derecho = pila.pop();
                NodoString izquierdo = pila.pop();
                NodoString nodoOperador = new NodoString(token);
                nodoOperador.setIzquierdo(izquierdo);
                nodoOperador.setDerecho(derecho);
                pila.push(nodoOperador);
            }
        }

        if (pila.size() != 1) {
            throw new IllegalArgumentException("Expresión inválida: exceso de operandos u operadores.");
        }

        return pila.pop();
    }

    private static Queue<String> infijaAPostfija(String expresion) {
        Stack<String> operadores = new Stack<>();
        Queue<String> salida = new LinkedList<>();

        for (int i = 0; i < expresion.length(); i++) {
            char c = expresion.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                StringBuilder operando = new StringBuilder();
                if (i > 0 && (expresion.charAt(i - 1) == '+' || expresion.charAt(i - 1) == '-')
                        && (i == 1 || expresion.charAt(i - 2) == '(')) { // Signo unario
                    operando.append(expresion.charAt(i - 1));
                    operadores.pop();
                }
                operando.append(c);
                salida.add(operando.toString());
            } else if (c == '(') {
                operadores.push(String.valueOf(c));
            } else if (c == ')') {
                while (!operadores.isEmpty() && !operadores.peek().equals("(")) {
                    salida.add(operadores.pop());
                }
                operadores.pop(); // Elimina el '(' de la pila
            } else if (esOperador(String.valueOf(c))) {
                while (!operadores.isEmpty() && prioridad(operadores.peek()) >= prioridad(String.valueOf(c))) {
                    salida.add(operadores.pop());
                }
                operadores.push(String.valueOf(c));
            }
        }

        while (!operadores.isEmpty()) {
            salida.add(operadores.pop());
        }

        return salida;
    }

    private static int prioridad(String operador) {
        switch (operador) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            default:
                return -1;
        }
    }

    public static boolean esOperador(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("^");
    }

    public static boolean esOperando(String token) {
        return token.matches("[a-zA-Z]") || token.matches("[-+][a-zA-Z]");
    }
}
