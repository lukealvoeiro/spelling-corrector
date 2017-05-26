import java.io.*;
import java.util.*;

public class SpellSorter {
	
	static Scanner scan = new Scanner(System.in);
	static QuadraticProbingHashTable<String> dictionary = new QuadraticProbingHashTable<String>();
	//static QuadraticProbingHashTable<Word> misspelled = new QuadraticProbingHashTable<Word>();
	//static SortingHashTable sortTable = new SortingHashTable(52);
	
	public static void readDictionary(String nameOfFile) throws IOException{
	//readDictionary takes in a string corresponding to the file name for a dictionary text file.
	//it reads the dictionary and then fills the dictionary hash table with the words in the file
		File file1 = new File(nameOfFile);
		Scanner filereader = new Scanner(file1);
		
		System.out.println("Reading in Dictionary...");
		
		while(filereader.hasNextLine()){
			dictionary.insert(filereader.nextLine());
		}
		
		filereader.close();
		System.out.println("Dictionary Read.");
	}
	
	public static void delete(Word x){
	//given a word object, modifies the word variable (a string) by deleting each character
	//if this modified word is in the dictionary hash table, add it to the word objects replaced ArrayList
		String word = x.getWord();
		for(int i=0; i<word.length(); i++){
			String wordModified = "";
			for(int j=0; j<word.length(); j++){
				if(j != i){
					wordModified += word.charAt(j);
				}
			}
			if(dictionary.contains(wordModified)){
				x.addReplacement(wordModified);
			}
		}
	}

	public static void insert(Word x){
	//EXTRA CREDIT: given a word object, modifies the word variable (a string) by inserting each letter of 
	//the alphabet in between each adjacent pair of characters in the word 
	//if this modified word is in the dictionary hash table, add it to the word objects replaced ArrayList
		String word = x.getWord();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for(int i=0; i<word.length()-1; i++){
			for(int j=0; j<alphabet.length(); j++){
			String wordModified = word.substring(0,i+1) + alphabet.charAt(j) + word.substring(i+1,word.length());
			if(dictionary.contains(wordModified)){
				x.addReplacement(wordModified);
			}
			}
		}
	}
	
	public static void swap(Word x){
	//EXTRA CREDIT: given a word object, modifies the word variable (a string) by swapping each adjacent pair of characters in the word.
	//if this modified word is in the dictionary hash table, add it to the word objects replaced ArrayList
		String word = x.getWord();
		for(int i=0; i<word.length(); i++){
			if((i+1)<word.length()){
				String wordModified = word.substring(0, i) + word.charAt(i+1) + word.charAt(i) + word.substring(i+2, word.length());
				if(dictionary.contains(wordModified)){
					x.addReplacement(wordModified);
				}
			}
		}
	}
	
	public static void replace(Word x){
	//EXTRA CREDIT: given a word object, modifies the word variable (a string) by swapping each letter of the word with the letters of the alphabet
	//if this modified word is in the dictionary hash table, add it to the word objects replaced ArrayList
		String word = x.getWord();
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		for(int i=0; i<word.length(); i++){
			for(int j=0; j<alphabet.length(); j++){
				String wordModified = word.substring(0,i) + alphabet.charAt(j) + word.substring(i+1,word.length());
				if(dictionary.contains(wordModified)){
					x.addReplacement(wordModified);
				}
			}
		}
	}
	
	public static void split(Word x){
	//EXTRA CREDIT: given a word object, modifies the word variable (a string) by inserting a space between each adjacent pair of characters in the word
	//if this modified word is in the dictionary hash table, add it to the word objects replaced ArrayList	
		String word = x.getWord();
		for(int i=0; i<word.length()-1; i++){
			String word1 = word.substring(0, i+1);
			String word2 = word.substring(i+1, word.length());
			if(dictionary.contains(word1) && dictionary.contains(word2)){
				x.addReplacement(word1 + " " + word2);
			}
		}
	}
	
	public static void readFile() throws IOException{
	//prompts the user for the input of filename, then allows them to choose to print all the misspelled words in the file
	//for each misspelled word, prompts the user to ignore the word and all subsequent instances of it, replace the word (with generated
	//replacements) and all subsequent instances of it, ignore it, or quit the program. Regardless of user choice, the program will output
	//two text files, one of them a ordered list (by order of appearance) of the misspelled words, and the other, a corrected version of the
	//file inputted
		System.out.println("Please enter a file to spell check:");
		Scanner scan = new Scanner(System.in);
		String fileInput = scan.nextLine();
		
		char option = 'z';
		while(option != 'q'){
			//prompting user
			System.out.println("Print words (p), enter new file (f), or quit(q)?");
			option = scan.nextLine().charAt(0);
			if(option == 'f'){
				System.out.println("New file name:");
				fileInput = scan.nextLine();
				
			}
			if(option == 'p'){
				QuadraticProbingHashTable<Word> misspelled = new QuadraticProbingHashTable<Word>();
				SortingHashTable sortTable = new SortingHashTable(52);
				
				//if p is selected, we will be creating the output files:
				String fileOutputOrder = fileInput.substring(0, fileInput.length()-4) + "_order.txt";
				BufferedWriter outOrder = new BufferedWriter(new FileWriter(fileOutputOrder));
				String fileOutputCorrected = fileInput.substring(0, fileInput.length()-4) + "_corrected.txt";
				BufferedWriter outCorrected = new BufferedWriter(new FileWriter(fileOutputCorrected));
				String fileOutputSorted = fileInput.substring(0, fileInput.length()-4) + "_sorted.txt";
				BufferedWriter outSorted = new BufferedWriter(new FileWriter(fileOutputSorted));
				
				//reading file
				File fileToRead = new File(fileInput);
				Scanner filereader = new Scanner(fileToRead);
			
				boolean quit = false;
				int lineCounter = 1;
				
				while(filereader.hasNextLine()){
					String line = filereader.nextLine();
					String[] lineArr = line.split(" ");
					for(int i=0; i<lineArr.length; i++){
						
						String tmp = lineArr[i];
						if(!dictionary.contains(tmp.replaceAll("\\W", ""))){
							
							Word incorrect = new Word(tmp.replaceAll("\\W", ""));
							if(!misspelled.contains(incorrect)){ //if word isnt in misspelled
								misspelled.insert(incorrect); //insert word into misspelled
								sortTable.insert(incorrect);
							}
							else{
								incorrect = misspelled.get(incorrect); //else, get the word object from misspelled
							}
							incorrect.addLine(lineCounter);
							if(!quit){
								
								//if the user hasn't already decided what should be done about this word
								if(!incorrect.getIgnored() && !incorrect.getReplaced()){ 
									System.out.println(incorrect.getWord() + " " + lineCounter); //print word and line
									System.out.println("Ignore All (i), Replace All (r), Next (n), or Quit (q)"); //print options
									char choice = scan.nextLine().charAt(0); //user input
									
									if(choice == 'i'){ //if ignore, set
										incorrect.setIgnored(true);
									}
									
									else if(choice == 'r'){ //if replace:
										incorrect.setReplaced(true);
										delete(incorrect); //generate possible replacements
										swap(incorrect); //generate more possible replacements
										replace(incorrect); //generate more possible replacements
										split(incorrect); //generate more possible replacements
										insert(incorrect); //generate more possible replacements
										
										System.out.println(incorrect); //print out replacement options
										String numOrIgnore = scan.nextLine(); //ask user for choice
										if(!numOrIgnore.equals("n")){ //if choice is not "next", set replaceWith to the user's choice
											int correctWordIndex = Integer.parseInt(numOrIgnore);
											incorrect.setReplaceWith(incorrect.getReplaceListIndex(correctWordIndex-1));
										}
									}
									else if(choice == 'n'){
										//do nothing
									}
									else if(choice == 'q'){
										quit = true; //to break from if statement on line 150
										//break;
									}	
								}
							}
							if(incorrect.getReplaced()){ //if incorrect is supposed to be corrected:
								//replace lineArr[i] with the the corrected word
								lineArr[i] = lineArr[i].replaceAll(incorrect.getWord(), incorrect.getReplaceWith());
							}
						//write out the misspelled word and the line it occured on
						outOrder.write(incorrect.getWord() + " " + lineCounter + "\n");
						}
						//writing out the corrected txt file
					outCorrected.write(lineArr[i] + " ");
					}
				lineCounter++;
				outCorrected.write("\n");
				
				}
			outOrder.close();
			outCorrected.close();
			for(int i=0; i<sortTable.size(); i++){
				LinkedList<Word> current = sortTable.get(i);
				sortTable.QuickSort(current, 0, current.size()-1);
				sortTable.PrintSubList(current, outSorted);
			}
			outSorted.close();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		readDictionary(args[0]);
		readFile();
	}
}