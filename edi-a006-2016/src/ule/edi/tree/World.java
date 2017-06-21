package ule.edi.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * En cada nodo se almacena una lista de entidades, cada una con su tipo y
 * cardinalidad. Ver {@link Entity}.
 * 
 * Si se codifica "bajar por la izquierda" como "L" y
 * "bajar por la derecha" como "R", el camino desde un 
 * nodo N hasta un nodo M (en uno de sus sub-árboles) será la
 * cadena de Ls y Rs que indica cómo llegar desde N hasta M.
 *
 * Se define también el camino vacío desde un nodo N hasta
 * él mismo, como cadena vacía.
 * 
 * Por ejemplo, el mundo:
 * 
 * {[F(1)], {[F(1)], {[D(2), P(1)], ∅, ∅}, {[C(1)], ∅, ∅}}, ∅}
 * 
 * o lo que es igual:
 * 
 * [F(1)]
 * |  [F(1)]
 * |  |  [D(2), P(1)]
 * |  |  |  ∅
 * |  |  |  ∅
 * |  |  [C(1)]
 * |  |  |  ∅
 * |  |  |  ∅
 * |  ∅
 * 
 * contiene un bosque (forest) en "", otro en "L", dos dragones y una princesa en "LL" y
 * un castillo en "LR".
 * 
 * @author profesor
 *
 */
public class World extends AbstractBinaryTreeADT<LinkedList<Entity>> {

	/**
	 * Devuelve el mundo al que se llega al avanzar a la izquierda.
	 * 
	 * @return
	 */
	protected World travelLeft() {
		
		return (World) leftSubtree;
	}

	private void setLeft(World left) {
		
		this.leftSubtree = left;
	}
	
	/**
	 * Devuelve el mundo al que se llega al avanzar a la derecha.
	 * 
	 * @return
	 */
	protected World travelRight() {
		
		return (World) rightSubtree;
	}

	private void setRight(World right) {
		
		this.rightSubtree = right;
	}
	
	private World() {
		
		//	Crea un mundo vacío
		this.content = null;
		
		this.leftSubtree = this.rightSubtree = null;
	}
	
	public static World createEmptyWorld() {
		
		return new World();
	}
	
	/**
	 * Inserta la entidad indicada en este árbol.
	 * 
	 * La inserción se produce en el nodo indicado por la dirección; todos
	 * los nodos recorridos para alcanzar aquél que no estén creados se
	 * inicializarán con una entidad 'bosque'.
	 * 
	 * La dirección se supondrá correcta, compuesta de cero o más Ls y Rs.
	 * 
	 * Dentro de la lista del nodo indicado, la inserción de nuevas entidades
	 * se realizará al final, como último elemento.
	 * 
	 * Por ejemplo, en un árbol vacío se pide insertar un 'dragón' en la
	 * dirección "LL". El resultado final será:
	 * 
     * [F(1)]
     * |  [F(1)]
     * |  |  [D(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  ∅
     * |  ∅
     * 
     * La dirección "" indica la raíz, de forma que insertar un 'guerrero' en
     * "" en el árbol anterior genera:
     * 
     * [F(1), W(1)]
     * |  [F(1)]
     * |  |  [D(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  ∅
     * |  ∅
     * 
     * La inserción tiene en cuenta la cardinalidad, de forma que al volver a
     * insertar un guerrero en "" se tiene:
     * 
     * [F(1), W(2)]
     * |  [F(1)]
     * |  |  [D(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  ∅
     * |  ∅
     *  
	 * @param address dirección donde insertar la entidad.
	 * @param e entidad a insertar.
	 */
	public void insert(String address, Entity e) {
		char[] pasos = address.toCharArray();
		World actual = this;
		for(int i = 0; i <= pasos.length - 1; i++){
			if((actual.content == null || actual.isEmpty()) && i < pasos.length){
				actual.content = new LinkedList<Entity>();
				actual.setLeft(new World());
				actual.setRight(new World());
				actual.content.add(new Entity(Entity.FOREST));
			}
			if(pasos[i] == 'L'){
				actual = actual.travelLeft();
			}else if(pasos[i] == 'R'){
				actual = actual.travelRight();
			}			
		}
		//if(actual == null){
			//actual = new World();
		//}
		if(actual.isEmpty()){		//Si el nodo al que he llegado esta vacio, inserto la entidad
			actual.content = new LinkedList<Entity>();
			actual.content.add(e);
			actual.setLeft(new World());
			actual.setRight(new World());
		}else{
			int index = actual.content.indexOf(e);
			if(index == -1){		//si no encuentro ninguna entidad igual en ese mundo la inserto al final
				actual.content.add(e);
			}else{					//Busco la entidad que hay en ese mundo y aumento su contador
				actual.content.get(index).setCount(actual.content.get(index).getCount() + e.getCount());
			}
		}
	}
	
	/**
	 * Indica cuántos castillos hay a no más de la distancia indicada.
	 * 
	 * Pasar de un nivel del árbol al siguiente supone avanzar una distancia
	 * unitaria.
	 * 
	 * Por ejemplo, en el mundo:
	 * 
     * [C(1)]
     * |  [C(1)]
     * |  |  ∅
     * |  |  ∅
     * |  [D(1)]
     * |  |  [C(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  [C(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * 
     * hay 1 castillo a no más de 0 de distancia, 2 a no más de 1 y 4 a no
     * más de 2.
     * 
	 * @param distance límite de distancia
	 * @return número de castillos a no más de esa distancia.
	 */
	public long countCastlesCloserThan(long distance) {
		if(!this.isEmpty()){
			return contadorCastillosrec(distance, new Entity(Entity.CASTLE));
		}
		return 0;
	}
	
	private long contadorCastillosrec(long distance, Entity e) {
		long contador = 0;
		int index = -1;
		if(this.content == null){
			return 0;
		}else if(distance == 0){
			index = this.content.indexOf(e);
			if(index != -1){
				return this.content.get(index).getCount();
			}
			return 0;
		}else{
			distance--;
			//Aumento el contador con los castillos de este nivel y paso al siguiente por la derecha y por la izquierda
			index = this.content.indexOf(e);
			if(index != -1){
				contador =  this.content.get(index).getCount();
			}
			return contador + travelLeft().contadorCastillosrec(distance, e) + travelRight().contadorCastillosrec(distance, e);
		}
	}

	/**
	 * Indica cuántas entidades del tipo dado hay en un nivel.
	 * 
	 * @param type tipo de entidad.
	 * @param n nivel a considerar.
	 * @return cuántas entidades de ese tipo hay en ese nivel.
	 */
	public long countAtLevel(int type, int n) {
		if(isEmpty()){ 
			return 0;
		}
		int index = content.indexOf(new Entity(type)); 
		if(n == 1){ //He llegado al nivel 
			if(index != -1){ 
				return content.get(index).getCount();
			}else{ 
				return 0;
			}
		}else{ 
			return travelLeft().countAtLevel(type, n - 1) + travelRight().countAtLevel(type, n - 1);
		}
	}
		
			

	/**
	 * Localiza la n-ésima princesa en in-orden.
	 * 
	 * Por ejemplo, en este mundo:
	 * 
     * [F(1)]
     * |  [F(1)]
     * |  |  [D(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  [C(1)]
     * |  |  |  [P(1)]
     * |  |  |  |  ∅
     * |  |  |  |  [P(2)]
     * |  |  |  |  |  ∅
     * |  |  |  |  |  ∅
     * |  |  |  ∅
     * |  ∅
     * 
     * la primera princesa está en 
     * 
     * 	[L, R, L]
     * 
     * y la segunda y tercera están ambas en
     * 
     * 	[L, R, L, R]
     * 
	 * @param n posición relativa entre las princesas en in-orden, n >= 1
	 * @param rx camino del nodo que contiene a la princesa encontrada.
	 * @return <code>true</code> si la encontró.
	 */
	public boolean findNPrincessInorden(long n, LinkedList<Character> rx) {
		long[] na = {n};
		return findNPrincessRec(na, rx);
	}
		
	private boolean findNPrincessRec(long[] na, LinkedList<Character> rx) {
		boolean hayPrincesa = false;
		if(na[0] <= 0){
				return true;
		}else if(this == null || this.isEmpty()){
			return false;
		}else{
			//Miro la rama de la izquierda
			rx.add('L');
			hayPrincesa = this.travelLeft().findNPrincessRec(na, rx);
			if(hayPrincesa){
				return true;
			}else{
				rx.removeLast();
			}
			//Miro si en el nodo actual hay alguna princesa, y si la hay se la resto al contador
			int index = this.content.indexOf(new Entity(Entity.PRINCESS));
			if(index != -1){
				na[0] = na[0] - this.content.get(index).getCount();		//le resto el numero de princesas que hay en ese nodo
			}
			if(na[0] <= 0){		//Si el contador ha llegado a 0, la hemos encontrado
				return true;
			}
			//Miro en la rama derecha
			rx.add('R');
			hayPrincesa = this.travelRight().findNPrincessRec(na, rx);
			if(hayPrincesa){
				return true;
			}else{
				rx.removeLast();
			}   	
		}
		return false;
	}

	/**
	 * Busca el primer dragón en anchura y devuelve cuántos nodos hay antes.
	 * 
	 * Los nodos vacíos no se cuentan. Por ejemplo, aquí devolvería 2:
	 * 
     * [F(1)]
     * |  [F(1)]
     * |  |  [D(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  [C(1)]
     * |  |  |  [P(1)]
     * |  |  |  |  ∅
     * |  |  |  |  [P(2)]
     * |  |  |  |  |  ∅
     * |  |  |  |  |  ∅
     * |  |  |  ∅
     * |  ∅
     * 
	 * Si no hubiera ningún dragón, devolverá el número de nodos no vacíos
	 * en el mundo.
	 * 
	 * @return el número de nodos no vacíos que ha recorrido antes del dragón.
	 */
	public long findFirstDragonInBreadthOrder() {
		if(this.content == null || this.isEmpty()){
			return 0;
		}
		Entity dragon = new Entity(Entity.DRAGON);
		LinkedList<World> listaRecorrer = new LinkedList<World>();
		long listaRecorridos = 0;
		World aux;
		listaRecorrer.add(this);
		while(!listaRecorrer.isEmpty()){
			aux = listaRecorrer.getFirst();
			listaRecorrer.removeFirst();
			if(aux != null){
				if(aux.content != null){
					int index = aux.content.indexOf(dragon);
					if(index != -1){		//Ha encontrado un dragon, le resto su nodo
						return listaRecorridos;
					}else{
						listaRecorridos++;
						if(!aux.travelLeft().isEmpty()){
							listaRecorrer.add(aux.travelLeft());
						}
						if(!aux.travelRight().isEmpty()){
							listaRecorrer.add(aux.travelRight());
						}
					}
				}
			}
		}
		return listaRecorridos ;		//Ya que va a contar el nodo del dragon
	}

}
