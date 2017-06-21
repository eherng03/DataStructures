package ule.edi.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.EmptyCollectionException;

public class UnorderedArrayList<T> implements UnorderedListADT<T> {

	private T[] storage;
	
	private int nElements;


	protected int INVALID_INDEX = -1;
	
	int INITIAL_CAPACITY = 5;

	
	@SuppressWarnings("unchecked")
	public UnorderedArrayList() {	
		this.storage = (T[]) new Object [INITIAL_CAPACITY];
		this.nElements = 0;		
	}

	@SuppressWarnings("unchecked")
	public UnorderedArrayList(T ... v) {
		this.storage = (T[]) new Object [INITIAL_CAPACITY];
		this.nElements = 0;		
		
		for (T x : v) {
			addToRear(x);
		}
	}

	@SuppressWarnings({"unchecked" })
	private void expand(){
		T[] aux = (T[]) new Object [this.storage.length*2];
		for(int i = 0; i < this.nElements; i++){
			aux[i] = this.storage[i];
		}
		this.storage = aux;
	}
	/**
	 * El elemento i es el que se ha movido.
	 */
	private void moveWhenRemove(int i){
		for(int j = i; j < this.nElements - 1; j++){
			this.storage[j] = this.storage[j+1];
		}
	}
	/**
	 * 
	 * @param i = posicion del elemento añadido
	 */
	private void moveWhenAdd(int i){
		for(int j = this.nElements; j > i; j--){
			this.storage[j] = this.storage[j-1];
		}
	}
	
	@Override
	public void addToFront(T element) {
		if(this.nElements == this.storage.length){
			this.expand();
		}
		if(this.isEmpty()){
			this.storage[0] = element;
			this.nElements++;
		}else{
			moveWhenAdd(0);
			this.storage[0] = element;
			this.nElements++;
		}
	}

	@Override
	public void addToRear(T element) {
		if(this.nElements == this.storage.length){
			this.expand();
		}
		this.storage[this.nElements] = element;
		this.nElements++;
	}

	
	@Override
	public T removeFirst() throws EmptyCollectionException {
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered list");
		}else{
			T element = this.storage[0];
			moveWhenRemove(0);
			this.nElements--;
			return element;
		}
		
	}

	@Override
	public T removeLast() throws EmptyCollectionException {
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered list");
		}else{
			T element;
			element = this.storage[this.nElements-1];
			this.storage[this.nElements-1] = null;
			this.nElements--;						
			return element;
		}
	}

	@Override
	public T remove(T element) throws EmptyCollectionException {
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered list");
		}else{
			T old = null;
			for(int i = 0; i < this.nElements; i++){
				if(this.storage[i].equals(element)){
					old = this.storage[i];
					this.storage[i] = null;
					moveWhenRemove(i);
					this.nElements--;
					break;
				}
			}
			if(old == null){
				throw new NoSuchElementException();
			}
			return old;
		}
	}

	
	@Override
	public T getFirst() throws EmptyCollectionException {
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered list");
		}else{
			return this.storage[0];
		}
	}

	@Override
	public T getLast() throws EmptyCollectionException {
		if(this.isEmpty()){
			throw new EmptyCollectionException("Unordered list");
		}else{
			return this.storage[this.nElements-1];
		}
	}

	
	@Override
	public boolean contains(T target) {
		boolean x = false;
		if(this.isEmpty()){
			return x;
		}else{
			for(int i = 0; i < this.nElements; i++){
				if(target.equals(this.storage[i])){
					x = true;
					return x;
				}
			}
		}
		return x;
	}

	@Override
	public boolean isEmpty() {
		if(this.nElements == 0){
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return this.nElements;
	}

	
	@Override
	public T getElementAt(int i) throws IndexOutOfBoundsException {
		if(i > this.nElements || i <= 0){
			throw new IndexOutOfBoundsException();
		}else{
			return this.storage[i-1];
		}
	}

	@Override
	public T replaceElementAt(int i, T value) throws IndexOutOfBoundsException {
		if(i > this.nElements || i <= 0){
			throw new IndexOutOfBoundsException();
		}else{
			T elementoAntiguo;
			elementoAntiguo = this.storage[i-1];
			this.storage[i-1] = value;
			return elementoAntiguo;
		}
	}

	@Override
	public T removeElementAt(int i) throws IndexOutOfBoundsException {
		if(i > this.nElements || i <= 0){
			throw new IndexOutOfBoundsException();
		}else{
			T elementoAntiguo;
			elementoAntiguo = this.storage[i-1];
			this.storage[i-1] = null;
			moveWhenRemove(i-1);
			this.nElements--;
			return elementoAntiguo;
		}
	}

	
	@Override
	public Iterator<T> iterator() {
		return new DefaultIteratorImpl(this.nElements, this.storage);
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
		private int count;
		private int current;
		private T[] items;
		
		public DefaultIteratorImpl(int nElements, T[] storage) {
			this.count = nElements;
			this.current = 0;			//indice por elque se va recorriendo el array
			this.items = storage;
		}

		@Override
		public boolean hasNext() {
			return current < count;
		}

		@Override
		public T next() throws NoSuchElementException {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			T aux = items[current];
			current++;					//preparamos la lista para que pase al siguiente elemento
			return  aux;
		}

		@Override
		public void remove() throws UnsupportedOperationException{
			//	Según el contrato de {@link java.util.Iterator}
			throw new UnsupportedOperationException();
		}
	};
	
	private class SkippingIteratorImpl implements Iterator<T> {
		private int count;
		private int current;
		private T[] items;
		
		public SkippingIteratorImpl(int nElements, T[] storage) {
			this.count = nElements;
			this.current = 0;
			this.items = storage;
		}

		@Override
		public boolean hasNext() {
			return current  < count;
		}

		@Override
		public T next() throws NoSuchElementException {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			T aux = items[current];
			current += 2;					//preparamos la lista para que pase al siguiente elemento
			return  aux;
		}

		@Override
		public void remove() throws  UnsupportedOperationException{
			//	Según el contrato de {@link java.util.Iterator}
			throw new UnsupportedOperationException();
		}		
	};

	@Override
	public Iterator<T> skippingIterator() {
		return new SkippingIteratorImpl(this.nElements, this.storage);
	}

	private class RangedIteratorImpl implements Iterator<T> {
		private int count;
		private int current;
		private T[] items;
		private int step;

		public RangedIteratorImpl(int from, int to, int step, T[] storage) {
			this.count = to;
			this.current = from-1;
			this.items = storage;
			this.step = step;
		}

		@Override
		public boolean hasNext() {
			return current < nElements && current < count;

		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}else{
				T aux = items[current];
				current += step;					//preparamos la lista para que pase al siguiente elemento
				return  aux;
			}
		}

		@Override
		public void remove() {
			//	Según el contrato de {@link java.util.Iterator}
			throw new UnsupportedOperationException();
		}		
	};

	@Override
	public Iterator<T> rangedIterator(int from, int to, int step) {
		Iterator<T> aux = new RangedIteratorImpl(from, to, step, this.storage);
		return aux;
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
		UnorderedArrayList<E> listanueva = new UnorderedArrayList<>();
		while(contents.hasNext()){
			listanueva.addToRear(contents.next());
		}
		

		return listanueva;
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
	 * 	UnorderedArrayList.distinct(x) es [1, 2, 3, 4, 5]
	 * 
	 * @param T1 una lista de elementos
	 * @return una lista con los elementos de T1 sin duplicados
	 */
	public static <E> UnorderedListADT<E> distinct(UnorderedListADT<E> T1) {
		UnorderedArrayList<E> listaNueva = new UnorderedArrayList<E>();	
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
	 *  UnorderedArrayList.reverse(x) es ['D', 'C', 'C', 'B', 'A']
	 *  
	 * @param T1 una lista de elementos. 
	 * @return otra lista con los mismos elementos en orden inverso.
	 */
	public static <E> UnorderedListADT<E> reverse(UnorderedListADT<E> T1) {
		UnorderedArrayList<E> listaNueva = new UnorderedArrayList<E>();
		Iterator<E> iterador = T1.iterator();
		while(iterador.hasNext()){
			listaNueva.addToFront(iterador.next());
		}
		return listaNueva;
	}
	
	
	@Override
	public String toString() {
		
		//	Construye y devuelve con el formato adecuado
		StringBuffer rx = new StringBuffer();
		
		rx.append("[");
		
		for (int i = 0; i < nElements; i++) {
			rx.append(storage[i]);
			rx.append(", ");
		}
		//	Elimina ", " de más
		if (! isEmpty()) {
			rx.delete(rx.length() - 2, rx.length());
		}
		
		rx.append("]");
		
		return rx.toString();
	}
	
}
