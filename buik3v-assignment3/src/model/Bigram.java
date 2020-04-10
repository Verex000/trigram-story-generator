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
 * Model for a bigram.
 * Consists of a string, and a list of trigrams.
 * @author Kevin Bui
 * @version 12/1/19
 */
public class Bigram {

	/**String representation of a trigram.*/
	private String bigramString;
	
	/**List of trigrams*/
	private List<Trigram> trigramList;
	
	/**
	 * Constructor
	 * @param str
	 */
	public Bigram(String str) {
		bigramString = str;
		trigramList = new ArrayList<Trigram>();
	}
	
	/**
	 * Returns trigram string.
	 * @return
	 */
	public String getString() {
		return bigramString;
	}
	
	/**
	 * Returns list of trigrams.
	 * @return
	 */
	public List<Trigram> getTrigramList() {
		return  trigramList;
	}
	
	/**
	 * Adds a trigram to the trigram list.
	 * @param t
	 */
	public void addToList(Trigram t) {
		trigramList.add(t);
	}
	
	/**
	 * Checks if a trigram exists in the trigram list.
	 * @param str
	 * @return
	 */
	public boolean containsTrigram(String str) {
		return trigramList.stream().anyMatch(trigram -> trigram.getString().equals(str));
	}
	
	/**
	 * Returns a trigram from the trigram list matching the conditions.
	 * @param t
	 * @return
	 */
	public Trigram findInList(Trigram t) {
		if(t == null) {
			System.out.println("Trigram Token is Null");
			return null;
		}
		for(Trigram trigramInList : trigramList) {
			if(trigramInList.equals(t)) {
				return trigramInList;
			}
		}
		return null;
	}
	
	/**
	 * Chooses a trigram based on probability given the unigram, and bigram.
	 * @return
	 */
	public Trigram chooseProbabilisticTrigram() {
		Random rand = new Random();
		int totalTrigramCount = 0;
		for(Trigram t : trigramList) {
			totalTrigramCount += t.getFrequency();
		}
		int randomWeight = (int) Math.ceil(rand.nextDouble() * totalTrigramCount);
		int sumWeight = 0;
		for(Trigram t : trigramList) {
			sumWeight += t.getFrequency();
			if(sumWeight >= randomWeight) {
				return t;
			}
		}
		return null;
	}
	
	/**
	 * Returns an integer representing the hashcode of this bigram.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(bigramString, trigramList);
	}
	
	/**
	 * Checks if two bigrams are equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(obj == null || this.getClass() != obj.getClass()) {
			return false;
		}
		
		Bigram otherBigram = (Bigram) obj;
		
		if(otherBigram.getString() == null || !this.getString().equals(otherBigram.getString())) {
			return false;
		}
		
		return true;
	}
}
