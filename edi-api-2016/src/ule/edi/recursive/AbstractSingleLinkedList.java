package ule.edi.recursive;

public abstract class AbstractSingleLinkedList<T> implements SimpleListADT<T> {

	protected static class Node<G> {

		Node(G content) {

			this.content = content;

			this.next = null;
		}

		G content;

		Node<G> next;

		@Override
		public String toString() {

			return "(" + (content != null ? content : "null") + ")";
		}

	}

	protected Node<T> first = null;


	/**
	 * Compara la estructura de esta lista (this) con la de otra.
	 * 
	 * Dos listas tendrán la misma estructura si tienen el mismo número de elementos. 
	 * 
	 * @return <tt>true</tt> si las listas this y other tienen la misma estructura, <tt>false</tt> en caso contrario.
	 */
	public abstract boolean isEqualStructure(AbstractSingleLinkedList<T> other);	

	
	@Override
	public void addFirst(T content) {

		Node<T> n = new Node<T>(content);

		n.next = this.first;

		this.first = n;
	}

	
	@Override
	public boolean isEmpty() {
		
		return (first == null);
	}

	@Override
	public String toString() {

		if (first != null) {

			StringBuffer rx = new StringBuffer();

			rx.append("[");

			Node<T> i = first;

			while (i.next != null) {

				rx.append(i.content);
				rx.append(", ");

				i = i.next;
			}

			rx.append(i.content + "]");

			return rx.toString();

		} else {

			return "[]";
		}
	}
	
}
