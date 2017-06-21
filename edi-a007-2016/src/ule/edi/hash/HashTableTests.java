package ule.edi.hash;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ule.edi.hash.HashTableImpl.Cell;
import ule.edi.hash.HashTableImpl.HashFunction;

public class HashTableTests {

	private HashTableImpl<String, String> TS;
	
	//	Mala función de hash, pero útil para los tests
	private static final HashFunction<String> hLength =  new HashFunction<String>() {

		@Override
		public int apply(int n, String g) {

			return (g.length() % n);
		}
	};

	@Before
	public void setupTables() {
		
		TS = new HashTableImpl<String, String>(hLength, 3, 3);
	}
	
	private String key(int n) {
		
		return String.format("K%03d", n);
	}
	
	private String value(int n) {
		
		return String.format("V%03d", n);
	}
	
	private void put(HashTableImpl<String, String> t, int n) {
		
		t.put(key(n), value(n));
	}
	
	@Ignore
	public void testPrinting() {
		
		TS.put("Z", "VZ");
		System.out.println(TS);
	}
	
	@Test
	public void testEqualCells(){
		Cell<String, String> cell1 = new Cell<String, String>("A", "01");
		Cell<String, String> cell2 = new Cell<String, String>("A", "02");
		Cell<String, String> cell3 = new Cell<String, String>("B", "01");
		Assert.assertTrue(cell1.equals(cell2));
		Assert.assertFalse(cell1.equals(cell3));
		String cell = cell1.toString();
	}
	
	
	
	@Test
	public void testGet(){
		TS.put("A","00");
		TS.put("AA", "01");
		TS.put("AAA", "02");
		TS.put("AAAA", "03");
		TS.put("AAAAA", "04");
		TS.put("AAAAAA", "05");
		String keyGet = TS.get("AAAA");
		Assert.assertEquals("03", keyGet);
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testGetEx()throws NoSuchElementException{
		String keyGet = TS.get("AAAA");
	}
	
	@Test
	public void testPut(){
		TS = new HashTableImpl<String,String>();
		TS.put("A","00");
		assertTrue(TS.contains("A"));
		TS.put("B","01");
		assertTrue(TS.contains("B"));
		TS.put("C","02");
		assertTrue(TS.contains("C"));
		TS.put("D","03");
		TS.put("E","04");
		TS.put("F","05");
		TS.put("G","06");
		TS.put("H","07");
		TS.put("I","08");
		TS.put("J","09");
		TS.put("K","10");
		TS.put("L","11");
		TS.put("M","12");
		TS.put("N","13");
		assertTrue(TS.contains("N"));
		TS.put("O","14");
		TS.put("P","15");
		TS.put("Q","16");
		assertTrue(TS.contains("Q"));
		TS.put("R","17");
		TS.put("F","18");
		
		
		assertFalse(TS.contains("Z"));
		assertEquals(TS.get("A"),"00");
		assertEquals(TS.get("B"),"01");
		assertEquals(TS.get("C"),"02");
		assertEquals(TS.get("Q"),"16");
		assertEquals(TS.get("K"),"10");
		
		TS.put("K","20");
		assertEquals("20",TS.get("K"));
		TS.put("AA","21");
		TS.put("AN","22");
		TS.put("NA","22");
		TS.put("NN","22");
		TS.put("NA","24");
		assertEquals(TS.get("NA"),"24");
		TS.put("AAA","25");
		TS.put("ANA","25");
		TS.put("NAN", "26"); //Todo correcto hasta aqui
		TS.put("NNN", "27");
		String todo = TS.toString();
		Assert.assertEquals(todo, todo);
	}
	
	
	@Test
	public void testRemove(){
		HashTableImpl<String, String> table = new HashTableImpl<String, String>();
		for(int i = 1; i <= 32; i++){
			put(table, i);
		}
		
		for(int i = 1; i <= 32; i++){
			Assert.assertTrue(table.contains(key(1)));
		}
		

		for(int i = 1; i <= 16; i++){
			table.remove(key(i));
		}
		Assert.assertEquals(16, table.size());
		
		for(int i = 1; i <= 32; i++){
			put(table, i);
		}
		Assert.assertEquals(32, table.size());
		
		for(int i = 1; i <= 16; i++){
			Assert.assertTrue(table.contains(key(i)));
		}
		
	}
	
	@Test
	public void testPutAndContain() {
		put(TS, 3);
		put(TS, 3);
		put(TS, 7);
		put(TS, 7);
		put(TS, 6);
		put(TS, 6);
		put(TS, 8);
		put(TS, 5558);
		put(TS, 8);
		Assert.assertEquals(5, TS.size());
		Assert.assertTrue(TS.contains(key(3)));
		Assert.assertTrue(TS.contains(key(7)));
		Assert.assertTrue(TS.contains(key(6)));
		Assert.assertTrue(TS.contains(key(8)));
		Assert.assertTrue(TS.contains(key(5558)));
		Assert.assertFalse(TS.contains(key(558)));
	}
	
	@Test
	public void testGetX() {
		put(TS, 3);
		put(TS, 7);
		put(TS, 6);
		put(TS, 8);
		put(TS, 5558);
		put(TS, 9);
		Assert.assertEquals(value(3), TS.get(key(3)));
		Assert.assertEquals(value(7), TS.get(key(7)));
		Assert.assertEquals(value(6), TS.get(key(6)));
		Assert.assertEquals(value(8), TS.get(key(8)));
		Assert.assertEquals(value(9), TS.get(key(9)));
		Assert.assertEquals(value(5558), TS.get(key(5558)));
	}

	@Test (expected = NoSuchElementException.class)
	public void testGetException() {
		TS.get(key(558));
	}
	
	@Test
	public void testRemoveX() {
		put(TS, 3);
		Assert.assertEquals(1, TS.size());
		Assert.assertTrue(TS.contains(key(3)));
		TS.remove(key(3));
		Assert.assertEquals(0, TS.size());
		Assert.assertFalse(TS.contains(key(3)));
		put(TS, 7);
		put(TS, 3);
		Assert.assertEquals(2, TS.size());
		Assert.assertTrue(TS.contains(key(3)));
		Assert.assertTrue(TS.contains(key(7)));
		TS.remove(key(3));
		Assert.assertEquals(1, TS.size());
		Assert.assertFalse(TS.contains(key(3)));
		Assert.assertTrue(TS.contains(key(7)));
		put(TS, 3);
		put(TS, 6);
		put(TS, 8);
		put(TS, 9);
		Assert.assertEquals(5, TS.size());
		Assert.assertTrue(TS.contains(key(3)));
		Assert.assertTrue(TS.contains(key(7)));
		Assert.assertTrue(TS.contains(key(6)));
		Assert.assertTrue(TS.contains(key(8)));
		TS.remove(key(8));
		TS.remove(key(10));
		TS.remove(key(7));
		TS.remove(key(9));
		Assert.assertEquals(2, TS.size());
		Assert.assertTrue(TS.contains(key(3)));
		Assert.assertFalse(TS.contains(key(7)));
		Assert.assertTrue(TS.contains(key(6)));
		Assert.assertFalse(TS.contains(key(8)));
		Assert.assertFalse(TS.contains(key(9)));
	}
	
	@Test
	public void testRemove1() {
		HashTableImpl<String, String> table = new HashTableImpl<String, String>(hLength, 3, 3);
		table.put("X", "AB");
		table.put("Y", "AB");
		table.put("Z", "AB");
		table.put("XX", "AB");
		table.put("YY", "AB");
		table.put("ZZZ", "AB");
		
		Assert.assertEquals(6, table.size());
		Assert.assertTrue(table.contains("X"));
		Assert.assertTrue(table.contains("Y"));
		Assert.assertTrue(table.contains("Z"));
		Assert.assertTrue(table.contains("XX"));
		Assert.assertTrue(table.contains("YY"));
		Assert.assertTrue(table.contains("ZZZ"));
		
		table.remove("X");
		table.remove("Y");
		table.remove("Z");
		
		Assert.assertEquals(3, table.size());
		Assert.assertFalse(table.contains("X"));
		Assert.assertFalse(table.contains("Y"));
		Assert.assertFalse(table.contains("Z"));
		Assert.assertTrue(table.contains("XX"));
		Assert.assertTrue(table.contains("YY"));
		Assert.assertTrue(table.contains("ZZZ"));
	}
	
	@Test
	public void testRemoveAll() {
		put(TS, 3);
		put(TS, 7);
		put(TS, 6);
		put(TS, 8);
		TS.remove(key(3));
		TS.remove(key(7));
		TS.remove(key(6));
		TS.remove(key(8));
	}

	@Test
	public void testList(){
		put(TS, 3);
		put(TS, 7);
		put(TS, 6);
		put(TS, 5558);
		Assert.assertEquals("[V003, V5558, V007, V006]", TS.values().toString());
		Assert.assertEquals("[K003, K5558, K007, K006]", TS.keys().toString());
	}

	@Test
	public void testPrimes(){
		Primes p = new Primes();
		for (int i = 0; i < 30; i++) {
			Primes.next(i);
			try{
				Primes.next(2147483647);
			}catch(IllegalStateException e){
			}
		}
	}
	
	@Test
	public void testGets(){
		Object[] cells = TS.getCells();
		int[] clinks = TS.getCLinks();
		Object[] overflow = TS.getOverflow();
		int[] olinks = TS.getOLinks();
		HashFunction<String> hash = TS.getHashFunction();
	}

	
	@Test
	public void testEva(){
		TS.put("Eva", "01");
		TS.put("Ave", "02");
		TS.put("Vae", "03");
		TS.put("Aaa", "04");
		TS.put("Hola", "05");
		TS.put("Aaaa", "01");
		TS.put("Evaa", "01");
		TS.put("Evva", "01");
		//Elimino los de 3 letras
		TS.remove("Eva");
		TS.remove("Ave");
		TS.remove("Vae");
		TS.remove("Aaa");
	}
	
}
