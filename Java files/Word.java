import java.util.ArrayList;

public class Word{
	
	private String word; //the string associate with this Word object
	private boolean ignored;
	private boolean replaced;
	private String replaceWith;
	private ArrayList<String> replaceList;
	private ArrayList<Integer> linesFound;
	private int indexSorted;
	
	public String getLinesFound(){
		String result = "";
		for(int i=0; i<linesFound.size();i++){
			result += linesFound.get(i) + " ";
		}
		return result;
	}
	public int compareTo(Word other){
		return word.compareTo(other.word);
	}
	
	public void addLine(int x){
		linesFound.add(x);
	}
	
	public int getIndexSorted(){
		return indexSorted;
	}
	
	public String getReplaceWith(){
		return replaceWith;
	}
	
	public boolean getReplaced(){
		return replaced;
	}
	
	public String getReplaceListIndex(int i){
		return replaceList.get(i);
	}
	
	public String toString(){ //used to print out all the recommended replacements
		if(replaceList.size() == 0){
			return "No possible corrections. Next (n)?";
		}
		else{
			String result = "Replace with ";
			for(int i=0; i<replaceList.size(); i++){
				result = result + "(" + (i+1) + ")" + replaceList.get(i) + " ";
			}
			result = result.substring(0, result.length()-1) + ", or next (n)?";
			return result;
		}
	}
	
	public void setReplaceWith(String x){
		replaceWith = x;
	}
	
	public void setReplaced(boolean x){
		replaced = x;
	}
	
	public Word(String w) //constructor
	{
		word = w;
		replaced = false;
		ignored = false;
		linesFound = new ArrayList<Integer>();
		replaceList = new ArrayList<String>();
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		for(int i=0; i<alphabet.length(); i++){
			if(word.charAt(0) == alphabet.charAt(i)){
				indexSorted = i;
				break;
			}
		}
	}
	
	public void setWord(String w)
	{
		word = w;
		ignored = false;
	}
	
	public String getWord()
	{
		return word;
	}

	public boolean getIgnored(){
		return ignored;
	}
	
	public void setIgnored(boolean x){
		ignored = x;
	}
	
	public void addReplacement(String x){ //adds a string to the replaceList ArrayList
		replaceList.add(x);
	}
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}
	
	/*
	 * equals method that compares two words
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Word other = (Word) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}	
	
	

}
