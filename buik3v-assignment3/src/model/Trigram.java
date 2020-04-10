/*
 * TCSS 435 - Autumn 2019
 * Programming Assignment 3 - Random Story
 */
package model;

import java.util.Objects;

/**
 * Model for a trigram.
 * A trigram consists of a string, and frequency.
 * @author Kevin Bui
 *
 */
public class Trigram {

	/**String representation of a trigram.*/
	private String trigramString;
	
	/**Frequency that this trigram appears given the parent bigram and unigram.*/
	private int frequency;
	
	/**
	 * Constructor
	 * @param str
	 */
	public Trigram(String str) {
		trigramString = str;
		frequency = 1;
	}
	
	/**
	 * Returns the string representation of a Trigram.
	 * @return
	 */
	public String getString() {
		return trigramString;
	}
	
	/**
	 * Increases the frequency of the trigram by one.
	 */
	public void incrementFrequency() {
		frequency++;
	}
	
	/**
	 * Returns the frequency.
	 * @return
	 */
	public int getFrequency() {
		return frequency;
	}
	
	
	/**
	 * Returns an integer representing the hashcode of this Trigram.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(trigramString, frequency);
	}
	
	/**
	 * Checks if two Trigrams are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		Trigram otherTrigram = (Trigram) obj;
		
		
		if(otherTrigram.getString() == null || !this.getString().equals(otherTrigram.getString())) {
			return false;
		}
		
		return true;
	}
}
