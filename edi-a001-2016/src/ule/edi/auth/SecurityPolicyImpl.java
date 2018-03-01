package ule.edi.auth;

/**
 * Implementa la interfaz SecurityPolicy.
 * 
 * Almacena las reglas en un array de objetos. Las posiciones no
 * utilizadas son <code>null</code>, de forma que en el array
 * pueden aparecer "huecos" cuando se borran reglas.
 * 
 * El constructor recibe como parámetro el número máximo de reglas
 * que puede contener la política.
 * 
 * @author profesor
 *
 */
public class SecurityPolicyImpl implements SecurityPolicy {
	
	private Rule[] rules;
	

	public SecurityPolicyImpl(int maxNumberOfRules) {
		rules = new Rule[maxNumberOfRules];
		//	TODO valores iniciales de los atributos de esta clase
	}
	
	/* (non-Javadoc)
	 * @see ule.edi.auth.SecurityPolicy#getUsedSlots()
	 */
	public int getUsedSlots() {
		//	TODO resolver según la especificación en la interfaz
		int usedSlots = 0;
		for(int i = 0; i < rules.length; i++){
			if(rules[i] != null){
				usedSlots++;
			}
		}
		return usedSlots;
	}
	
	/* (non-Javadoc)
	 * @see ule.edi.auth.SecurityPolicy#getNumberOfSlots()
	 */
	public int getNumberOfSlots() {
		int numberOfSlots = rules.length;
		return numberOfSlots;
	}
	
	/* (non-Javadoc)
	 * @see ule.edi.auth.SecurityPolicy#getAvailableSlots()
	 */
	public int getAvailableSlots() {
		int availableSlots = 0;
		for(int i = 0; i < rules.length; i++){
			if(rules[i] == null){
				availableSlots++;
			}
		}
		return availableSlots;
	}
	
	/* (non-Javadoc)
	 * @see ule.edi.auth.SecurityPolicy#removeRule(ule.edi.auth.Rule)
	 */
	public void removeRule(Rule r) {
		for(int i = 0; i < rules.length; i++){
			if(rules[i] != null){
				if(rules[i].equals(r)){
					rules[i] = null;
					break;
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see ule.edi.auth.SecurityPolicy#addRule(ule.edi.auth.Rule)
	 */
	public boolean addRule(Rule r) {
		if(getAvailableSlots() > 0){	//si hay huecos compruebo, si no devuelvo false directamente
			for(int i = 0; i < rules.length; i++){
				if(rules[i] != null){
					if(rules[i].equals(r)){
						return false;
					}
				}
			}
			//Si sale de este bucle es que no hay ninguna igual.
			for(int i = 0; i < rules.length; i++){
				if(rules[i] == null){	//si hay un hueco la meto
					rules[i] = r;
					return true;
				}
			}
		}
		return false;		
	}
	
	private void verify(AuthRequest c) throws InvalidRequestException {
		if (c.name == null) {
			throw new InvalidRequestException("Request user name is null", c);
		}
		if (c.name.length() == 0) {
			throw new InvalidRequestException("Request user name is empty", c);
		}
		if (c.utc < 0 ) {
			throw new InvalidRequestException("Request UTC time is invalid", c);
		}
	}
	
	/* (non-Javadoc)
	 * @see ule.edi.auth.SecurityPolicy#process(ule.edi.auth.AuthRequest)
	 */
	public void process(AuthRequest c) throws InvalidRequestException {
		
		//	Lanza una excepción si la petición no es válida, que se propagará
		//	fuera de este método, ya que no hay try/catch
		verify(c);
		//comprueba que todas las reglas permitan la peticion
		for (int i = 0; i < rules.length; i++) {
			if(rules[i] != null){
				if(!rules[i].allows(c)){
					return;
				}
			}
		}
		c.allowed = true;
	}
	
	
	/**
	 * Devuelve una representación en texto del objeto.
	 * 
	 * Aquí se ha decidido usar un formato similar a <a href="http://www.json.org/">JSON</a>.
	 */
	@Override
	public String toString() {
		
		//	Indicado para construir el resultado añadiendo varias cadenas
		StringBuffer buffer = new StringBuffer();
		
		//	Las comillas dobles hay que protegerlas con \, para que no sean
		//	interpretadas como el final del literal.
		buffer.append("{\"SecurityPolicy\":[");
		for (int i = 0; i < rules.length; i++) {
			
			if (rules[i] != null) {
				//	A su vez usa el toString() que definan las clases de reglas
				buffer.append(rules[i].toString());
				buffer.append(", ");
			} else {
				buffer.append("null, ");
			}
		}
		//	Quita la última coma añadida
		if (rules.length > 0) {
			buffer.delete(buffer.length() - 2, buffer.length());
		}
		buffer.append("]}");
		
		return buffer.toString();
	}


}
