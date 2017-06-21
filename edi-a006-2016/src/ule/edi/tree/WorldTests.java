package ule.edi.tree;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WorldTests {

	private World w = null;
	
	private LinkedList<Character> lC = null;
	
	@Before
	public void setupWorlds() {
		
		w = World.createEmptyWorld();
		
		lC = new LinkedList<Character>();
	}
	
	@Test
	public void testInsertRender() {
		w.insert("", Entity.warriors(3));	
		w.insert("LL", Entity.warriors(2));	
		w.insert("LL", Entity.castles(1));	
		w.insert("LRLR", Entity.princesses(3));
		w.insert("LRLR", Entity.princesses(2));
		w.insert("", Entity.warriors(3));
		System.out.println(w);
		System.out.println(w.render());		
	}
	
	@Test
	public void testCountCastlesCloserThan(){
		Assert.assertEquals(0, w.countCastlesCloserThan(3));
		w.insert("", Entity.castles(1));
		w.insert("L", Entity.castles(1));
		w.insert("R", Entity.dragons(1));
		w.insert("RL", Entity.castles(1));
		w.insert("RR", Entity.castles(1));
		System.out.println(w.render());
		Assert.assertEquals(1, w.countCastlesCloserThan(0));
		Assert.assertEquals(2, w.countCastlesCloserThan(1));
		Assert.assertEquals(4, w.countCastlesCloserThan(2));
	}
	
	@Test
	public void testContarDragones(){
		Assert.assertEquals(0, w.findFirstDragonInBreadthOrder());
		w.insert("", Entity.castles(1));
		w.insert("L", Entity.castles(1));
		w.insert("R", Entity.dragons(1));
		w.insert("LR", Entity.dragons(3));
		w.insert("RL", Entity.dragons(2));
		w.insert("RR", Entity.dragons(1));
		System.out.println(w.render());
		Assert.assertEquals(2, w.findFirstDragonInBreadthOrder());
	}
	
	@Test
	public void testContarDragonesYNoHay(){
		Assert.assertEquals(0, w.findFirstDragonInBreadthOrder());
		w.insert("", Entity.castles(1));
		w.insert("L", Entity.castles(1));
		w.insert("R", Entity.princesses(1));
		w.insert("LR", Entity.warriors(3));
		w.insert("RL", Entity.castles(2));
		w.insert("RR", Entity.warriors(1));
		System.out.println(w.render());
		Assert.assertEquals(6, w.findFirstDragonInBreadthOrder());
	}
	
	@Test 
	public void testEncontrarPrincesa(){
		w.insert("", Entity.castles(1));
		w.insert("L", Entity.castles(1));
		w.insert("R", Entity.princesses(2));
		w.insert("LR", Entity.dragons(3));
		w.insert("RL", Entity.princesses(3));
		w.insert("RR", Entity.dragons(1));
		System.out.println(w.render());
		LinkedList<Character> rx = new LinkedList<Character>();
		Assert.assertTrue(w.findNPrincessInorden(3, rx));
		Assert.assertEquals("[R, L]", rx.toString());
	}
	
	@Test
	public void testCountAtLevel(){
		Assert.assertEquals(0, w.countAtLevel(Entity.PRINCESS, 2));
		w.insert("", Entity.castles(1));
		w.insert("L", Entity.castles(1));
		w.insert("R", Entity.princesses(2));
		w.insert("LR", Entity.dragons(3));
		w.insert("RL", Entity.princesses(3));
		w.insert("RR", Entity.dragons(1));
		System.out.println(w.render());
		Assert.assertEquals(2, w.countAtLevel(Entity.PRINCESS, 2));
		
	}
	
	

	
	
	
}
