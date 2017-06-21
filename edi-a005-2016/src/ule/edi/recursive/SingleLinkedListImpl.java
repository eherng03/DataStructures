package ule.edi.recursive;

public class SingleLinkedListImpl<T> extends AbstractSingleLinkedList<T> {
	
	public SingleLinkedListImpl() {

	}

	@SuppressWarnings("unchecked")
	public SingleLinkedListImpl(T... values) {

		for (T i : values) {

			addToRear(i);
		}
	}

	@Override
	public void addToRear(T content) {
		if(this.isEmpty()){
			this.addFirst(content);
		}else{
			buscarUltimoRec(first).next = new Node<T>(content);
		}
	}
	
	private Node<T> buscarUltimoRec(Node<T> siguiente){
		if(siguiente.next == null){
			return siguiente;
		}else{
			return  buscarUltimoRec(siguiente.next);
		}
		
		
	}

	@Override
	public boolean isAscend() {
		if(this.isEmpty()){
			return true;
		}else{
			return this.isAscendRec(first);
		}
	}
	

	@Override
	public boolean isDescend() {
		if(this.isEmpty()){
			return true;
		}else{
			return this.isDescendRec(first);
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean isAscendRec(Node<T> siguiente){
		if(siguiente.next == null){
			return true;
		}else if(((Comparable<T>)siguiente.content).compareTo(siguiente.next.content) <= 0){
			return isAscendRec(siguiente.next);
		}else{
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	private boolean isDescendRec(Node<T> siguiente){
		if(siguiente.next == null){
			return true;
		}else if(((Comparable<T>)siguiente.content).compareTo(siguiente.next.content) >= 0){
			return isDescendRec(siguiente.next);
		}else{
			return false;
		}
	}

	@Override
	public boolean isEqualStructure(AbstractSingleLinkedList<T> other) {
		if(this.isEmpty() && other.isEmpty()){
			return true;
		}else if(this.isEmpty() || other.isEmpty()){
			return false;
		}else{
			return isEqualRec(this.first, other.first);
		}
	}
	
	private boolean isEqualRec(Node<T> llama, Node<T> other){
		if(llama.next == null && other.next == null){
			return true;
		}else if(llama.next == null || other.next == null){
			return false;
		}else{
			return isEqualRec(llama.next, other.next);
		}
	}

	@Override
	public void dropElements(int n) {
		if(!this.isEmpty() && n != 0){
			this.dropElementsRec(1, this.first, n);
		}
	}
	
	private void dropElementsRec(int nBorrados, Node<T> actual, int n){
		if(actual.next == null || nBorrados == n){
			first = actual.next;
		}else{
			nBorrados++;
			dropElementsRec(nBorrados, actual.next, n);
			
		}
	}

	@Override
	public SimpleListADT<T> reverse() {
		SimpleListADT<T> listaReverse = new SingleLinkedListImpl<>();
		if(!this.isEmpty()){
			return reverseRec(listaReverse, this.first);
		}
		return listaReverse;
		
	}

	private SimpleListADT<T> reverseRec(SimpleListADT<T> listaReverse, Node<T> actual) {
		if(actual.next == null){
			listaReverse.addFirst(actual.content);
			return listaReverse;
		}else{
			listaReverse.addFirst(actual.content);
			return reverseRec(listaReverse, actual.next);
		}
	}

	@Override
	public SimpleListADT<T> repeatAllElements() {
		SimpleListADT<T> listaRepeat = new SingleLinkedListImpl<>();
		if(!this.isEmpty()){
			return repeatAllRec(listaRepeat, this.first);
		}
		return listaRepeat;
	}

	private SimpleListADT<T> repeatAllRec(SimpleListADT<T> listaRepeat, Node<T> actual) {
		if(actual.next == null){
			listaRepeat.addToRear(actual.content);
			listaRepeat.addToRear(actual.content);
			return listaRepeat;
		}else{
			listaRepeat.addToRear(actual.content);
			listaRepeat.addToRear(actual.content);
			return repeatAllRec(listaRepeat, actual.next);
		}
	}

	@Override
	public SimpleListADT<T> repeatNElements(int n) {
		if(n == 0){
			return this;
		}else if(!this.isEmpty()){
			SimpleListADT<T> listaRepeat = new SingleLinkedListImpl<>();
			return repeatNRec(listaRepeat, this.first, n, 1);
		}
		return new SingleLinkedListImpl<>();
	}

	private SimpleListADT<T> repeatNRec(SimpleListADT<T> listaRepeat, Node<T> actual, int n, int numRep) {
		if(actual.next == null || numRep == n){
			listaRepeat.addToRear(actual.content);
			listaRepeat.addToRear(actual.content);
			actual = actual.next;
		}else{
			numRep++;
			listaRepeat.addToRear(actual.content);
			listaRepeat.addToRear(actual.content);
			return repeatNRec(listaRepeat, actual.next, n, numRep);
		}
	
		if(actual != null){
			listaRepeat = ((SingleLinkedListImpl<T>) listaRepeat).introducirElementosRec(actual, listaRepeat);
		}
		return listaRepeat;
	}
	
	private SimpleListADT<T> introducirElementosRec(Node<T> actual, SimpleListADT<T> listaRepeat){
		if(actual.next == null){
			listaRepeat.addToRear(actual.content);
			return listaRepeat;
		}else{
			listaRepeat.addToRear(actual.content);
			return introducirElementosRec(actual.next, listaRepeat);
		}
	}

}
