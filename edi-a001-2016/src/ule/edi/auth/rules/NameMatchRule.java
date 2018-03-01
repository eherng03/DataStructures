package ule.edi.auth.rules;

import ule.edi.auth.AuthRequest;
import ule.edi.auth.Rule;

/**
 * Permite peticiones cuyo nombre de usuario coincida con la expresión regular dada.
 * 
 * El método {@link String#matches(String)} permite comprobar si una cadena coincide
 * con la expresión regular dada como parámetro.
 * 
 * Un resumen de la sintáxis que usa Java para expresiones regulares:
 * 
 * https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html#sum
 *  
 * @author profesor
 *
 */
public class NameMatchRule implements Rule {
	
	private String pattern;

	public NameMatchRule(String pattern) {
		
		this.pattern = pattern;
	}
	
	@Override
	public boolean allows(AuthRequest c) {
		return (c.name.matches(pattern));
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) {
			return true;
		}
		
		if (obj instanceof NameMatchRule) {
			
			NameMatchRule other = (NameMatchRule) obj;
			
			return (this.pattern.equals(other.pattern));
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return pattern.hashCode();
	}
	
	@Override
	public String toString() {		
		return "{\"Type\":\"NameMatchRule\", \"Pattern\":\"" + pattern + "\"}";
	}



}
