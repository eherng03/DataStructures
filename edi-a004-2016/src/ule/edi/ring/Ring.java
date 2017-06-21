package ule.edi.ring;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Almacena datos en una estructura doblemente enlazada circular.
 * 
 * Un anillo siempre tiene un nodo fijo de 'referencia' (nodo cabecera)
 * que tiene a <code>null</code> su contenido. Un anillo vacío será un
 * nodo de referencia (R) que se apunta a sí mismo como 'next' y
 * como 'previous', para cerrar el anillo.
 * 
 * 	> (R) <> (R) <
 * 
 * Cuando se añaden elementos, se hace en nuevos nodos que
 * deben mantener la estructura circular cerrada; por ejemplo tras
 * insertar A, B y C se tendría:
 * 
 * 	> (R) <> A <> B <> C <> (R) <
 * 
	 * Se define el sentido de las agujas del reloj como el indicado
	 * por las referencias 'next' en los nodos:
	 *  
	 *   (R)  A  B  C  (R)
	 *   
	 * y el sentido contrario el dado por las referencias 'previous':
	 * 
	 *   (R)  C  B  A  (R)
 *   
 * @author profesor
 *
 * @param <T> tipo de dato en cada posición del anillo
 */
public class Ring<T> implements Iterable<T> {

	/**
	 * Nodo doblemente enlazado para almacenar un dato.
	 * 
	 * @author profesor
	 *
	 * @param <G> tipo de dato almacenado en este nodo.
	 */
	protected static class Node<G> {
		
		public Node(G content) {
			
			this.content = content;
			
			this.previous = this.next = null;
		}
		
		G content;
		
		Node<G> next;
		
		Node<G> previous;

		@Override
		public String toString() {
			
			return "(" + (content != null ? content : "null") + ")"; 
		}
		
	}
	
	//	Cada anillo tiene un nodo vacío fijo como referencia
	//
	private final Node<T> reference = new Node<T>(null);
	
	//	Las clases que heredan de ésta pueden consultar la referencia
	//	pero no cambiarla.
	//
	protected Node<T> reference() {
		
		return reference;
	}
	
	//	Número de elementos en el anillo
	//
	protected int nElements;
	
	/**
	 * Dirección en sentido horario en el anillo.
	 */
	protected static final int FORWARD = 0;
	
	/**
	 * Dirección en sentido contrario al sentido horario.
	 */
	protected static final int BACKWARDS = 1;

	
	protected Ring() {
		
		this.nElements = 0;
		this.reference.next = reference;
		this.reference.previous = reference;
		this.reference.content = null;
	}
	
	/**
	 * Indica el número de elementos en este anillo. (Si el anillo está vacío el tamaño es 0)
	 * 
	 * @return cuántos elementos hay en este anillo.
	 */
	public int size() {

		return nElements;
	}
	
	/**
	 * Busca el primer nodo con el contenido dado, a partir de otro nodo.
	 * 
	 * A partir del siguiente nodo al dado, en la dirección indicada, busca
	 * un nodo cuyo contenido sea igual (Object#equals) al dado. Si no lo
	 * encuentra con una vuelta completa al anillo, devolverá el nodo inicial.
	 * 
	 * Por ejemplo, con un anillo 'x' como el siguiente:
	 * 
	 * 	> (R) <> A <> B <> C <> D <> (R) <
	 * 
	 * se tendría que
	 * 
	 * 	z = x.find(Ring.FORWARD, x.reference(), B)
	 * 
	 * devuelve el nodo que contiene B, y a continuación
	 * 
	 *  x.find(Ring.FORWARD, z, B)
	 * 
	 * devuelve z ya que tras visitar C, D, A vuelve a z sin encontrar otra B.
	 * 
	 * @param direction en qué dirección buscar.
	 * @param start nodo que marca el inicio de la búsqueda, pero no se incluye en ella.
	 * @param target contenido a buscar.
	 * @return un nodo encontrado, o el inicio 'start' si no se encuentra el dato.
	 */
	protected Node<T> find(int direction, Node<T> start, T target) {
		Node<T> aux = start;
		if(target == null){
			return reference;
		}else{
			if(direction == BACKWARDS){
				aux = aux.previous;
			}else if(direction == FORWARD){
				aux = aux.next;
			}
			while(!aux.equals(start)){
				if(target.equals(aux.content)){
					return aux;
				}
				if(direction == BACKWARDS){
					aux = aux.previous;
				}else if(direction == FORWARD){
					aux = aux.next;
				}
			}
			return start;
		}
		
	}

	/**
	 * Indica si el anillo es vacío.
	 * 
	 * @return está este anillo vacío? 
	 */
	public boolean isEmpty() {
		return nElements == 0;
	}
	
	/**
	 * Elimina el nodo dado de este anillo.
	 * 
	 * Si el nodo indicado fuera el nodo cabecera, no hace nada.
	 * 
	 * @param n un nodo en este anillo.
	 */
	protected void remove(Node<T> n) {
		
		if(!n.equals(reference)){
			Node<T> aux = reference.next;
			while(!aux.equals(reference)){
				if(aux.equals(n)){
					aux.previous.next = aux.next;
					aux.next.previous = aux.previous;
					this.nElements--;
					break;
				}
				aux = aux.next;
			}
		}
	}
	
	/**
	 * Inserta un nuevo nodo en el anillo.
	 * 
	 * La inserción se realiza al lado del nodo base indicado,
	 * en la dirección dada. Por ejemplo:
	 * 
	 * 	x = > (R) <> (R) <
	 * 	x.insert(x.reference(), Ring.FORWARD, A)
	 *
	 * 	x = > (R) <> A <> (R) <
	 *  x.insert(x.reference(), Ring.FORWARD, B)
	 *  
	 *  x = > (R) <> B <> A <> (R) <
	 *  x.insert(x.reference(), Ring.BACKWARDS, C)
	 *  
	 *  x = > (R) <> B <> A <> C <> (R) <
	 *  x.insert(x.find(Ring.FORWARD, x.reference(), A), Ring.BACKWARDS, D)
	 *  
	 *  x = > (R) <> B <> D <> A <> C <> (R) <
	 *  
	 * @param base nodo referencia para la inserción
	 * @param direction en qué dirección insertar
	 * @param element dato para el nuevo nodo
	 */
	protected void insert(Node<T> base, int direction, T element) {
		Node<T> aux  = new Node<T>(element);
		if(this.isEmpty()){		//Si está vacio el next y el previous del reference pasan a ser el nuevo nodo
			aux.next = base;
			aux.previous = base;
			base.next = aux;
			base.previous = aux;
		}else{
			if(direction == FORWARD){
				aux.next = base.next;
				aux.previous = base;
				base.next.previous = aux;
				base.next = aux;
			}else if(direction == BACKWARDS){
				aux.next = base;
				aux.previous = base.previous;
				base.previous.next = aux;
				base.previous = aux;
			}
		}
		
		this.nElements++;
	}
	
	@Override
	public String toString() {
		
		Node<T> i;
		
		StringBuffer rx = new StringBuffer();
		
		rx.append("> (R) <> ");
		
		i = reference.next; while (i != reference) {
			
			rx.append(i.content);
			rx.append(" <> ");
			
			i = i.next;
		}
		
		rx.append("(R) <");
		
		return rx.toString();
	}
	
	/**
	 * Genera una cadena con el contenido de cada elemento.
	 * 
	 * Se genera al concatenar el resultado de invocar {@link Object#toString()}
	 * en cada elemento, en un recorrido del anillo en la dirección dada.
	 *  
	 * Por ejemplo, si x es un anillo con T String:
	 * 
	 *  x = > (R) <> B <> D <> A <> C <> (R) <
	 * 
	 * se tiene que
	 * 
	 *  x.toSequence(Ring.FORWARD) es "BDAC"
	 *  
	 * y
	 * 
	 *  x.toSequence(Ring.BACKWARDS) es "CADB"
	 *  
	 * @return cadena con el contenido del anillo.
	 */
	public String toSequence(int direction) {
		Node<T> aux = reference;
		String cadena = "";
		if(direction == BACKWARDS){
			aux = aux.previous;
		}else if(direction == FORWARD){
			aux = aux.next;
		}
		while(!aux.equals(reference)){
			cadena = cadena + aux.content.toString();
			if(direction == BACKWARDS){
				aux = aux.previous;
			}else if(direction == FORWARD){
				aux = aux.next;
			}
		}
		return cadena;
	}
	
	/**
	 * Iterador sobre los elementos del anillo.
	 * 
	 * En el constructor se indica en qué sentido se desea
	 * recorrer el anillo.
	 * 
	 * @author profesor
	 *
	 */
	private class IteratorImpl implements Iterator<T> {
		private int direccion;
		private Node<T> actual;
		
		public IteratorImpl(int direction) {
			this.direccion = direction;
			if(direction == BACKWARDS){
				actual = reference.previous;
			}else if(direction == FORWARD){
				actual = reference.next;
			}
		}
		
		@Override
		public boolean hasNext() {
			return actual != reference;
		}

		@Override
		public T next() {
			T element;
			if(!hasNext()){
				throw new NoSuchElementException();
			}else{
				element = actual.content;
				if(this.direccion == BACKWARDS){
					actual = actual.previous;
				}else if(this.direccion == FORWARD){
					actual = actual.next;
				}
			}
			return element;
		}

		@Override
		public void remove() {

			throw new UnsupportedOperationException();
		}
		
	}

	@Override
	public Iterator<T> iterator() {
		
		return new IteratorImpl(Ring.FORWARD);
	}

	public Iterator<T> forwardIterator() {
		
		return new IteratorImpl(Ring.FORWARD);
	}

	public Iterator<T> backwardsIterator() {
		
		return new IteratorImpl(Ring.BACKWARDS);
	}

	/**
	 * Compara anillos, elemento a elemento.
	 * 
	 * Serán iguales si contienen los mismos elementos y en el mismo
	 * orden. Las comparaciones entre elementos serán con
	 * {@link Object#equals(Object)}.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj instanceof Ring<?>) {
			
			Ring<?> other = (Ring<?>) obj;
			
			if(this.nElements != other.nElements){
				return false;
			}
			if(!this.toSequence(BACKWARDS).equals(other.toSequence(BACKWARDS))){
				return false;
			}
			return true;
		}
				
		return false;
	}
	
}
