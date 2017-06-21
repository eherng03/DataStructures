package ule.edi.list;

import java.util.Iterator;

/**
 * Lista en la que la posición de los elementos está determinada por
 * el orden de inserción.
 * 
 * @author profesor
 *
 * @param <T> tipo elemento que esta lista almacena.
 */
public interface UnorderedListADT<T> extends ListADT<T> {

	/**
	 * Inserta el elemento dado en la primera posición de la lista.
	 * 
	 * @param element
	 *            elemento a añadir lista en la primera posición.
	 */
	public void addToFront(T element);

	/**
	 * Añade el elemento al final de la lista.
	 * 
	 * @param element
	 *            elemento a insertar al final de la lista.
	 */
	public void addToRear(T element);

	/**
	 * Consulta el elemento en la posición [1, ...] dada.
	 * 
	 * Si la lista tiene N elementos, las posiciones válidas son [1, 2, ..., N].
	 * 
	 * @param i posición en la colección
	 * @return el elemento en esa posición.
	 * @throws IndexOutOfBoundsException la posición no es válida.
	 */
	public T getElementAt(int i) throws IndexOutOfBoundsException;
	
	/**
	 * Cambia un elemento en la posición [1, ...] dada.
	 * 
	 * Si la lista tiene N elementos, las posiciones válidas son [1, 2, ..., N].
	 * 
	 * @param i posición a cambiar
	 * @param value nuevo valor a almacenar en la posición
	 * @return valor que estaba en la posición
	 * @throws IndexOutOfBoundsException posición no es válida.
	 */
	public T replaceElementAt(int i, T value) throws IndexOutOfBoundsException;
	
	/**
	 * Elimina un elemento en la posición [1, ...] dada.
	 * 
	 * Si la lista tiene N elementos, las posiciones válidas son [1, 2, ..., N].
	 * 
	 * @param i posición del elemento a eliminar, comienza en 1.
	 * @return el elemento que estaba en esa posición.
	 * @throws IndexOutOfBoundsException posición no es válida.
	 */
	public T removeElementAt(int i) throws IndexOutOfBoundsException;
	
	/**
	 * Devuelve un iterador que recorre la lista, pero sólo para posiciones impares de la lista (1º, 3º..)
	 * 
	 * Por ejemplo, una lista x con elementos [A, B, C, D, E] 
	 * 
	 * 	x.skippingIterator() devuelve uno a uno, A, C y E.
	 * 
	 * @return iterador para posiciones impares de la lista.

	 */
	public Iterator<T> skippingIterator();
	
	/**
	 * Devuelve un iterador que recorre un subconjunto de elementos de la lista.
	 * 
	 * Los primeros parámetros indican posiciones de elementos que marcan el rango de elementos 
	 * a considerar. El último indica cuántas posiciones avanzar
	 * en cada paso.
	 * 
	 * Por ejemplo, en una lista x de 10 elementos [1,2,3,4,5,6,7,8,9,10] se tendría:
	 * 
	 *  x.rangedIterator(1, 10, 1) es equivalente al iterador normal
	 *  
	 *  x.rangedIterator(1, 10, 2) recorre los elementos [1, 3, 5, 7, 9]
	 *  
	 *  x.rangedIterator(4, 8, 2) recorre los elementos [4, 6, 8]
	 * 
	 *  x.rangedIterator(1, 100, 10) recorre las posiciones [1]
	 * 
	 * @param from primera posición de la lista a considerar
	 * @param to límite para las posiciones a considerar (incluido)
	 * @param step cuántas posiciones avanzar en cada paso del iterador
	 * @return iterador que permite recorrer determinados elementos de la lista
	 */
	public Iterator<T> rangedIterator(int from, int to, int step);

}
