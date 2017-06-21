package ule.edi.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BinarySearchTreeADTImplTests {
	
    /*
	* ∅
    */
	private BinarySearchTreeADTImpl<Integer> TE = null;
	
	/*
	* 1
	* |  ∅
	* |  2
	* |  |  ∅
	* |  |  3
	* |  |  |  ∅
	* |  |  |  4
	* |  |  |  |  ∅
	* |  |  |  |  ∅
    */	
	private BinarySearchTreeADTImpl<Integer> T1234 = null;
	
	/*
	* 4
	* |  3
	* |  |  2
	* |  |  |  1
	* |  |  |  |  ∅
	* |  |  |  |  ∅
	* |  |  |  ∅
	* |  |  ∅
	* |  ∅
    */	
	private BinarySearchTreeADTImpl<Integer> T4321 = null;
	
	/*
	* 5
	* |  2
	* |  |  1
	* |  |  |  ∅
	* |  |  |  ∅
	* |  |  3
	* |  |  |  ∅
	* |  |  |  ∅
	* |  8
	* |  |  7
	* |  |  |  ∅
	* |  |  |  ∅
	* |  |  9
	* |  |  |  ∅
	* |  |  |  ∅
    */	
	private BinarySearchTreeADTImpl<Integer> TC3 = null;
	
	/*
	* 10
	* |  5
	* |  |  ∅
	* |  |  ∅
	* |  20
	* |  |  ∅
	* |  |  30
	* |  |  |  ∅
	* |  |  |  ∅
	*/
	private BinarySearchTreeADTImpl<Integer> TEx = null;
	
	private LinkedList<String> lS = null;
	
	@Before
	public void setupBSTs() {
		
		TE = new BinarySearchTreeADTImpl<Integer>();
		
		T1234 = new BinarySearchTreeADTImpl<Integer>();
		T1234.insert(1,2,3,4);
		
		T4321 = new BinarySearchTreeADTImpl<Integer>();
		T4321.insert(4, 3, 2, 1);
		
		TC3 = new BinarySearchTreeADTImpl<Integer>();
		TC3.insert(5, 2, 8, 1, 3, 7, 9);
		
		TEx = new BinarySearchTreeADTImpl<Integer>();
		TEx.insert(10, 20, 30, 5);
		
		lS = new LinkedList<String>();
	}
	
	@Test
	public void testCountEmpty(){
		Assert.assertEquals(1, TE.countEmpty());
		Assert.assertEquals(5, T1234.countEmpty());
		Assert.assertEquals(5, T4321.countEmpty());
		Assert.assertEquals(8, TC3.countEmpty());
		Assert.assertEquals(5, TEx.countEmpty());
	}
	
	@Test
	public void testCountOddLevelElements(){
		Assert.assertEquals(0, TE.countOddLevelElements());
		Assert.assertEquals(2, T1234.countOddLevelElements());
		Assert.assertEquals(2, T4321.countOddLevelElements());
		Assert.assertEquals(5, TC3.countOddLevelElements());
		Assert.assertEquals(2, TEx.countOddLevelElements());
	}
	
	@Test
	public void testGetContentWithPath(){
		Assert.assertEquals(Integer.valueOf(3), T1234.getContentWithPath("11"));
		Assert.assertEquals(Integer.valueOf(1), T4321.getContentWithPath("000"));
		Assert.assertEquals(Integer.valueOf(7), TC3.getContentWithPath("10"));
		Assert.assertEquals(Integer.valueOf(5), TC3.getContentWithPath(""));
		Assert.assertEquals(Integer.valueOf(30), TEx.getContentWithPath("11"));
	}
	
	@Test (expected = NoSuchElementException.class)
	public void testGetContentWithPathException(){
		T1234.getContentWithPath("11010");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testGetContentWithPathException1(){
		T1234.getContentWithPath("3");
	}

	@Test
	public void testParentChildPairs(){
		List<String> buffer = new LinkedList<String>();
		
		TE.parentChildPairs(buffer);
		Assert.assertEquals("[]", buffer.toString());
		buffer.clear();
		
		T1234.parentChildPairs(buffer);
		Assert.assertEquals("[(1,2), (2,3), (3,4)]", buffer.toString());
		buffer.clear();
		
		T4321.parentChildPairs(buffer);
		Assert.assertEquals("[(4,3), (3,2), (2,1)]", buffer.toString());
		buffer.clear();
		
		TC3.parentChildPairs(buffer);
		Assert.assertEquals("[(5,2), (2,1), (2,3), (5,8), (8,7), (8,9)]", buffer.toString());
		buffer.clear();
		
		TEx.parentChildPairs(buffer);
		Assert.assertEquals("[(10,5), (10,20), (20,30)]", buffer.toString());
		buffer.clear();
	}
	
}
