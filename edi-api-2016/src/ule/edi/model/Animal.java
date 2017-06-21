package ule.edi.model;

public class Animal implements Comparable<Animal> {

	protected double weight; //	grams
	
	protected String name;
	
	public Animal(String name, double weight) {
		this.name = name;
		this.weight = weight;
	}
	
	@Override
	public String toString() {
		
		return name + " is a " + getClass().toString() + " of " + weight + "g";
	}

	@Override
	public int compareTo(Animal o) {
		
		return (weight < o.weight ? -1 : (weight > o.weight ? +1 : 0));
	}
}
