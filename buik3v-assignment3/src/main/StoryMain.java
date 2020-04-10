/*
 * TCSS 435 - Autumn 2019
 * Programming Assignment 3 - Random Story
 */
package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Random;
import model.Unigram;
import model.Bigram;
import model.Trigram;

/**
 * This program creates a table of unigrams, bigrams, and trigrams based on
 * training data, and generates a random story of 1000 words.
 * @author Kevin Bui
 * @version 12/1/2019
 */
public class StoryMain {
	
	/**
	 * Driver, allows users to input training data, generate a random story or quit.
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Unigram> unigramMap = new HashMap<String, Unigram>();
		Scanner scan = new Scanner(System.in);
		boolean quit = false;
		while(!quit) {
			System.out.println("Press 1 to input a training file:");
			System.out.println("Press 2 to generate a story:");
			System.out.println("Press 3 to quit:");
			int option = scan.nextInt();
			System.out.println();
			if(option == 1) {
				File txtFile = readFile(scan);
				Scanner fileScan;
				try {
					fileScan = new Scanner(txtFile);
					buildTrainingData(fileScan, unigramMap);
					fileScan.close();
				} catch (FileNotFoundException e) {
					System.out.println("fileScan is not valid!");
					e.printStackTrace();
				}
				System.out.println();
			}
			else if(option == 2) {
				System.out.println("Writing to file: ");
				//String fileName = scan.next();
				generateStory(unigramMap);
			}
			else if(option == 3) {
				quit = true;
			}
			else {
				System.out.println("error, invalid input!");
			}
		}
		scan.close();
	}

	/**
	 * Returns a user specified file.
	 * @param scan
	 * @return a file to be scanned
	 */
	public static File readFile(Scanner scan) {
		System.out.println("Please input a text file to read: ");
		String txt = scan.next();
		File txtFile = new File(txt);
		return txtFile;
	}
	
	/**
	 * Generates a random story of 1000 words using unigrams, bigrams, and trigrams built before.
	 * @param unigramMap
	 */
	public static void generateStory(Map<String, Unigram> unigramMap) {
		int wordCount = 0;
		Object[] uArr = unigramMap.values().toArray();
		
		//The previous trigram will be used to choose the next unigram if it exists in the table.
		Trigram carryOver = null;
		
		Random random = new Random();
			while(wordCount <= 1000) {
				Unigram randomUnigram = null;
				Bigram randomBigram = null;
				Trigram randomTrigram = null;
				
				if(carryOver != null && unigramMap.containsKey(carryOver.getString())) {
					randomUnigram = unigramMap.get(carryOver.getString());
				}
				else {
					//Choose a random unigram from the table
					int randIndex = random.nextInt(uArr.length);
					randomUnigram= (Unigram)uArr[randIndex];
				}
				randomBigram = randomUnigram.chooseRandomBigram();
				randomTrigram = randomBigram.chooseProbabilisticTrigram();
				carryOver = randomTrigram;
				
				System.out.print(randomBigram.getString() + " ");
				wordCount++;
				System.out.print(randomTrigram.getString() + " ");
				wordCount++;
				
				if(wordCount % 25 == 0) {
					System.out.println();
					System.out.println();
				}
			}
			System.out.println();
	}
	
	/**
	 * Builds the table holding the unigrams, bigrams and trigrams by scanning a 
	 * user specified file.
	 * @param fileScan
	 * @param unigramMap
	 */
	public static void buildTrainingData(Scanner fileScan, Map<String, Unigram> unigramMap) {
		while(fileScan.hasNext()) {
			String unigramToken = fileScan.next().toLowerCase();
			String bigramToken = null;
			String trigramToken = null;
			if(fileScan.hasNext()) {
				bigramToken = fileScan.next().toLowerCase();
			}
			if(fileScan.hasNext()) {
				trigramToken = fileScan.next().toLowerCase();
			}
			Unigram u = new Unigram(unigramToken);
			Bigram b = new Bigram(bigramToken);
			Trigram t = new Trigram(trigramToken);
			if(bigramToken != null && trigramToken != null) {
				if(unigramMap.containsKey(u.getString())) {
					u = unigramMap.get(u.getString());
					if(u.containsBigram(b.getString())) {
						Bigram bInList = u.findInList(b);
						if(bInList.containsTrigram(t.getString())) {
							Trigram tInList = bInList.findInList(t);
							tInList.incrementFrequency();
						}
						
						//if the trigram is not linked to the bigram.
						else {
							bInList.addToList(t);
						}
					}
					
					//if the bigram is not linked to the unigram.
					else {
						b.addToList(t);
						unigramMap.get(u.getString()).addToList(b);
					}
				}
				//If the unigram does not exist in the table.
				else {
					b.addToList(t);
					u.addToList(b);
					unigramMap.put(u.getString(), u);
				}
			}
		}
	}
}
