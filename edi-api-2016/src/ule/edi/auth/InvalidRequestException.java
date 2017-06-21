package ule.edi.auth;

public class InvalidRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5427494319928805455L;
	
	public AuthRequest request;

	public InvalidRequestException(String reason, AuthRequest c) {
		
		super(reason);
		
		this.request = c;
	}
	
	
}
