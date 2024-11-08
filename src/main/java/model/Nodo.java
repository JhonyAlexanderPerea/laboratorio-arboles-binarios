package model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Nodo<T extends Comparable<T>> implements Serializable {

	private static final long serialVersionUID = 1L;
	private T clave;
	private Nodo<T> derecho, izquierdo;

	// Constructor que acepta una clave genérica
	public Nodo(T clave) {
		this.clave = clave;
		derecho = izquierdo = null;
	}

	// Constructor que acepta clave, izquierdo y derecho
	public Nodo(T clave, Nodo<T> izquierdo, Nodo<T> derecho) {
		this.clave = clave;
		this.izquierdo = izquierdo;
		this.derecho = derecho;
	}

	// Setters y Getters
	public void setClave(T clave) {
		this.clave = clave;
	}

	public void setDerecho(Nodo<T> derecho) {
		this.derecho = derecho;
	}

	public void setIzquierdo(Nodo<T> izquierdo) {
		this.izquierdo = izquierdo;
	}

	public Nodo<T> getDerecho() {
		return this.derecho;
	}

	public Nodo<T> getIzquierdo() {
		return this.izquierdo;
	}

	public T getClave() {
		return this.clave;
	}

	// Métodos de Lógica para la Conversión de Expresiones y Construcción del Árbol

	public  Nodo<String> construirArbolDesdeExpresion(String expresion) {
		Queue<String> posfija = convertirAPosfija(expresion);
		Stack<Nodo<String>> pila = new Stack<>();

		while (!posfija.isEmpty()) {
			String token = posfija.poll();
			if (esOperador(token)) {
				Nodo<String> nodo = new Nodo<>(token);
				nodo.setDerecho(pila.pop());
				nodo.setIzquierdo(pila.pop());
				pila.push(nodo);
			} else {
				pila.push(new Nodo<>(token));
			}
		}

		return pila.pop();
	}

	private static Queue<String> convertirAPosfija(String expresion) {
		Queue<String> salida = new LinkedList<>();
		Stack<String> operadores = new Stack<>();
		StringTokenizer tokens = new StringTokenizer(expresion, "+-*/() ", true);

		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken().trim();
			if (token.isEmpty()) continue;

			if (esOperador(token)) {
				while (!operadores.isEmpty() && precedencia(operadores.peek()) >= precedencia(token)) {
					salida.add(operadores.pop());
				}
				operadores.push(token);
			} else if (token.equals("(")) {
				operadores.push(token);
			} else if (token.equals(")")) {
				while (!operadores.peek().equals("(")) {
					salida.add(operadores.pop());
				}
				operadores.pop();
			} else {
				salida.add(token);
			}
		}

		while (!operadores.isEmpty()) {
			salida.add(operadores.pop());
		}

		return salida;
	}

	public static boolean esOperador(String token) {
		return "+-*/".contains(token);
	}

	private static int precedencia(String operador) {
		switch (operador) {
			case "+": case "-":
				return 1;
			case "*": case "/":
				return 2;
			default:
				return -1;
		}
	}
}
