package ule.edi.auth.rules;

import java.util.Arrays;

import ule.edi.auth.AuthRequest;
import ule.edi.auth.Rule;

/**
 * Permite peticiones realizadas dentro de un intervalo determinado de tiempo.
 * 
 * Para una regla creada con valores min y max, se permiten peticiones de
 * autenticación cuya marca de tiempo esté en [min, max), abierto por la
 * derecha. Es decir, una petición exáctamente en 'max' no se permite.
 * 
 * @author profesor
 *
 */
public class TimeWindowRule implements Rule {
	
	private long allowedUTCWindow[];

	public TimeWindowRule(long minUTCValue, long maxUTCValue) {
		
		allowedUTCWindow = new long [ 2 ];
		
		allowedUTCWindow[0] = minUTCValue;
		allowedUTCWindow[1] = maxUTCValue;
	}
	
	@Override
	public boolean allows(AuthRequest c) {
		return ((allowedUTCWindow[0] <= c.utc) && (c.utc < allowedUTCWindow[1]));
	}

	@Override
	public boolean equals(Object obj) {
		
		if (obj == this) {
			return true;
		}
		
		if (obj instanceof TimeWindowRule) {
			
			TimeWindowRule other = (TimeWindowRule) obj;
			
			return ((this.allowedUTCWindow[0] == other.allowedUTCWindow[0]) && (this.allowedUTCWindow[1] == other.allowedUTCWindow[1]));
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(allowedUTCWindow);
	}
	
	@Override
	public String toString() {		
		return "{\"Type\":\"TimeWindowRule\", \"Interval\":[" + allowedUTCWindow[0] + ", " + allowedUTCWindow[1] + "]}";
	}
	
	
}
