package ule.edi.recursive;

/**
 * Lista de elementos.
 * 
 * @author profesor
 *
 * @param <T> tipo de elementos en la lista.
 */
public interface SimpleListADT<T> {

	/**
	 * Añade un elemento al final de la lista.
	 * 
	 * @param content el elemento a añadir.
	 */
	public void addFirst(T content);
	
	/**
	 * Añade un elemento al final de la lista.
	 * 
	 * @param content el elemento a añadir.
	 */
	public void addToRear(T content);
	
	/**
	 * Indica si los elementos en la lista están en orden ascendente.
	 * 
	 * Para comparar los datos se hará un 'cast' a <code>Comparable<T></code> y se
	 * usará entonces el método {@link Comparable#compareTo(Object)}.
	 * 
	 * La lista vacía está en orden ascendente por definición; una lista no vacía
	 * estará en orden ascendente sii cada uno de sus elementos es menor o igual que
	 * su siguiente.
	 * 
	 * @return <tt>true</tt> si la lista tiene los elementos en orden ascendente o es vacia, <tt>false</tt> en cualquier otro caso.
	 */
	public boolean isAscend();

	/**
	 * Indica si los elementos en la lista están en orden descendente.
	 * 
	 * Para comparar los datos se hará un 'cast' a <code>Comparable<T></code> y se
	 * usará entonces el método {@link Comparable#compareTo(Object)}.
	 * 
	 * La lista vacía está en orden descendente por definición; una lista no vacía
	 * estará en orden descendente sii cada uno de sus elementos es mayor o igual que
	 * su siguiente.
	 * 
	 * @return <tt>true</tt> si la lista tiene los elementos en orden descendente o es vacia, <tt>false</tt> en cualquier otro caso.
	 */
	public boolean isDescend();
	
	/**
	 * Devuelve una lista con el contenido de ésta en orden inverso.
	 * 
	 * Si esta lista es vacía, devuelve una lista vacía.
	 * 
	 * Por ejemplo, sea x una lista de caracteres ['A', 'B', 'C', 'C', 'D'], se tiene:
	 * 
	 *  x.reverse() es ['D', 'C', 'C', 'B', 'A']
	 *  
	 * @return otra lista con los mismos elementos en orden inverso.
	 */
	public SimpleListADT<T> reverse();
	
	/**
	 * Elimina de esta lista los n primeros elementos.
	 * 
	 * Si n es mayor que el número de elementos en esta lista, quedará vacía.
	 * 
	 * @param n número de elementos a eliminar.
	 */
	public void dropElements(int n);
	
	/**
	 * Genera una lista con todos los elementos de ésta por duplicado.
	 * 
	 * Por ejemplo, sea x una lista de caracteres ['A', 'B', 'C', 'C', 'D'], se tiene:
	 * 
	 *  z = x.repeatAllElements()
	 * 
	 * 	z es ['A', 'A', 'B', 'B', 'C', 'C', 'C', 'C', 'D', 'D']
	 * 
	 * y x no se modifica.
	 *  
	 * @return lista con elementos duplicados.
	 */
	public SimpleListADT<T> repeatAllElements();
	
	/**
	 * Genera una lista con los elementos de ésta, los n primeros elementos duplicados.
	 * 
	 * Por ejemplo, sea x una lista de caracteres ['A', 'B', 'C', 'D'], se tiene:
	 * 
	 *  z = x.repeatNElements(2)
	 * 
	 * 	z es ['A', 'A', 'B', 'B', 'C', 'D']
	 * 
	 * y como antes, x no se modifica.
	 *  
	 * @return lista con elementos duplicados.
	 */
	public SimpleListADT<T> repeatNElements(int n);
	
	/**
	 * Indica si la lista es vacía.
	 * 
	 * @return <tt>true</tt> si esta lista es vacía.
	 */
	public boolean isEmpty();
}
