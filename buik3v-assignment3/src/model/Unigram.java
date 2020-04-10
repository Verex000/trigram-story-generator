/*
 * TCSS 435 - Autumn 2019
 * Programming Assignment 3 - Random Story
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Model for a Unigram
 * A unigram is a string, and contains a list of bigrams.
 * @author Kevin Bui
 * @version 12/1/19
 */
public class Unigram {


	/**The string representation of a unigram.*/
	private String unigramString;
	
	/**The list of bigrams*/
	private List<Bigram> bigramList;
	
	/**
	 * Constructor
	 * @param str
	 */
	public Unigram(String str) {
		this.unigramString = str;
		bigramList = new ArrayList<Bigram>();
	}
	
	/**
	 * Returns the unigram string.
	 * @return
	 */
	public String getString() {
		return unigramString;
	}
	
	/**
	 * Returns the bigram list.
	 * @return
	 */
	public List<Bigram> getList() {
		return bigramList;
	}
	
	/**
	 * Adds a bigram to the bigram list
	 * @param b
	 */
	public void addToList(Bigram b) {
		bigramList.add(b);
	}
	
	/**
	 * Checks if a bigram exists in the bigram list.
	 * @param str
	 * @return
	 */
	public boolean containsBigram(String str) {
		return bigramList.stream().anyMatch(bigram -> bigram.getString().equals(str));
	}
	
	/**
	 * Returns a bigram in the bigram list matching the conditions.
	 * @param b
	 * @return
	 */
	public Bigram findInList(Bigram b) {
		if(b == null) {
			System.out.println("Bigram Token is null");
		}
		for(Bigram bigramInList : bigramList) {
			if(bigramInList.equals(b)) {
				return bigramInList;
			}
		}
		return null;
	}
	
	/**
	 * Chooses a ranodm bigram from the list.
	 * @return
	 */
	public Bigram chooseRandomBigram() {
		Random rand = new Random();
		int randomIndex = rand.nextInt(bigramList.size());
		return bigramList.get(randomIndex);
	}
	
	/**
	 * Returns an integer representing the hashcode of this Unigram.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(unigramString, bigramList);
	}
	
	/**
	 * Checks if two Unigrams are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		Unigram otherUnigram = (Unigram) obj;
		
		
		if(otherUnigram.getString() == null || !this.getString().equals(otherUnigram.getString())) {
			return false;
		}
		
		if(otherUnigram.getList().size() != this.bigramList.size()) {
			return false;
		}
		List<Bigram> otherBigramList = otherUnigram.getList();
		
		for(int i = 0; i < bigramList.size(); i++) {
			if(!bigramList.get(i).getString().equals(otherBigramList.get(i).getString())) {
				return false;
			}
		}
		return true;
	}
	

}
