package ule.edi.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ule.edi.EmptyCollectionException;

public class UnorderedArrayListTests {

	private UnorderedArrayList<Number> lN1;
	
	@Before
	public void setupLists() {
		
		this.lN1 = new UnorderedArrayList<Number>();
	}
	
	//	JUnit no executa los tests anotados con @Ignore
	
	@Test
	public void testConstructor(){
		
		UnorderedListADT<Integer> lN2 = new UnorderedArrayList<>(Integer.valueOf(7), Integer.valueOf(5), Integer.valueOf(8));
		
		Assert.assertEquals("[7, 5, 8]", lN2.toString());
		
	}
	
	@Test
	public void testDistinct() {
	
		UnorderedListADT<Number> d2lN1 = UnorderedArrayList.distinct(lN1);
		Assert.assertEquals(0, d2lN1.size());
		
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(1);
		lN1.addToRear(2);
		lN1.addToRear(1);
		
		Assert.assertEquals(5L, lN1.size());
		
		//	distinct es un método estático de 'utilidad'
		UnorderedListADT<Number> dlN1 = UnorderedArrayList.distinct(lN1);
		
		Assert.assertEquals(2L, dlN1.size());
		Assert.assertTrue(dlN1.contains(1));
		Assert.assertTrue(dlN1.contains(2));
	}
	
	@Test
	public void testRangedIterator() {
	
		UnorderedListADT<Number> lN2;
		UnorderedListADT<Number> lN3;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.rangedIterator(1, 16, 4);
		lN2 = UnorderedArrayList.listWith(aux);
		Assert.assertEquals("[0, 4, 8, 12]", lN2.toString());	
		
		Iterator<Number> aux1 = lN1.rangedIterator(1, 16, 3);
		lN3 = UnorderedArrayList.listWith(aux1);
		Assert.assertEquals("[0, 3, 6, 9, 12, 15]", lN3.toString());
		
			
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testRangedIteratortException1() throws UnsupportedOperationException {
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.rangedIterator(1, 16, 4);
		lN2 = UnorderedArrayList.listWith(aux);
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
		lN2 = UnorderedArrayList.listWith(aux);
		aux.next();
	}
	
	@Test
	public void testSkippingIterator() {
	
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 13; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.skippingIterator();
		lN2 = UnorderedArrayList.listWith(aux);
		Assert.assertEquals("[0, 2, 4, 6, 8, 10, 12]", lN2.toString());		
		
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testSkippingIteratortException1() throws UnsupportedOperationException {
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.skippingIterator();
		lN2 = UnorderedArrayList.listWith(aux);
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
		lN2 = UnorderedArrayList.listWith(aux);
		aux.next();
	}
	
	
	@Test
	public void testDefaultIterator() {
	
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 10; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.iterator();
		lN2 = UnorderedArrayList.listWith(aux);
		Assert.assertEquals("[0, 1, 2, 3, 4, 5, 6, 7, 8, 9]", lN2.toString());		
		
		
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testDefaultIteratortException1() throws UnsupportedOperationException {
		UnorderedListADT<Number> lN2;
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}

		//	listWith es un método estático
		Iterator<Number> aux = lN1.iterator();
		lN2 = UnorderedArrayList.listWith(aux);
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
		lN2 = UnorderedArrayList.listWith(aux);
		aux.next();
	}
	
	@Test
	public void testReverse(){
		for (int i = 0; i < 10; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		
		UnorderedListADT<Number> dlN1 = UnorderedArrayList.reverse(lN1);
		
		Assert.assertEquals(10, dlN1.size());
		Assert.assertTrue(dlN1.contains(1));
		Assert.assertTrue(dlN1.contains(2));
		Assert.assertEquals("[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]", dlN1.toString());
	}
	
	
	@Test
	public void testGetElementAt(){
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		
		Assert.assertEquals(Integer.valueOf(1), lN1.getElementAt(2));
		
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void testGetElementAtException() throws IndexOutOfBoundsException{
		lN1.getElementAt(-1);
		lN1.getElementAt(8);
	}
	
	@Test
	public void testGetFirst() throws EmptyCollectionException {
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		
		Assert.assertEquals(Integer.valueOf(0), lN1.getFirst());
	}
	
	@Test (expected = EmptyCollectionException.class)
	public void testGetFirstException() throws EmptyCollectionException {
		lN1.getFirst();
	}
	
	@Test
	public void testGetLast(){
		try {
			Assert.assertEquals(Integer.valueOf(31), lN1.getLast());
		} catch (EmptyCollectionException e) {
		}
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		
		try {
			Assert.assertEquals(Integer.valueOf(31), lN1.getLast());
		} catch (EmptyCollectionException e) {
		}
		
	}
	@Test (expected = EmptyCollectionException.class)
	public void testGetLastException() throws EmptyCollectionException {
		lN1.getLast();
	}

	
	@Test
	public void testEmpty(){
		Assert.assertTrue(lN1.isEmpty());
		Assert.assertEquals(0, lN1.size());
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		
		Assert.assertEquals(32, lN1.size());
		Assert.assertFalse(lN1.isEmpty());
		
	}
	
	@Test
	public void testContains(){
		
		Assert.assertFalse(lN1.contains(Integer.valueOf(3)));
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		
		Assert.assertTrue(lN1.contains(Integer.valueOf(3)));
		Assert.assertFalse(lN1.contains(Integer.valueOf(80)));
	}
	

	
	@Test
	public void testAddToFront(){
		for (int i = 0; i < 8; i++) {
			lN1.addToFront(Integer.valueOf(i));
		}
		
		Assert.assertEquals("[7, 6, 5, 4, 3, 2, 1, 0]", lN1.toString());
		
	}
	
	@Test
	public void testRemoveFirst() throws EmptyCollectionException{
		
		for (int i = 0; i < 8; i++) {
			lN1.addToFront(Integer.valueOf(i));
		}
		
		lN1.removeFirst();
		
		Assert.assertEquals("[6, 5, 4, 3, 2, 1, 0]", lN1.toString());
		
	}
	
	@Test (expected = EmptyCollectionException.class)
	public void testRemoveFirstException() throws EmptyCollectionException {
		lN1.removeFirst();
	}
	
	@Test
	public void testRemoveLast() throws EmptyCollectionException{
		
		for (int i = 0; i < 8; i++) {
			lN1.addToFront(Integer.valueOf(i));
		}
		lN1.removeLast();
		Assert.assertEquals("[7, 6, 5, 4, 3, 2, 1]", lN1.toString());
		
	}
	
	@Test (expected = EmptyCollectionException.class)
	public void testRemoveLastException() throws EmptyCollectionException {
		lN1.removeLast();
	}
	
	@Test
	public void testRemoveX() throws EmptyCollectionException{

		for (int i = 0; i < 8; i++) {
			lN1.addToFront(Integer.valueOf(i));
		}
		
		lN1.remove(Integer.valueOf(4));
		
		
		Assert.assertEquals("[7, 6, 5, 3, 2, 1, 0]", lN1.toString());
		
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
	
	@Test
	public void testRemoveElementAtX(){
		
		for (int i = 0; i < 8; i++) {
			lN1.addToFront(Integer.valueOf(i));
		}
		
		lN1.removeElementAt(4);
		
		Assert.assertEquals("[7, 6, 5, 3, 2, 1, 0]", lN1.toString());
		
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
	
	
	@Test
	public void testReplaceElementAt(){
		
		for (int i = 0; i < 32; i++) {
			lN1.addToRear(Integer.valueOf(i));
		}
		lN1.replaceElementAt(4, Integer.valueOf(8));
		Assert.assertEquals(Integer.valueOf(8), lN1.getElementAt(4));
		
	}
	
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

}
