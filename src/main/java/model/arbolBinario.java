package model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class arbolBinario<T extends Comparable<T>> implements Serializable {

	public Nodo<T> raiz;
	private int altura;
	private String nombre;

	public arbolBinario() {}

	public arbolBinario(Nodo<T> raiz) {
		this.raiz = raiz;
	}

	public Nodo<T> getRaiz() {
		return this.raiz;
	}

	public void agregar(Nodo<T> nuevoNodo) {
		if (raiz == null) {
			raiz = nuevoNodo;
		} else {
			agregarRecursivo(raiz, nuevoNodo);
		}
	}

	private void agregarRecursivo(Nodo<T> actual, Nodo<T> nuevoNodo) {
		if (nuevoNodo.getClave().compareTo(actual.getClave()) < 0) {
			if (actual.getIzquierdo() == null) {
				actual.setIzquierdo(nuevoNodo);
			} else {
				agregarRecursivo(actual.getIzquierdo(), nuevoNodo);
			}
		} else if (nuevoNodo.getClave().compareTo(actual.getClave()) > 0) {
			if (actual.getDerecho() == null) {
				actual.setDerecho(nuevoNodo);
			} else {
				agregarRecursivo(actual.getDerecho(), nuevoNodo);
			}
		} else {
			System.out.println("El elemento ya existe");
		}
	}

	public void recorrerEnPreorden(Nodo<T> nodo, List<T> claves) {
		if (nodo != null) {
			claves.add(nodo.getClave());
			recorrerEnPreorden(nodo.getIzquierdo(), claves);
			recorrerEnPreorden(nodo.getDerecho(), claves);
		}
	}

	public List<T> obtenerClavesPreorden() {
		List<T> claves = new ArrayList<>();
		recorrerEnPreorden(raiz, claves);
		return claves;
	}

	public void imprimirArbolHorizontal() {
		imprimirArbolHorizontal(raiz, 0);
	}

	private void imprimirArbolHorizontal(Nodo<T> nodo, int nivel) {
		if (nodo == null) {
			return;
		}

		imprimirArbolHorizontal(nodo.getDerecho(), nivel + 1);

		for (int i = 0; i < nivel; i++) {
			System.out.print("    ");
		}

		System.out.println(nodo.getClave());
		imprimirArbolHorizontal(nodo.getIzquierdo(), nivel + 1);
	}

	public int contarHojas(Nodo<T> nodo) {
		if (nodo == null) {
			return 0;
		}
		if (nodo.getIzquierdo() == null && nodo.getDerecho() == null) {
			return 1;
		}
		return contarHojas(nodo.getIzquierdo()) + contarHojas(nodo.getDerecho());
	}

	// Otros métodos restantes adaptados a T...

	public void eliminar(T clave) {
		raiz = eliminarNodo(raiz, clave);
	}

	private Nodo<T> eliminarNodo(Nodo<T> nodo, T clave) {
		if (nodo == null) {
			System.out.println("El nodo con clave " + clave + " no existe en el árbol.");
			return null;
		}

		int comparacion = clave.compareTo(nodo.getClave());

		if (comparacion < 0) {
			nodo.setIzquierdo(eliminarNodo(nodo.getIzquierdo(), clave));
		} else if (comparacion > 0) {
			nodo.setDerecho(eliminarNodo(nodo.getDerecho(), clave));
		} else {
			if (esHoja(nodo)) {
				return null;
			}
			if (nodo.getIzquierdo() == null) {
				return nodo.getDerecho();
			} else if (nodo.getDerecho() == null) {
				return nodo.getIzquierdo();
			}

			Nodo<T> sucesor = devolverMasPequenioRecursivo(nodo.getDerecho());
			nodo.setClave(sucesor.getClave());
			nodo.setDerecho(eliminarNodo(nodo.getDerecho(), sucesor.getClave()));
		}
		return nodo;
	}

	public Nodo<T> devolverMasPequenioRecursivo(Nodo<T> nodo) {
		if (nodo.getIzquierdo() == null) {
			return nodo;
		}
		return devolverMasPequenioRecursivo(nodo.getIzquierdo());
	}

	public boolean esHoja(Nodo<T> nodo) {
		return nodo.getIzquierdo() == null && nodo.getDerecho() == null;
	}

	public boolean esIgual(arbolBinario otroArbol) {
		return esIgual(this.raiz, otroArbol.getRaiz());
	}

	// Método recursivo para comparar los nodos de ambos árboles
	private boolean esIgual(Nodo nodo1, Nodo nodo2) {
		// Ambos nodos son nulos, los árboles son iguales
		if (nodo1 == null && nodo2 == null) {
			return true;
		}

		// Uno de los nodos es nulo y el otro no, los árboles no son iguales
		if (nodo1 == null || nodo2 == null) {
			return false;
		}

		// Comparar los valores de los nodos y hacer la recursión en los hijos
		return nodo1.getClave() == nodo2.getClave() &&
				esIgual(nodo1.getIzquierdo(), nodo2.getIzquierdo()) &&
				esIgual(nodo1.getDerecho(), nodo2.getDerecho());
	}
}
