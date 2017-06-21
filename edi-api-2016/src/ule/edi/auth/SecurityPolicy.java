package ule.edi.auth;

/**
 * Un conjunto de reglas que validan o rechazan peticiones de autenticación.
 * 
 * La política se crea con un número máximo de reglas posibles ("slots").
 * 
 * @author profesor
 *
 */
public interface SecurityPolicy {

	
	/**
	 * Indica cuántas reglas puede contener la política como máximo.
	 * 
	 * @return
	 */
	public int getNumberOfSlots();

	/**
	 * Indica cuántas reglas tiene la política.
	 * 
	 * @return
	 */
	public int getUsedSlots();
	
	/**
	 * Indica cuántos huecos libres quedan para añadir reglas.
	 * 
	 * @return
	 */
	public int getAvailableSlots();
	
	
	/**
	 * Añade una regla a la política de seguridad.
	 * 
	 * No se permiten reglas duplicadas, así que si ya hay una
	 * regla x que sea igual a la dada, no se hará nada; es decir,
	 * si x.equals(r) es cierto.
	 * 
	 * @param r regla a añadir.
	 * @return cierto si se añadió la regla, falso en caso contrario.
	 */
	public boolean addRule(Rule r);
	
	/**
	 * Elimina la regla dada de la política de seguridad.
	 * 
	 * Elimina la regla que sea igual a la pasada como parámetro, es decir
	 * elimina la regla x tal que x.equals(r) sea cierto.
	 * 
	 * @param r regla a eliminar.
	 */
	public void removeRule(Rule r);
	
	
	/**
	 * Procesa la petición con las reglas almacenadas en la política.
	 * 
	 * Una petición se considera no válida si el nombre de usuario es
	 * null, o cadena vacía, o si la marca UTC tiene un valor negativo.
	 * 
	 * Si todas las reglas de la política permiten la petición, entonces
	 * se marca ésta como aceptada. En cuanto se encuentre una regla que
	 * no permite la petición, se detiene el procesamiento.
	 * 
	 * @param c petición a procesar.
	 * @throws InvalidRequestException si la petición no es válida.
	 */
	public void process(AuthRequest c) throws InvalidRequestException;
	
}
