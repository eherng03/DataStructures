package ule.edi.recursive;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SingleLinkedListImplTests {


	private SimpleListADT<String> lS;

	private SimpleListADT<String> lSABC;
	
	@Before
	public void setupLists() {
	
		lS = new SingleLinkedListImpl<String>();
		
		lSABC = new SingleLinkedListImpl<String>("A", "B", "C");
	}

	@Test
	public void testConstructDefault() {
		
		Assert.assertEquals("[]", lS.toString());
	}

	@Test
	public void testConstructWithContent() {
		
		Assert.assertEquals("[A, B, C]", lSABC.toString());
	}
	
	@Test
	public void testAddFirst() {
		
		Assert.assertEquals("[]", lS.toString());
		
		lS.addFirst("A");
		Assert.assertEquals("[A]", lS.toString());
		
		lS.addFirst("B");
		Assert.assertEquals("[B, A]", lS.toString());
		
		lS.addFirst("C");
		Assert.assertEquals("[C, B, A]", lS.toString());
	}
	
	@Test
	public void testAddTorear(){
		lS.addToRear("E");
		lS.addToRear("V");
		lS.addToRear("A");
		Assert.assertEquals("[E, V, A]", lS.toString());
	}
	
	@Test
	public void testIsDescend(){
		Assert.assertTrue(lS.isDescend());
		lS.addToRear("E");
		lS.addToRear("D");
		lS.addToRear("C");
		Assert.assertTrue(lS.isDescend());
		Assert.assertFalse(lS.isAscend());
	}
	
	@Test
	public void testIsAscend(){
		Assert.assertTrue(lS.isAscend());
		lS.addToRear("a");
		lS.addToRear("b");
		lS.addToRear("c");
		Assert.assertTrue(lS.isAscend());
		Assert.assertFalse(lS.isDescend());
	}
	
	@Test
	public void testIsEqualStructure(){
		//caso con alguna lista vacia, o con las dos
		SingleLinkedListImpl<String> lS1 = new SingleLinkedListImpl<String>();
		//ambas vacias
		Assert.assertTrue(((SingleLinkedListImpl<String>) lS).isEqualStructure((AbstractSingleLinkedList<String>) lS1));
		//una vacia y la otra no
		Assert.assertFalse(((SingleLinkedListImpl<String>) lS).isEqualStructure((AbstractSingleLinkedList<String>) lSABC));
		Assert.assertFalse(((SingleLinkedListImpl<String>) lSABC).isEqualStructure((AbstractSingleLinkedList<String>) lS));
		
		lS.addToRear("a");
		lS.addToRear("b");
		lS.addToRear("c");
		Assert.assertTrue(((SingleLinkedListImpl<String>) lS).isEqualStructure((AbstractSingleLinkedList<String>) lSABC));
		lS.addToRear("c");
		Assert.assertFalse(((SingleLinkedListImpl<String>) lS).isEqualStructure((AbstractSingleLinkedList<String>) lSABC));
	}
	
	@Test
	public void testDropElements(){
		lS.dropElements(3);
		Assert.assertEquals("[]", lS.toString());
		lS.dropElements(0);
		Assert.assertEquals("[]", lS.toString());
		lS.addToRear("a");
		lS.addToRear("b");
		lS.addToRear("c");
		lS.addToRear("d");
		lS.addToRear("e");
		lS.addToRear("f");
		lS.dropElements(3);
		Assert.assertEquals("[d, e, f]", lS.toString());
	}
	
	@Test
	public void testRepeatAll(){
		lS.repeatAllElements();
		lS.addToRear("a");
		lS.addToRear("b");
		lS.addToRear("c");
		lS.addToRear("d");
		lS.addToRear("e");
		lS.addToRear("f");
		SimpleListADT<String> repetido = lS.repeatAllElements();
		Assert.assertEquals("[a, a, b, b, c, c, d, d, e, e, f, f]", repetido.toString());
	}
	
	@Test
	public void testRepeatN(){
		lS.repeatNElements(3);
		lS.repeatNElements(0);
		lS.addToRear("a");
		lS.addToRear("b");
		lS.addToRear("c");
		lS.addToRear("d");
		lS.addToRear("e");
		lS.addToRear("f");
		SimpleListADT<String> repetido = lS.repeatNElements(3);
		Assert.assertEquals("[a, a, b, b, c, c, d, e, f]", repetido.toString());
	}
	
	@Test
	public void testReverse(){
		lS.reverse();
		lS.addToRear("a");
		lS.addToRear("b");
		lS.addToRear("c");
		lS.addToRear("d");
		lS.addToRear("e");
		lS.addToRear("f");
		SimpleListADT<String> reverse = lS.reverse();
		Assert.assertEquals("[f, e, d, c, b, a]", reverse.toString());
	}
	
	
	
}
