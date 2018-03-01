package ule.edi.auth;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ule.edi.auth.rules.NameMatchRule;
import ule.edi.auth.rules.TimeWindowRule;

public class SecurityPolicyTests {

	//	Para cada método @Test, queremos disponer de un objeto sp4 ya preparado
	private SecurityPolicy sp4;
	
	@Before
	public void setupBeforeEachTestMethod() {
		//	Cada método @Test ya dispone de...
		this.sp4 = new SecurityPolicyImpl(4);
		
		Logger.getLogger("ule.edi").info("setupBeforeEachTestMethod");
	}
	
	@After
	public void teardownAfterEachTestMethod() {
		//	Tras cada método @Test
		
		Logger.getLogger("ule.edi").info("teardownAfterEachTestMethod");
	}

	
	//	También es posible indicar acciones antes y después de toda la ejecución
	@BeforeClass
	public static void setupBeforeAnythingElse() {
		
		Logger.getLogger("ule.edi").info("setupBeforeAnythingElse");
	}
	
	@AfterClass
	public static void teardownAfterWeAreDone() {
		
		Logger.getLogger("ule.edi").info("teardownAfterWeAreDone");
	}
	
	
	@Test
	public void testDuplicatedRules() {
		
		Logger.getLogger("ule.edi").info("testDuplicatedRules");
		
		Assert.assertTrue(sp4.getUsedSlots() == 0);
		
		//	Equivalente, compara tipos primitivos (long en este caso)
		Assert.assertEquals(0, sp4.getUsedSlots());
		
		
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 10)));
		Assert.assertEquals(1, sp4.getUsedSlots());
		
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 11)));
		Assert.assertEquals(2, sp4.getUsedSlots());
		
		//	No se admiten duplicados, no debería añadir esta regla
		Assert.assertFalse(sp4.addRule(new TimeWindowRule(0, 11)));
		Assert.assertEquals(2, sp4.getUsedSlots());		
	}

	@Test
	public void testBasicNamePatterns() throws InvalidRequestException {
		
		Logger.getLogger("ule.edi").info("testBasicNamePatterns");
		
		//	El nombre debe ser 'admin'
		sp4.addRule(new NameMatchRule("admin"));
		
		
		AuthRequest a = new AuthRequest("admin", 2);
		
		sp4.process(a);
		Assert.assertTrue(a.allowed);

		AuthRequest b = new AuthRequest("Admin", 2);
		
		sp4.process(b);
		Assert.assertFalse(b.allowed);		
		
		//	Se permite 'admin' ó 'Admin'
		sp4.removeRule(new NameMatchRule("admin"));
		
		sp4.addRule(new NameMatchRule("admin|Admin"));
		

		AuthRequest c = new AuthRequest("Admin", 2);
		
		sp4.process(c);
		Assert.assertTrue(c.allowed);		
		
	}
	
	@Test
	public void testNumberOfSlots() {
		
		Logger.getLogger("ule.edi").info("testNumberOfSlots");
		
		Assert.assertTrue(sp4.getNumberOfSlots() == 4);
		
		//	Equivalente, compara tipos primitivos (long en este caso)
		Assert.assertEquals(4, sp4.getNumberOfSlots());
	}
	
	@Test
	public void testAvailableSlots() {
		
		Logger.getLogger("ule.edi").info("testAvailableSlots");
		
		Assert.assertTrue(sp4.getAvailableSlots() == 4);
		
		//	Equivalente, compara tipos primitivos (long en este caso)
		Assert.assertEquals(4, sp4.getAvailableSlots());
	}
	
	@Test
	public void testRemoveRule() {
		
		Logger.getLogger("ule.edi").info("testRemoveRule");
		
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 10)));
		Assert.assertTrue(sp4.getAvailableSlots() == 3);
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 11)));
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 12)));
		sp4.removeRule(new TimeWindowRule(0, 10));
		sp4.removeRule(new TimeWindowRule(0, 11));
		Assert.assertTrue(sp4.getAvailableSlots() == 3);
		
	}
	
	@Test
	public void testFullArray() {
		
		Logger.getLogger("ule.edi").info("testFullArray");
		
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 10)));
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 11)));
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 12)));
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 13)));
		
		Assert.assertTrue(sp4.getAvailableSlots() == 0);
		
		Assert.assertFalse(sp4.addRule(new TimeWindowRule(0, 11)));
	}
	
	@Test
	public void testString(){
		
		Logger.getLogger("ule.edi").info("testString");
		
		Assert.assertEquals((new TimeWindowRule(0, 10)).toString(), (new TimeWindowRule(0, 10)).toString());
		
		Assert.assertEquals((new NameMatchRule("hola")).toString(), (new NameMatchRule("hola")).toString());
		
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 10)));
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 11)));
		Assert.assertTrue(sp4.addRule(new TimeWindowRule(0, 12)));
		
		Assert.assertEquals(sp4.toString(), sp4.toString());
	}
	
	public void testEquals(){
		
		Logger.getLogger("ule.edi").info("testEquals");
		
		Assert.assertTrue((new TimeWindowRule(0, 10)).equals(new TimeWindowRule(0, 10)));
		
		Assert.assertFalse((new TimeWindowRule(0, 11)).equals(new TimeWindowRule(0, 10)));
		
		Assert.assertFalse((new NameMatchRule("hola")).equals(new NameMatchRule("adios")));
		
	}
	
public void testHashCode(){
		
		Logger.getLogger("ule.edi").info("testHashCode");
		
		Assert.assertEquals((new TimeWindowRule(0, 10)).hashCode(), (new TimeWindowRule(0, 10)).hashCode());
		
		Assert.assertEquals((new NameMatchRule("hola")).hashCode(), (new NameMatchRule("hola")).hashCode());
		
	}

@Test
public void testProcessTimeWindowRule() throws InvalidRequestException{
	
	Logger.getLogger("ule.edi").info("testProcessTimeWindowRule");
	
	//	El nombre debe ser 'admin'
	sp4.addRule(new TimeWindowRule(2, 5));
	
	
	AuthRequest a = new AuthRequest("admin", 3);
	
	sp4.process(a);
	Assert.assertTrue(a.allowed);

	AuthRequest b = new AuthRequest("Admin", 1);
	
	sp4.process(b);
	Assert.assertFalse(b.allowed);		
			
	
}
	
	
}
