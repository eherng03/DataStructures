package ule.edi.auth;

/**
 * Petición de autenticación.
 * 
 * Almacena información sobre una petición para autenticar a un usuario, con
 * su nombre y el momento de la petición.
 * 
 * Una petición se construye con el atributo <code>allowed</code> a falso, y
 * será la política de seguridad la que tras evaluar la petición la marque como
 * aceptada, cambiando el atributo a cierto, o como rechazada.
 * 
 * @author profesor
 *
 */
public class AuthRequest {
	
	
	public String name;
	
	public long utc;
	
	boolean allowed;

	/**
	 * Construye una petición de autenticación.
	 * 
	 * @param name nombre del usuario.
	 * @param utc momento de la petición, en UTC.
	 */
	public AuthRequest(String name, long utc) {
		
		this.name = name;
		
		this.utc = utc;
		
		this.allowed = false;
	}
	
	@Override
	public String toString() {
		return "User \"" + name + "\" requests access at " + utc + " UTC time";
	}

}
