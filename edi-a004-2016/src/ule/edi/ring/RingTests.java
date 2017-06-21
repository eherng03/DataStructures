package ule.edi.ring;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Test;

public class RingTests {

	private Ring<Integer> range(int a, int b) {
		
		Ring<Integer> rx = new Ring<Integer>();
		for (int i = a; i <= b; ++i) {
			rx.insert(rx.reference(), Ring.BACKWARDS, i);
		}
		
		return rx;
	}
	
	@Test
	public void testEqualsRingEmpty() {
		
		Ring<Integer> x = new Ring<Integer>();
		Ring<Integer> z = new Ring<Integer>();
		
		Assert.assertTrue(x.equals(z));
		Assert.assertTrue(z.equals(x));
		z.insert(z.reference(), Ring.BACKWARDS, 8);
		Assert.assertFalse(z.equals(x));
		Assert.assertEquals(0, x.size());
	
	}

	@Test
	public void testRemove() {
		
		Ring<Integer> x = range(1, 4);
		x.remove(x.reference());
		Assert.assertEquals("1234", x.toSequence(Ring.FORWARD));
		x.remove(x.find(Ring.FORWARD, x.reference(), 1));
		Assert.assertEquals("234", x.toSequence(Ring.FORWARD));
		
		Assert.assertEquals(3, x.size());
	}
	
	@Test
	public void testNodoToString(){
		Ring<Integer> x = new Ring<Integer>();
		Assert.assertEquals("(null)", x.reference().toString());
	}
	
	@Test
	public void testFind(){
		Ring<Integer> x = range(1, 4);
		Assert.assertEquals(x.reference(), x.find(Ring.FORWARD, x.reference(), x.reference().content));
		Assert.assertEquals("(2)", x.find(Ring.BACKWARDS, x.reference(), 2).toString());
		Assert.assertEquals("(2)", x.find(Ring.FORWARD, x.reference().next, 2).toString());
		Assert.assertEquals("(3)", x.find(Ring.BACKWARDS, x.reference().previous.previous, 3).toString());
		Assert.assertEquals("(3)", x.find(Ring.FORWARD, x.reference().previous.previous, 3).toString());
		Assert.assertEquals("(null)" , x.find(Ring.FORWARD, x.reference(), 8).toString());
	}
	
	@Test
	public void testInsert(){
		
		Ring<Integer> rx = new Ring<Integer>();
		for (int i = 1; i <= 5; ++i) {
			rx.insert(rx.reference(), Ring.FORWARD, i);
		}
		Assert.assertEquals("54321", rx.toSequence(Ring.FORWARD));
		Assert.assertEquals("12345", rx.toSequence(Ring.BACKWARDS));
		
		Ring<Integer> rx1 = new Ring<Integer>();
		for (int i = 1; i <= 5; ++i) {
			rx1.insert(rx1.reference(), Ring.BACKWARDS, i);
		}
		Assert.assertEquals("54321", rx1.toSequence(Ring.BACKWARDS));
	}
	
	@Test
	public void testToString(){
		Ring<Integer> rx = new Ring<Integer>();
		for (int i = 1; i <= 5; ++i) {
			rx.insert(rx.reference(), Ring.FORWARD, i);
		}
		Assert.assertEquals("> (R) <> 5 <> 4 <> 3 <> 2 <> 1 <> (R) <",  rx.toString());
	}
	@Test
	public void testIterador(){
		Ring<Integer> rx = new Ring<Integer>();
		for (int i = 1; i <= 5; ++i) {
			rx.insert(rx.reference(), Ring.FORWARD, i);
		}
		Iterator<Integer> iter = rx.iterator();
		Iterator<Integer> iterBack = rx.backwardsIterator();
		Iterator<Integer> iterFor = rx.forwardIterator();
		String cadena = "";
		while(iter.hasNext()){
			cadena = cadena + iter.next().toString();
		}
		Assert.assertEquals("54321", cadena);
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testIteratortException1() throws NoSuchElementException{
		Ring<Integer> rx = new Ring<Integer>();
		for (int i = 1; i <= 5; ++i) {
			rx.insert(rx.reference(), Ring.FORWARD, i);
		}
		Iterator<Integer> iter = rx.iterator();
		while(iter.hasNext()){
			iter.next();
		}
		iter.next();
	}
	
	@Test (expected = UnsupportedOperationException.class)
	public void testIteratortException2() throws UnsupportedOperationException{
		Ring<Integer> rx = new Ring<Integer>();
		for (int i = 1; i <= 5; ++i) {
			rx.insert(rx.reference(), Ring.FORWARD, i);
		}
		Iterator<Integer> iter = rx.iterator();
		iter.remove();
	}
	
	
}
