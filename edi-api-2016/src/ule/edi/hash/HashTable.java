package ule.edi.hash;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * TAD tabla hash.
 * 
 * Almacena pares (k,v) de clave (k, key) y valor (v, value). Las claves
 * no se pueden repetir en la tabla, de forma que si se guarda (k,v) y
 * la clave ya está presente, se modifica su valor asociado.
 *  
 * El cómo almacenar los valores asociados a sus respectivas claves depende
 * de cada implementación, pero siempre se basará en el cómputo del valor
 * de una <tt>función hash</tt> del objeto dado como clave.
 * 
 * No se permiten valores ni claves <tt>null</tt>.
 * 
 * @author profesor
 *
 * @param <K> tipo de las claves
 * @param <V> tipo de los valores
 */
public interface HashTable<K, V> {

	/**
	 * Almacena el par (clave,valor) en esta tabla.
	 * 
	 * Si ya existe una entrada para esa clave, sustituye
	 * su valor.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(K key, V value);
	
	/**
	 * Indica si esta tabla contiene la clave dada.
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(K key);
	
	/**
	 * Devuelve el valor asociado a la clave indicada.
	 * 
	 * @param key
	 * @return
	 * @throws NoSuchElementException si no existe el par solicitado
	 */
	public V get(K key);
	
	/**
	 * Elimina el par (clave,valor) de esta tabla.
	 * 
	 * Si no existe, no hace nada.
	 * 
	 * @param key
	 */
	public void remove(K key);
	
	/**
	 * Indica cuántos pares (clave,valor) hay en esta tabla.
	 * 
	 * @return
	 */
	public long size();
	
	/**
	 * Devuelve una lista de todas las claves en esta tabla.
	 * 
	 * NOTA El orden no importa, pero será consistente entre este
	 * método y {@link HashTable#values()}.
	 * 
	 * @return
	 */
	public List<K> keys();
	
	/**
	 * Devuelve una lista de todos los valores en esta tabla.
	 * 
	 * NOTA El orden no importa, pero será consistente entre este
	 * método y {@link HashTable#keys()}.
	 * 
	 * @return
	 */
	public List<V> values();
}
