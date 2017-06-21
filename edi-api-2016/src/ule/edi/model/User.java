package ule.edi.model;

public class User implements Comparable<User> {

	private String uniqueLogin;
	
	public User(String uniqueLogin) {
		
		this.uniqueLogin = uniqueLogin;
	}
	
	@Override
	public int compareTo(User o) {
		
		return uniqueLogin.compareTo(o.uniqueLogin);
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o) { return true; }
		
		if (o instanceof User) {
			
			User other = (User)o;
			
			return (other.compareTo(this) == 0);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		
		return uniqueLogin.hashCode();
	}
	
	@Override
	public String toString() {
		
		return uniqueLogin;
	}
}
