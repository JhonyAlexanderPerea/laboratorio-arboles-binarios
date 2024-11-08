package model;

import java.util.Stack;

public class convertidorExpresion {

    public static arbolBinario parse(String expresion) {
        Stack<Nodo> nodos = new Stack<>();
        Stack<Character> operadores = new Stack<>();

        for (char c : expresion.toCharArray()) {
            if (Character.isWhitespace(c)) continue;

            if (Character.isLetterOrDigit(c)) {
                nodos.push(new Nodo(c));
            } else if (c == '(') {
                operadores.push(c);
            } else if (c == ')') {
                while (operadores.peek() != '(') {
                    Nodo derecho = nodos.pop();
                    Nodo izquierdo = nodos.pop();
                    nodos.push(new Nodo(operadores.pop(), izquierdo, derecho));
                }
                operadores.pop();
            } else if (isOperator(c)) {
                while (!operadores.isEmpty() && precedence(operadores.peek()) >= precedence(c)) {
                    Nodo derecho = nodos.pop();
                    Nodo izquierdo = nodos.pop();
                    nodos.push(new Nodo(operadores.pop(), izquierdo, derecho));
                }
                operadores.push(c);
            }
        }

        while (!operadores.isEmpty()) {
            Nodo derecho = nodos.pop();
            Nodo izquierdo = nodos.pop();
            nodos.push(new Nodo(operadores.pop(), izquierdo, derecho));
        }

        return new arbolBinario(nodos.pop());
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedence(char operator) {
        switch (operator) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            default: return 0;
        }
    }
}
