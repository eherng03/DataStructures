package ule.edi.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

import ule.edi.EmptyCollectionException;

/**
 * Árbol binario de búsqueda (binary search tree, BST).
 * 
 * @author profesor
 * 
 * @param <T>
 *            tipo de la información en cada nodo, comparable.
 */
public class BinarySearchTreeADTImpl<T extends Comparable<? super T>> extends
		AbstractBinaryTreeADT<T> {

	/**
	 * Devuelve el árbol binario de búsqueda izquierdo.
	 * 
	 * @return
	 */
	protected BinarySearchTreeADTImpl<T> getLeftBST() {
		return (BinarySearchTreeADTImpl<T>) leftSubtree;
	}

	private void setLeftBST(BinarySearchTreeADTImpl<T> left) {
		this.leftSubtree = left;
	}
	
	/**
	 * Devuelve el árbol binario de búsqueda derecho.
	 * 
	 * @return
	 */
	protected BinarySearchTreeADTImpl<T> getRightBST() {
		return (BinarySearchTreeADTImpl<T>) rightSubtree;
	}

	private void setRightBST(BinarySearchTreeADTImpl<T> right) {
		this.rightSubtree = right;
	}
	
	/**
	 * Árbol BST vacío
	 */
	public BinarySearchTreeADTImpl() {
		
		setContent(null);
		
		setLeftBST(null);
		setRightBST(null);
	}

	private BinarySearchTreeADTImpl<T> emptyBST() {
		return new BinarySearchTreeADTImpl<T>();
	}
	
	/**
	 * Inserta todos los elementos de una colección en el árbol.
	 * 
	 * @param elements
	 *            valores a insertar.
	 */
	public void insert(Collection<T> elements) {

		//	O todos o ninguno; si alguno es 'null', ni siquiera se comienza a insertar
		for (T i : elements) {
			if (i == null) {
				throw new IllegalArgumentException("No se aceptan elementos nulos");
			}
		}
		
		for (T j : elements) {
			insert(j);
		}
	}

	/**
	 * Inserta todos los elementos de un array en el árbol.
	 * 
	 * @param elements elementos a insertar.
	 */
	public void insert(T ... elements) {
		
		//	O todos o ninguno; si alguno es 'null', ni siquiera se comienza a insertar
		for (T i : elements) {
			if (i == null) {
				throw new IllegalArgumentException("No se aceptan elementos nulos");
			}
		}
		
		for (T j : elements) {
			insert(j);
		}
	}
	
	/**
	 * Inserta (como hoja) un nuevo elemento en el árbol de búsqueda. Si el
	 * elemento ya existe en el árbol NO lo inserta.
	 * 
	 * @param element
	 *            valor a insertar.
	 */
	public void insert(T element) {
		
		if (element == null) {
			throw new IllegalArgumentException("No se aceptan elementos nulos");
		}
		
		if (isEmpty()) {
		
			// Ya no es vacío, ahora es hoja
			setContent(element);
			
			//	Sus hijos son vacíos
			setLeftBST(emptyBST());
			setRightBST(emptyBST());
		} else {
			
			// Compara con el valor de la raíz
			int diff = element.compareTo(content);

			if (diff == 0) {
				// Ya está en el árbol
				return;
			}

			if (diff < 0) {
				// Es menor, irá en el sub-árbol izquierdo
				getLeftBST().insert(element);
			}
			
			if (diff > 0) {
				// Es mayor, irá al sub-árbol derecho
				getRightBST().insert(element);
			}
		}
	}

	/**
	 * Elimina los elementos de la colección del árbol.
	 * 
	 * @param elements
	 *            elementos a eliminar.
	 */
	public void withdraw(Collection<T> elements) {
		for (T e : elements) {
			withdraw(e);
		}
	}

	/**
	 * Elimina los valores en un array del árbol.
	 * 
	 * @param elements valores a eliminar.
	 */
	public void withdraw(T ... elements) {
		for (T e : elements) {
			withdraw(e);
		}
	}
	
	/**
	 * Devuelve el máximo en el árbol.
	 * 
	 * @return máximo valor en el árbol.
	 * @throws EmptyCollectionException 
	 */
	public T findMax() throws EmptyCollectionException {
		//	Inspeccionar con recursividad, bajando por la derecha
		if (!isEmpty()) {
			if (getRightBST().isEmpty()) {
				return getContent();
			} else {
				return getRightBST().findMax();
			}
		} else {
			throw new EmptyCollectionException("No se puede encontrar el máximo de un árbol vacío");
		}
	}
	
	/**
	 * Elimina un elemento del árbol.
	 * 
	 * @param element
	 *            elemento a eliminar.
	 * @throws NoSuchElementException si el elemento a eliminar no está en el árbol           
	 */
	public void withdraw(T element) {

		if (element == null) {
			// Eliminado (no estaba)
			return;
		}

		if (!isEmpty()) {
			// Compara con la información en la raíz
			int diff = element.compareTo(getContent());

			if (diff == 0) {

				// Eliminar este nodo

				// Si es una hoja
				if (getLeftBST().isEmpty() && getRightBST().isEmpty()) {
					setContent(null);
					setLeftBST(null);
					setRightBST(null);
				}
				// Si tiene sub-árbol izquierdo pero no derecho
				else if (!getLeftBST().isEmpty() && getRightBST().isEmpty()) {
					setContent(getLeftBST().getContent());

					setRightBST(getLeftBST().getRightBST());
					setLeftBST(getLeftBST().getLeftBST());
				}
				// Si tiene sub-árbol derecho pero no izquierdo
				else if (getLeftBST().isEmpty() && !getRightBST().isEmpty()) {
					setContent(getRightBST().getContent());
					setLeftBST(getRightBST().getLeftBST());
					setRightBST(getRightBST().getRightBST());

				}
				// // Si tiene sub-árbol izquierdo y derecho
				else if (!getLeftBST().isEmpty() && !getRightBST().isEmpty()) {

					try {
						T searchMax = getLeftBST().findMax(); // busca el mayor
																// de los
																// menores
						// sustituye el elemento que quiere eliminar por el
						// mayor de los menores
						content = searchMax;
						// elimina el mayor de los menores que no tendrá hijo
						// derecho y por tanto termina sin recursividad
						getLeftBST().withdraw(searchMax);
					} catch (EmptyCollectionException e) {
						// No debería ser posible
						throw new IllegalStateException(e);
					}
				}

			}
			// Si no lo encuentra sigue buscando por la rama adecuada
			else {
				if (diff > 0)
					getRightBST().withdraw(element);

				else
					getLeftBST().withdraw(element);
			}
		}
		// si el elemento no está en el árbol
		else
			throw new NoSuchElementException();
	}					
	
	/**
	 * Devuelve el número de árboles vacíos en este árbol.
	 * 
	 * Por ejemplo, sea un árbol "A" {10, {5, ∅, ∅}, {20, ∅, {30, ∅, ∅}}}:
	 * 
	 * 10
	 * |  5
	 * |  |  ∅
	 * |  |  ∅
	 * |  20
	 * |  |  ∅
	 * |  |  30
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * 
	 * el resultado sería 5.
	 * 
	 * @return número de árboles vacíos.
	 */
	public long countEmpty() {
		if(this.isEmpty()){
			return 1;
		}else{
			return this.getLeftBST().countEmpty() + this.getRightBST().countEmpty();
		}
	}
	
	

	/**
	 * Indica el número de elementos en niveles impares.
	 * 
	 * La raíz de éste árbol está en nivel 1.
	 * 
	 * Por ejemplo, sea un árbol "A" {10, {5, ∅, ∅}, {20, ∅, {30, ∅, ∅}}}:
	 * 
	 * 10
	 * |  5
	 * |  |  ∅
	 * |  |  ∅
	 * |  20
	 * |  |  ∅
	 * |  |  30
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * 
	 * el resultado sería 2.
	 * 
	 * @return número de elementos en niveles impares.
	 */
	public long countOddLevelElements() {
		
		return this.contarImparesRec(1);
	}
	
	private long contarImparesRec(int i) {
		if(this.content == null){
			return 0;
		}else{	
			if(i % 2 == 1){
				return 1 + this.getLeftBST().contarImparesRec(i+1) + this.getRightBST().contarImparesRec(i+1);
			}else{
				return this.getLeftBST().contarImparesRec(i+1) + this.getRightBST().contarImparesRec(i+1);
			}
		}
	}

	/**
	 * Acumula en pre-orden, una lista con los pares 'padre-hijo' en este árbol.
	 * 
	 * Por ejemplo, sea un árbol "A":
	 * 
	 * {10, {5, {2, ∅, ∅}, ∅}, {20, ∅, {30, ∅, ∅}}}
	 * 
     * 10
     * |  5
     * |  |  2
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  ∅
     * |  20
     * |  |  ∅
     * |  |  30
     * |  |  |  ∅
     * |  |  |  ∅
     * 
	 * el resultado sería una lista de cadenas:
	 * 
	 * 	[(10,5), (5,2), (10,20), (20,30)]
	 * 
	 * @param buffer lista con el resultado.
	 */
	public void parentChildPairs(List<String> buffer) {
		if(this.content != null){
			if(!this.isEmpty() || this.isLeaf()){
				parentChildPairsRec(buffer);
			}
		}
	}
	
	private void parentChildPairsRec(List<String> buffer) {
		if(!this.leftSubtree.isEmpty()){
			buffer.add("(" + this.content + "," + this.getLeftBST().content + ")");
			this.getLeftBST().parentChildPairsRec(buffer);
		}
		if(!this.rightSubtree.isEmpty()){
			buffer.add("(" + this.content + "," + this.getRightBST().content + ")");
			this.getRightBST().parentChildPairsRec(buffer);
		}
		
		
		
	}

	/**
	 * Devuelve el contenido del nodo alcanzado desde la raíz
	 * de éste árbol, con el camino dado.
	 * 
	 * Si se codifica "bajar por la izquierda" como "0" y
	 * "bajar por la derecha" como "1", el camino desde un 
	 * nodo N hasta un nodo M (en uno de sus sub-árboles) será la
	 * cadena de 0s y 1s que indica cómo llegar desde N hasta M.
     *
     * Se define también el camino vacío desde un nodo N hasta
     * él mismo, como cadena vacía.

	 * Por ejemplo, sea un árbol "A" {10, {5, ∅, ∅}, {20, ∅, {30, ∅, ∅}}}:
	 * 
	 * 10
	 * |  5
	 * |  |  ∅
	 * |  |  ∅
	 * |  20
	 * |  |  ∅
	 * |  |  30
	 * |  |  |  ∅
	 * |  |  |  ∅
	 * 
	 * Entonces se tiene que A.getContentWithPath("11") es 30 y
	 * que A.getContentWithPath("") es 10.
	 * 
	 * @param path camino a seguir desde la raíz.
	 * @return contenido del nodo alcanzado.
	 * @throws NoSuchElementException si el camino no alcanza un nodo en el árbol
	 * @throws IllegalArgumentException si el camino no contiene sólamente 0s y 1s
	 */
	public T getContentWithPath(String path){
		char[] pasos = path.toCharArray();
		BinarySearchTreeADTImpl<T> actual = this;
		if(actual.isEmpty()){
			throw new NoSuchElementException();
		}
		for(int i = 0; i < pasos.length; i++){
			if(pasos[i] == '0'){
				actual = actual.getLeftBST();
			}else if(pasos[i] == '1'){
				actual = actual.getRightBST();
			}else{
				throw new IllegalArgumentException();
			}
			if(actual.isEmpty()){
				throw new NoSuchElementException();
			}
		}
		
		return actual.content;
	}

	
	
}
