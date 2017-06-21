package ule.edi.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ule.edi.EmptyCollectionException;

public class UnorderedSingleLinkedListTests {

	private UnorderedSingleLinkedList<Number> lN1;
	
	@Before
	public void setupLists() {
		
		this.lN1 = new UnorderedSingleLinkedList<Number>();
	}
	
	//	JUnit no executa los tests anotados con @Ignore
	
	/*
	 * TEST DISTICT
	 */
	@Test
	public void testDistinct() {
	
		UnorderedListADT<Number> dlN1 = UnorderedSingleLinkedList.distinct(lN1);
		Assert.assertEquals("[]", dlN1.toString());
		
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(1);
		
		Assert.assertEquals(5L, lN1.size());
		
		//	distinct es un método estático de 'utilidad'
		UnorderedListADT<Number> dlN2 = UnorderedSingleLinkedList.distinct(lN1);
		
		Assert.assertEquals(2L, dlN2.size());
		Assert.assertTrue(dlN2.contains(1));
		Assert.assertTrue(dlN2.contains(2));
	}
	

	
	/*
	 * TEST SIZE
	 */
	@Test
	public void testSize(){
		for (int i = 0; i < 10; i++) {
			lN1.addToFront(Integer.valueOf(i));
		}
		Assert.assertEquals(10, lN1.size());	
	}
	
	/*
	 * TEST TO STRING
	 */
	@Test
	public void testToString() throws EmptyCollectionException{
		for (int i = 0; i < 10; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		Assert.assertEquals("(9)", lN1.getUltimoNodo().toString());
	}
	
	/*
	 * TEST REMOVE
	 */
	@Test
	public void testRemove() throws EmptyCollectionException{
		for (int i = 0; i < 10; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		lN1.removeFirst();
		Assert.assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", lN1.toString());	
		lN1.removeLast();
		Assert.assertEquals("[1, 2, 3, 4, 5, 6, 7, 8]", lN1.toString());
		lN1.remove(Integer.valueOf(3));
		Assert.assertEquals("[1, 2, 4, 5, 6, 7, 8]", lN1.toString());
		lN1.removeElementAt(3);
		Assert.assertEquals("[1, 2, 5, 6, 7, 8]", lN1.toString());
		lN1.removeElementAt(2);
		Assert.assertEquals("[1, 5, 6, 7, 8]", lN1.toString());
		lN1.removeElementAt(1);
		Assert.assertEquals("[5, 6, 7, 8]", lN1.toString());
		lN1.removeLast();
		lN1.removeLast();
		lN1.removeLast();
		Assert.assertEquals("[5]", lN1.toString());
		lN1.removeLast();
		Assert.assertEquals("[]", lN1.toString());
		
	}
	
	/*
	 * TESTS EXCEPCIONES REMOVE
	 */
	@Test (expected = EmptyCollectionException.class)
	public void testRemoveFirstException() throws EmptyCollectionException {
		lN1.removeFirst();
	}
	
	@Test (expected = EmptyCollectionException.class)
	public void testRemoveLastException() throws EmptyCollectionException {
		lN1.removeLast();
	}
	
	@Test (expected = EmptyCollectionException.class)
	public void testRemoveXException() throws EmptyCollectionException {
		lN1.remove(Integer.valueOf(4));
	}
	
	//Test de la excepcion que salta cuando la lista no contiene ese elemento
	@Test (expected = NoSuchElementException.class)
	public void testRemoveXException2() throws NoSuchElementException, EmptyCollectionException {
		for (int i = 0; i < 8; i++) {
			lN1.addToFront(Integer.valueOf(i));
		}
		
		lN1.remove(Integer.valueOf(50));
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void testRemoveElementAtXException() throws IndexOutOfBoundsException {
		for (int i = 0; i < 8; i++) {
			lN1.addToFront(Integer.valueOf(i));
		}
		lN1.removeElementAt(-1);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void testRemoveElementAtXException2() throws IndexOutOfBoundsException {
		for (int i = 0; i < 8; i++) {
			lN1.addToFront(Integer.valueOf(i));
		}
		lN1.removeElementAt(10);
	}
	
	/*
	 * TEST GET
	 */
	@Test
	public void testGet() throws EmptyCollectionException{
		for (int i = 0; i < 10; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		Assert.assertEquals(Integer.valueOf(0), lN1.getFirst());
		Assert.assertEquals(Integer.valueOf(9), lN1.getLast());
		Assert.assertEquals(Integer.valueOf(5), lN1.getElementAt(6));
		Assert.assertEquals(Integer.valueOf(0), lN1.getElementAt(1));
	}
	
	/*
	 * TESTS EXCEPCIONES GET
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void testGetElementAtException() throws IndexOutOfBoundsException{
		lN1.getElementAt(-1);
		lN1.getElementAt(8);
	}
	
	@Test (expected = EmptyCollectionException.class)
	public void testGetFirstException() throws EmptyCollectionException {
		lN1.getFirst();
	}
	
	@Test (expected = EmptyCollectionException.class)
	public void testGetLastException() throws EmptyCollectionException {
		lN1.getLast();
	}
	
	/*
	 * TEST CONTAINS
	 */
	@Test
	public void testContains(){
		for (int i = 0; i < 10; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		Assert.assertTrue(lN1.contains(Integer.valueOf(3)));
		Assert.assertFalse(lN1.contains(Integer.valueOf(30)));
	}
	
	@Test
	public void testRemoveAll() throws EmptyCollectionException{
		for (int i = 0; i < 10; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		for (int i = 0; i < 10; i++) {
			lN1.remove(Integer.valueOf(i));
		}
	}
	
	/*
	 * TEST REVERSE
	 */
	@Test
	public void testReverse(){
		UnorderedListADT<Number> dlN1 = UnorderedSingleLinkedList.reverse(lN1);
		Assert.assertEquals("[]", dlN1.toString());
		for (int i = 0; i < 5; i++) {
			lN1.addToRear(Integer.valueOf(i));
			lN1.addToRear(Integer.valueOf(i));
		}
		UnorderedListADT<Number> dlN2 = UnorderedSingleLinkedList.reverse(lN1);
		Assert.assertEquals("[4, 4, 3, 3, 2, 2, 1, 1, 0, 0]", dlN2.toString());
		
		
	}
	
	
	
	/*
	 * TEST REPLACE ELEMENT AT
	 */
	@Test
	public void testReplaceElementAt(){
		
		for (int i = 0; i < 8; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		lN1.replaceElementAt(4, Integer.valueOf(8));
		Assert.assertEquals("[0, 1, 2, 8, 4, 5, 6, 7]", lN1.toString());
		lN1.replaceElementAt(1, Integer.valueOf(8));
		Assert.assertEquals("[8, 1, 2, 8, 4, 5, 6, 7]", lN1.toString());
		
	}
	
	/*
	 * TESTS EXCEPCIONES REPLACE
	 */
	@Test (expected = IndexOutOfBoundsException.class)
	public void testReplaceElementAtXException() throws IndexOutOfBoundsException {
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		lN1.replaceElementAt(-1, Integer.valueOf(8));
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void testReplaceElementAtXException2() throws IndexOutOfBoundsException {
		for (int i = 0; i < 8; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		lN1.replaceElementAt(9, Integer.valueOf(8));
	}
	

	/*
	 * TEST RANGED ITERATOR
	 */
	@Test
	public void testRangedIterator() {
	
		UnorderedListADT<Number> lN2;
		UnorderedListADT<Number> lN3;
		UnorderedListADT<Number> lN4;
		
		for (int i = 0; i < 15; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//Caso normal
		Iterator<Number> aux = lN1.rangedIterator(1, 20, 4);
		lN2 = UnorderedSingleLinkedList.listWith(aux);
		Assert.assertEquals("[0, 4, 8, 12]", lN2.toString());
		//Caso en el que no empieza en 1
		Iterator<Number> aux2 = lN1.rangedIterator(3, 20, 4);
		lN3 = UnorderedSingleLinkedList.listWith(aux2);
		Assert.assertEquals("[2, 6, 10, 14]", lN3.toString());
		//Caso en el que empieza en un numero mas grande que el tamaño de la lista
		Iterator<Number> aux3 = lN1.rangedIterator(16, 20, 4);
		lN4 = UnorderedSingleLinkedList.listWith(aux3);
		Assert.assertEquals("[]", lN4.toString());
		
	}	
	
	/*
	 * TESTS EXCEPCIONES RANGED ITERATOR
	 */
	
	@Test (expected = UnsupportedOperationException.class)
	public void testRangedIteratortException1() throws UnsupportedOperationException {
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.rangedIterator(1, 16, 4);
		lN2 = UnorderedSingleLinkedList.listWith(aux);
		aux.remove();
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testRangedIteratortException2() throws NoSuchElementException {
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.rangedIterator(1, 16, 4);
		lN2 = UnorderedSingleLinkedList.listWith(aux);
		aux.next();
	}
	
	/*
	 * TEST DEFAULT ITERATOR
	 */
	@Test
	public void testDefaultIterator() {
	
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 8; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		lN2 = UnorderedSingleLinkedList.listWith(lN1.iterator());
		Assert.assertEquals("[0, 1, 2, 3, 4, 5, 6, 7]", lN2.toString());		
		
	}	
	
	/*
	 * TESTS EXCEPCIONES DEFAULT ITERATOR
	 */
	@Test (expected = UnsupportedOperationException.class)
	public void testDefaultIteratortException1() throws UnsupportedOperationException {
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.iterator();
		lN2 = UnorderedSingleLinkedList.listWith(aux);
		aux.remove();
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testDefaultteratortException2() throws NoSuchElementException {
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.iterator();
		lN2 = UnorderedSingleLinkedList.listWith(aux);
		aux.next();
	}
	
	/*
	 * TEST SKIPPING ITERATOR
	 */
	@Test
	public void testSkippingIterator() {
	
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 13; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.skippingIterator();
		lN2 = UnorderedSingleLinkedList.listWith(aux);
		Assert.assertEquals("[0, 2, 4, 6, 8, 10, 12]", lN2.toString());		
		
	}
	
	/*
	 * TESTS EXCEPCIONES SKIPPING ITERATOR
	 */
	@Test (expected = UnsupportedOperationException.class)
	public void testSkippingIteratortException1() throws UnsupportedOperationException {
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.skippingIterator();
		lN2 = UnorderedSingleLinkedList.listWith(aux);
		aux.remove();
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testSkippingIteratortException2() throws NoSuchElementException {
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.skippingIterator();
		lN2 = UnorderedSingleLinkedList.listWith(aux);
		aux.next();
	}
	
	@Test
	public void testConstructor(){
		UnorderedListADT<Number> lN2 = new UnorderedSingleLinkedList<Number>(Integer.valueOf(7), Integer.valueOf(5), Integer.valueOf(8));
		
		Assert.assertEquals("[7, 5, 8]", lN2.toString());
	}
	
	
}
