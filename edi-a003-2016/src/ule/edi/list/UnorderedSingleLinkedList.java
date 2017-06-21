package ule.edi.list;


import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.EmptyCollectionException;
import ule.edi.list.UnorderedSingleLinkedList.Node;

public class UnorderedSingleLinkedList<T> implements UnorderedListADT<T> {

	// Estructura de datos, lista simplemente enlazada
	//
	// Este es el primer nodo de la lista
	protected Node<T> first = null;

	// Clase para cada nodo en la lista
	//
	// Es estática y genérica, un 'nodo con elementos de tipo G'.
	//
	// Arriba se usa para implementar una lista de elementos
	// de tipo T.
	//
	protected static class Node<G> {

		G element;
		Node<G> next;
		
		Node(G element) {
			this.element = element;
			this.next = null;
		}

		@Override
		public String toString() {
			return "(" + element + ")";
		}

		
	}

	public UnorderedSingleLinkedList() {
		// Vacía
	}

	@SuppressWarnings("unchecked")
	public UnorderedSingleLinkedList(T... v) {
		// Añadir en el mismo orden que en 'v'
		for (T Vi : v) {
			addToRear(Vi);
		}
	}
	

	
	/**
	 * Como precondicion, la lista nunca va a estar vacia
	 * @return ultimo
	 */
	public Node<T> getUltimoNodo(){
		Node<T> ultimo = first;
		while(ultimo.next != null){			//Cuando el siguiente al ultimo es null, sale del while y devuelve el anterior a este
			ultimo = ultimo.next;
		}
		return ultimo;
	}
	
	
	@Override
	public void addToFront(T element) {
		if(this.isEmpty()){				//si esta vacia, el primer elemento es el que se quiere introducir
			first = new Node<T>(element);
		}else{
			Node<T> aux = new Node<T>(element);
			aux.next = first;
			first = aux;			
		}
	}

	@Override
	public void addToRear(T element){
		if(this.isEmpty()){
			first = new Node<T>(element);
		}else{
			this.getUltimoNodo().next = new Node<T>(element);
		}
	}


	@Override
	public T removeFirst() throws EmptyCollectionException {
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered Single Linked List");
		}else{
			T element = this.first.element;
			first = first.next;
			return element;
		}
	}

	@Override
	public T removeLast() throws EmptyCollectionException {
		T element = null;
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered Single Linked List");
		}else if(this.size() == 1){
			element = first.element;
			first = null;
		}else if(this.size() == 2){
			element = first.next.element;
			first.next = null;
		}else{
			Node<T> penultimo = first;
			Node<T> ultimo = first.next;
			while(ultimo.next != null){			//Cuando el siguiente al ultimo es null, sale del while y devuelve el anterior a este
				penultimo = ultimo;
				ultimo = penultimo.next;
			}
			element = ultimo.element;
			penultimo.next = null;
		}
		return element;
		
	}

	@Override
	public T remove(T element) throws EmptyCollectionException {
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered Single Linked List");
		}else if(!this.contains(element)){
			throw new NoSuchElementException();
		}else{
			T devolver;
			if(first.element == element){
				devolver = this.removeFirst();
			}else{
				Node<T> aux = first;
				Node<T> siguiente = first.next;
				while(siguiente.element != element){
					aux = aux.next;
					siguiente = aux.next;
				}
				devolver = siguiente.element;
				aux.next = siguiente.next;
			}
			
			return devolver;
		}
	}


	@Override
	public T getFirst() throws EmptyCollectionException {
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered Single Linked List");
		}else{
			return this.first.element;
		}
	}

	@Override
	public T getLast() throws EmptyCollectionException {
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered Single Linked List");
		}else{
			return this.getUltimoNodo().element;
		}
	}

	
	@Override
	public boolean contains(T target) {
		Node<T> aux = this.first;
		while(aux != null){
			if(aux.element.equals(target)){
				return true;
			}
			aux = aux.next;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		if(first == null){
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		int size = 0;
		Node<T> ultimo = first;
		while(ultimo != null){
			size++;
			ultimo = ultimo.next;
		}
		return size;
	}

	
	@Override
	public T getElementAt(int i) throws IndexOutOfBoundsException {
		if(i > this.size() || i <= 0){
			throw new IndexOutOfBoundsException();
		}else{
			if(i == 1){
				return this.first.element;
			}else{
				Node<T> aux = this.first;
				for(int j = 1; j < i; j++){
					aux = aux.next;
				}
				return aux.element;
			}
		}
	}

	@Override
	public T removeElementAt(int i) throws IndexOutOfBoundsException {
		T element;
		if(i > this.size() || i <= 0){
			throw new IndexOutOfBoundsException();
		}else{
			if(i == 1){
				element = this.first.element;
				first = first.next;
				return element;
			}else if(i == 2){
				element = this.first.next.element;
				first.next = first.next.next;
				return element;
			}else {
				Node<T> primero = this.first;
				Node<T> segundo = primero.next;
				for(int j = 2; j < i; j++){
					primero = primero.next;
					segundo = primero.next;
				}
				element = segundo.element;
				primero.next = segundo.next;
				return element;
			}
		}
	}
	
	@Override
	public T replaceElementAt(int n, T element) {
		if(n > this.size() || n <= 0){
			throw new IndexOutOfBoundsException();
		}else{
			if(n == 1){
				T aux = this.first.element;
				this.first.element = element;
				return aux;
				
			}else{
				Node<T> aux = this.first;
				for(int j = 1; j < n; j++){
					aux = aux.next;
				}
				T auxE = aux.element;
				aux.element = element;
				return auxE;
			}
		}
	}

	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new DefaultIteratorImpl(this.first);
	}
	
	/**
	 * Clase para el iterador.
	 * 
	 * Como clase interior (anidada no estática) tiene acceso a los atributos
	 * de la clase que la contiene. También al parámetro 'T' del tipo genérico.
	 * 
	 * Si fuera anidada y estática, no tendría acceso.
	 * 
	 * @author profesor
	 */	
	private class DefaultIteratorImpl implements Iterator<T> {
		private Node<T> current;
		
		public DefaultIteratorImpl(Node<T> first) {
			this.current = first;
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}else{
				T aux = current.element;
				current = current.next;
				return aux;
			}
		}

		@Override
		public void remove() {
			// Según el contrato de {@link java.util.Iterator}
			throw new UnsupportedOperationException();
		}
	}

	private class SkippingIteratorImpl implements Iterator<T> {
		private Node<T> current;
		
		public SkippingIteratorImpl(Node<T> first) {
			this.current = first;
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public T next() {
			if(!this.hasNext()){
				throw new NoSuchElementException();
			}else{
				T aux = current.element;
				if(current.next != null){
					current = current.next.next;
				}else{
					current = null;
				}
				return aux;
			}
			
		}

		@Override
		public void remove() {
			//	Según el contrato de {@link java.util.Iterator} 
			throw new UnsupportedOperationException();
		}
	};

	@Override
	public Iterator<T> skippingIterator() {
		// TODO Auto-generated method stub
		return new SkippingIteratorImpl(first);
	}

	private class RangedIteratorImpl implements Iterator<T> {
		private Node<T> current;
		private int from;
		private int to;
		private int step;


		public RangedIteratorImpl(Node<T> first, int from, int to, int step) {
			this.current = first;
			if(from > 1 && from < to){
				for(int i = 2; i <= from; i++){
					if(current != null){
						current = current.next;
					}else{
						current = null;
						break;
					}
				}
			}
			this.from = from;
			this.to = to;
			this.step = step;
		}

		@Override
		public boolean hasNext() {
			return current != null && from <= to;
		}

		@Override
		public T next() {
			T aux;
			if(!this.hasNext()){
				throw new NoSuchElementException();
			}else{
				aux = current.element;
				for(int i = 0; i < step; i++){
					if(current.next != null){
						current = current.next;
					}else{
						current = null;
						break;
					}
					
				}
				from = from + step;
			}
			return aux;
		}

		@Override
		public void remove() {			
			throw new UnsupportedOperationException();
		}
		
	};
		
	@Override
	public Iterator<T> rangedIterator(int from, int to, int step) {
		return new RangedIteratorImpl(this.first, from, to, step);
	}

	/**
	 * Elimina duplicados y devuelve el resultado como otra lista.
	 * 
	 * Si T1 es vacía, devuelve una lista vacía.
	 * 
	 * Únicamente se dispone de las operaciones del TAD y del iterador por defecto.
	 * 
	 * Por ejemplo, con una lista x de números [1, 2, 3, 1, 2, 3, 4, 5, 3] se tendría:
	 * 
	 * 	UnorderedSingleLinkedList.distinct(x) es [1, 2, 3, 4, 5]
	 * 
	 * @param T1 una lista de elementos
	 * @return una lista con los elementos de T1 sin duplicados
	 */
	public static <E> UnorderedListADT<E> distinct(UnorderedListADT<E> T1) {
		UnorderedSingleLinkedList<E> listaNueva = new UnorderedSingleLinkedList<E>();	
		Iterator<E> iterador = T1.iterator();
		if(T1.isEmpty()){
			return listaNueva;
		}else{
			while(iterador.hasNext()){
				E siguiente = iterador.next();
				if(!listaNueva.contains(siguiente)){
					listaNueva.addToRear(siguiente);
				}
			}
		}
		return listaNueva;
	}

	/**
	 * Devuelve una lista en orden inverso.
	 * 
	 * Únicamente se dispone de las operaciones del TAD y del iterador por defecto.
	 * 
	 * Si T1 es vacía, devuelve una lista vacía.
	 * 
	 * Por ejemplo, sea x una lista de caracteres ['A', 'B', 'C', 'C', 'D'], se tiene:
	 * 
	 *  UnorderedSingleLinkedList.reverse(x) es ['D', 'C', 'C', 'B', 'A']
	 *  
	 * @param T1 una lista de elementos. 
	 * @return otra lista con los mismos elementos en orden inverso.
	 */
	public static <E> UnorderedListADT<E> reverse(UnorderedListADT<E> T1) {
		UnorderedSingleLinkedList<E> listaNueva = new UnorderedSingleLinkedList<>();
		Iterator<E> iterador = T1.iterator();
		if(T1.isEmpty()){
			return listaNueva;
		}else{
			while(iterador.hasNext()){
				listaNueva.addToFront(iterador.next());
			}
		}
		return listaNueva;
	}
	
	/**
	 * Construye y devuelve una lista con los elementos dados por un iterador.
	 * 
	 * Por ejemplo, si el iterador devuelve sucesivamente los caracteres
	 * 'a', 'z', 'b', 'y' al invocar a next(), la lista resultado sería
	 * 
	 * 	['a', 'z', 'b', 'y']
	 * 
	 * @param contents iterador de los elementos a añadir en la nueva lista.
	 * @return una lista con los elementos que va entregando el iterador.
	 */
	public static <E> UnorderedListADT<E> listWith(Iterator<E> contents) {
		UnorderedSingleLinkedList<E> listaNueva = new UnorderedSingleLinkedList<>();
		while(contents.hasNext()){
			listaNueva.addToRear(contents.next());
		}
		return listaNueva;
	}
	
	
	@Override
	public String toString() {

		// Construye y devuelve con el formato adecuado
		StringBuffer rx = new StringBuffer();

		rx.append("[");
		Node<T> i = this.first;
		while (i != null) {
			rx.append(i.element);
			rx.append(", ");
			i = i.next;
		}
		// Elimina ", " de más
		if (!isEmpty()) {
			rx.delete(rx.length() - 2, rx.length());
		}

		rx.append("]");

		return rx.toString();
	}

	
}
