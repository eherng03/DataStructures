package ule.edi.hash;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.omg.PortableServer.ServantRetentionPolicyValue;

import ule.edi.list.ListADT;

public class HashTableImpl<K, V> implements HashTable<K, V> {
	
	/**
	 * Función que indica el índice en el array de celdas para una clave.
	 * 
	 * @author profesor
	 *
	 * @param <K>
	 */
	public static interface HashFunction<K> {
		
		/**
		 * Indica el índice en un array de tamaño n.
		 * 
		 * @param n tamaño del array destino.
		 * @param g clave del par (clave,valor) a almacenar.
		 * @return índice para almacenar el par (clave,valor).
		 */
		public int apply(int n, K g);
	};

	/**
	 * Valor para los 'enlaces' a siguiente similar a null en listas enlazadas
	 */
	static final int NILL = -1;
	

	static class Cell<K, V> {
		
		public Cell(K key, V value) {
		
			this.key = key;
			
			this.value = value;
		}
		
		@Override
		public boolean equals(Object obj) {
		
			if (this == obj) {
				
				return true;
			}
			
			if (obj instanceof Cell) {
				
				Cell<?, ?> other = (Cell<?, ?>) obj;
				
				return (key.equals(other.key));
			}
			
			return false;
		}


		@Override
		public String toString() {
			
			return "(" + key + ", " + value + ")";
		}


		K key;
		
		V value;
	};
	
	//	Array de celdas, primario
	private Object[] cells;
	
	//	Enlaces
	private int[] clinks;
	
	
	//	Colisiones
	private int firstAvailable;
	
	private Object[] overflow;
	
	private int[] olinks;
	
	//en la tabla hash
	private long nElements;
	
	//	por defecto
	private HashFunction<K> hash = new HashFunction<K>() {

		@Override
		public int apply(int n, K g) {

			return (g == null ? 0 : (g.hashCode() % n));
		}
	};

	//	Gestión de arrays de Cell<K, V>
	//
	@SuppressWarnings("unchecked")
	private Cell<K, V> getCell(Object[] from, int n) {
		
		return (Cell<K, V>) from[n];
	}
	
	private void setCell(Cell<K, V> c, int n, Object[] into) {
		
		into[n] = c;
	}

	private boolean isAvailable(Object[] in, int n) {
		
		return (in[n] == null);
	}


	static final int DEFAULT_CELL_ARRAY_SIZE = 5;
	
	static final int DEFAULT_OVERFLOW_ARRAY_SIZE = 5;
	
	/**
	 * Construye una tabla vacía del tamaño por defecto.
	 * 
	 * Una tabla vacía sería (con 5 celdas, 5 overflow):
	 * 
	 * En cells[] nulls, en clinks[] NILL:
	 * 
     * Celdas:
     * 00000: null >> -
     * 00001: null >> -
     * 00002: null >> -
     * 00003: null >> -
     * 00004: null >> -
     * 
     * En overflow[] nulls, en olinks[] índices:
     * 
     * Colisiones: primero disponible: 00000
     * 00000: null >> 1
     * 00001: null >> 2
     * 00002: null >> 3
     * 00003: null >> 4
     * 00004: null >> -
     * 
     * Los valores de olink construyen junto con el índice del primero
     * disponible, una 'lista enlazada' de posiciones disponibles en desbordamiento.
	 */
	public HashTableImpl() {
		this.cells = new Object[DEFAULT_CELL_ARRAY_SIZE];
		for(int i = 0; i < cells.length; i++){
			this.cells[i] = null;
		}
	
		this.clinks = new int[DEFAULT_CELL_ARRAY_SIZE];
		for(int i = 0; i < clinks.length; i++){
			this.clinks[i] = NILL;
		}
		
		this.firstAvailable = 0;
		
		this.overflow = new Object[DEFAULT_OVERFLOW_ARRAY_SIZE];
		for(int i = 0; i < overflow.length; i++){
			this.overflow[i] = null;
		}
		
		this.olinks = new int[DEFAULT_OVERFLOW_ARRAY_SIZE];
		for(int i = 0; i < olinks.length; i++){
			this.olinks[i] = i + 1;
		}
		this.olinks[olinks.length-1] = NILL;
		
		this.nElements = 0;
	}
	
	/**
	 * Construye una tabla vacía con parámetros específicos.
	 * 
	 * @param h función hash a utilizar
	 * @param n número de celdas
	 * @param m tamaño de la zona de desbordamiento
	 */
	public HashTableImpl(HashFunction<K> h, int n, int m) {
		this.hash = h;
		
		this.cells = new Object[n];
		for(int i = 0; i < cells.length; i++){
			this.cells[i] = null;
		}
		this.clinks = new int[n];
		for(int i = 0; i < clinks.length; i++){
			this.clinks[i] = NILL;
		}
		
		this.firstAvailable = 0;
		
		this.overflow = new Object[m];
		for(int i = 0; i < overflow.length; i++){
			this.overflow[i] = null;
		}
		this.olinks = new int[m];
		for(int i = 0; i < olinks.length; i++){
			this.olinks[i] = i+1;
		}
		this.olinks[olinks.length-1] = NILL;
		
		this.nElements = 0;
	}

	/*
	 * Si al ir a insertar en desbordamiento no queda espacio disponible,
	 * se produce un 'rehash' con nuevas estructuras de datos de tamaño
	 * mayor. Se supondrá que el nuevo tamaño para un array 'x' será
	 * 
	 * 	Primes.next(2 * x.length)
	 * 
	 * para las celdas y para la zona de desbordamiento.
	 * 
	 * Por ejemplo, una tabla con 3 celdas y 6 de desbordamiento se
	 * transforma a una tabla de 7 celdas y 13 posiciones para
	 * desbordamiento.
	 * 
	 *  (non-Javadoc)
	 * @see ule.edi.hash.HashTable#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(K key, V value) {
		
		
		int posicion = hash.apply(cells.length, key);
		Cell<K, V> cellN = new Cell<K, V>(key, value);
		
		//En el caso de que este vacia, o la key sea la misma pero con distinto value, se actualiza
		if(isAvailable(cells, posicion)){		
			setCell(cellN, posicion, cells);
			nElements++;
			
		}else if((getCell(cells, posicion).key.equals(key))){
			setCell(cellN, posicion, cells);
		}else{
			//Si esta llena expando capacidad
			if(estaLLeno()){
				this.expandir();
			}
			posicion = hash.apply(cells.length, key);
			boolean insertado = false;
			for(int i = 0; i < overflow.length; i++){
				if(!isAvailable(overflow, i)){	//si la celda esta llena
					if(getCell(overflow, i).key.equals(key)){	//Y contiene la misma clave
						setCell(cellN, i, overflow);
						insertado = true;
					}
				}
			}
			if(insertado == false){		//si no estaba repetida la clave se inserta en firstAvailable
				setCell(cellN, firstAvailable, overflow);
				int aux = olinks[firstAvailable];
				olinks[firstAvailable] = clinks[posicion];	//El enlace de la tabla de overflow pasa a ser el que tenia en clinks
				clinks[posicion] = firstAvailable;
				firstAvailable = aux;
				nElements++;
			}
		}
		
	}

	private boolean estaLLeno() {
		for(int i = 0; i < overflow.length; i++){
			if(isAvailable(overflow, i)){
				return false;
			}
		}
		return true;
	}


	private void expandir() {
		int tamCells = Primes.next(2 * this.cells.length);
		int tamOver = Primes.next(2 * this.overflow.length);
		HashTableImpl<K, V> nuevaTabla = new HashTableImpl<>(hash, tamCells, tamOver);
		for(int i = 0; i < cells.length; i++){		//Inserto las claves de la tabla antigua en la nueva
			if(!isAvailable(cells, i)){
				nuevaTabla.put(getCell(cells, i).key, getCell(cells, i).value);
			}
		}
		for(int i = 0; i < overflow.length; i++){		//Inserto las claves de la tabla de overflow
			if(!isAvailable(overflow, i)){
				nuevaTabla.put(getCell(overflow, i).key, getCell(overflow, i).value);
			}
		}
		this.cells = nuevaTabla.cells;
		this.clinks = nuevaTabla.clinks;
		this.firstAvailable = nuevaTabla.firstAvailable;
		this.hash = nuevaTabla.hash;
		this.nElements = nuevaTabla.nElements;
		this.olinks = nuevaTabla.olinks;
		this.overflow = nuevaTabla.overflow;
	}

	
	@Override
	public boolean contains(K key) {
		int posicion = hash.apply(cells.length, key);
		//Si coincide con la posicion en la que deberia estar...
		if(!isAvailable(cells, posicion) && getCell(cells, posicion).key.equals(key)){	
			return true;
		}else{					
			//Si no buscamos en el array de overflow
			int posOver = this.clinks[posicion];	
			//Sera nill cuando llegue a la primera que colisiono
			while(posOver != NILL){		
				if(!isAvailable(overflow, posOver) && getCell(overflow, posOver).key.equals(key)){	
					return true;
				}else{
					posOver = this.olinks[posOver];
				}
			}
		}
		return false;		//SI no lo a encontrado en los pasos anteriores devuekve false
	}

	
	@Override
	public V get(K key) {
		if(!this.contains(key)){
			throw new NoSuchElementException();
		}else{
			V valor = null;
			int posicion = hash.apply(cells.length, key);
			if(!isAvailable(cells, posicion) && getCell(cells, posicion).key.equals(key)){
				valor = getCell(cells, posicion).value;
			}else{		//Si no he encontrado esa clave en cells busco en overflow
				//Si no buscamos en el array de overflow
				int posOver = clinks[posicion];	
				//Sera nill cuando llegue a la primera que colisiono
				while(posOver != NILL){		
					if(!isAvailable(overflow, posOver) && getCell(overflow, posOver).key.equals(key)){	
						valor = getCell(overflow, posOver).value;
						posOver = NILL;		//Para que pare el while
					}else{
						posOver = this.olinks[posOver];
					}
				}
			}
			return valor;
		}
	}

	@Override
	public void remove(K key) {
		if(this.contains(key)){
			int posicion = hash.apply(cells.length, key);
		
			if(!isAvailable(cells, posicion) && getCell(cells, posicion).key.equals(key)){
				//El elemento a borrar esta en cells
				if(clinks[posicion] == NILL){	//Significa que no ha habido colisiones
					setCell(null, posicion, cells);
					nElements--;
				}else{	
					/* en el caso de que haya habido colision, hay que mover los elementos de la tabla de overflow
					 * Y meter el primero que colisiono en la posicion obtenida con la hash
					 */
					int posOver = clinks[posicion];
					if(olinks[posOver] == NILL && !isAvailable(overflow, posOver)){	//Es decir no hay una lista de colisiones, pero hay elemento
						setCell(getCell(overflow, posOver), posicion, cells);	//Meto en la posicion a borrar el elemento que primero colisiono
						setCell(null, posOver, overflow); 	//Y borro el que estaba en posOver
						olinks[posOver] = firstAvailable;
						clinks[posicion] = NILL;
						firstAvailable = posOver;
					}else{
						int posicionAnterior = posOver;
						int posicionActual = olinks[posOver];
						while(olinks[posicionActual] != NILL){		//Hasta que no lleguemos a la primera colision...
							posicionAnterior = posicionActual;
							posicionActual = olinks[posicionAnterior];
						}
						//olinks[posicionActual] es NILL, la primera colision
						//paso el contenido de dicha posicion a cells
						setCell(getCell(overflow, posicionActual), posicion, cells);
						//borro el contenido en overflow
						setCell(null, posicionActual, overflow);
						//Dejo el Olink como al inicializarlo
						olinks[posicionActual] = firstAvailable; 
						//pongo el anterior elemento, es decir el siguiente que colisiono, con su link a NILL
						olinks[posicionAnterior] = NILL;
						//Actualizo el siguiente valor disponible
						firstAvailable = posicionActual;
					}
					nElements--;
				}
			}else{		
				//el elemento que hay que borrar esta en overflow
				int posActual = NILL;
				int posAnterior = NILL;
				for(int i = 0; i < overflow.length; i++){	//siempre va a encontrarla porque partimos de que lo contiene
					if(!isAvailable(overflow, i)){
						if(getCell(overflow, i).key.equals(key)){
							posActual = i;
							break;
						}
					}
				}
				//buscamos la posicion anterior, la que tenga en olink la actual
				for(int i = 0; i < olinks.length; i++){
					if(olinks[i] == posActual){
						posAnterior = i;
						break;
					}
				}
				if(posAnterior != NILL){	//es decir tiene mas colisiones detras
					//cambio los enlaces
					olinks[posAnterior] = olinks[posActual];
				}else{
					clinks[posicion] = olinks[posActual];
				}
				
				//vacio la celda
				setCell(null, posActual, overflow);
				olinks[posActual] = firstAvailable;
				nElements--;
				firstAvailable = posActual;
				
			}
		}
	}

	@Override
	public String toString() {
		
		StringBuffer rx = new StringBuffer();
		
		rx.append("Celdas:\n");
		for (int n = 0; n < cells.length; ++n) {
			rx.append(String.format("%05d", n) + ": ");
			rx.append((cells[n] != null ? cells[n].toString() : "null"));
			rx.append(" >> " + (clinks[n] != NILL ? clinks[n] : "-"));
			rx.append("\n");
		}
		rx.append("\n");
		
		rx.append("Colisiones: primero disponible: " + String.format("%05d", firstAvailable) + "\n");
		for (int n = 0; n < overflow.length; ++n) {
			rx.append(String.format("%05d", n) + ": ");
			rx.append((overflow[n] != null ? overflow[n].toString() : "null"));
			rx.append(" >> " + (olinks[n] != NILL ? olinks[n] : "-"));
			rx.append("\n");
		}
		rx.append("\n");
		
		return rx.toString();
	}

	@Override
	public long size() {
		return nElements;
	}

	@Override
	public List<K> keys() {
		List<K> listaKeys = new LinkedList<K>();
		for(int i = 0; i < this.cells.length; i++){
			if(!isAvailable(cells, i)){
				listaKeys.add(this.getCell(this.cells, i).key);
			}
		}
		for(int i = 0; i < this.overflow.length; i++){
			if(!isAvailable(overflow, i)){
				listaKeys.add(this.getCell(this.overflow, i).key);
			}
		}
		return listaKeys;
	}

	@Override
	public List<V> values() {
		List<V> listaValues = new LinkedList<V>();
		for(int i = 0; i < this.cells.length; i++){
			if(!isAvailable(cells, i)){
				listaValues.add(this.getCell(this.cells, i).value);
			}
		}
		for(int i = 0; i < this.overflow.length; i++){
			if(!isAvailable(overflow, i)){
				listaValues.add(this.getCell(this.overflow, i).value);
			}
		}
		return listaValues;
	}
	
	//
	//	Para JUnit
	//
	Object[] getCells() {
		
		return cells;
	}
	
	int[] getCLinks() {
		
		return clinks;
	}
	
	Object[] getOverflow() {
		
		return overflow;
	}
	
	int[] getOLinks() {
		
		return olinks;
	}

	HashFunction<K> getHashFunction() {
		
		return hash;
	}
		
}
