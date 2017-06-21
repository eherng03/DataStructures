package ule.edi.auth;

/**
 * Una regla en una política de seguridad.
 * 
 * @author profesor
 *
 */
public interface Rule {

	/**
	 * Comprueba si esta regla permite la petición dada.
	 *  
	 * @param c una petición a comprobar.
	 * @return cierto si la regla permite la petición, falso en caso contrario.
	 */
	public boolean allows(AuthRequest c);
	
}
