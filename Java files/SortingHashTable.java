import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class SortingHashTable {
	
	private LinkedList<Word>[] alphabetArr;
	private int size;
	
	@SuppressWarnings("unchecked")
	public SortingHashTable(int dim){
		alphabetArr = new LinkedList[dim];
		for(int i=0; i<alphabetArr.length; i++){
			alphabetArr[i] = new LinkedList<Word>();
		}
		size = dim;
	}
	
	public void insert(Word x){
		int index = x.getIndexSorted();
		alphabetArr[index].addLast(x);
	}
	
	public int size(){
		return size;
	}
	
	public LinkedList<Word> get(int index){
		return alphabetArr[index];
	}
	
	public void PrintSubList(LinkedList<Word> A, BufferedWriter file) throws IOException{
		for(int i=0; i<A.size(); i++){
			file.write(A.get(i).getWord() + " " + A.get(i).getLinesFound() + "\n");
		}
	}
	
	
	public int getPivot(LinkedList<Word> A, int first, int last){
		int middleIndex = (first+last)/2;
		Word middle = A.get(middleIndex);
		String firstStr = A.get(first).getWord();
		String middleStr = middle.getWord();
		String lastStr = A.get(last).getWord();
		
		if(firstStr.compareTo(middleStr) < 0){
			if(middleStr.compareTo(lastStr) < 0){ //First < middle < last
				return middleIndex;
			}
			else{ //First < last < middle
				return last;
			}
		}
		if(middleStr.compareTo(lastStr) < 0){
			if(firstStr.compareTo(lastStr) < 0){
				return first;
			}
			else{
				return last;
			}
		}
		if(lastStr.compareTo(firstStr) < 0){
			if(middleStr.compareTo(firstStr) < 0){
				return middleIndex;
			}
			else{
				return first;
			}
		}
		return -1;
	}
	
	public int partition(LinkedList<Word> A, int first, int last, int pivot){
		Word newLast = A.get(pivot);
		Word newPivot = A.get(last);
		A.set(pivot, newPivot);
		A.set(last, newLast);
		
		int i = first;
		int j = last - 1;
		boolean loop = true;
		while(loop){
			while(i<last && A.get(i).compareTo(A.get(pivot)) >= 0){
				i++; 
				}
			while(j>first && A.get(j).compareTo(A.get(pivot)) <= 0){
				j--; 
				}
			if(i<j){
				Word tmp = A.get(i);
				A.set(i, A.get(j));
				A.set(j, tmp);
			}
			else{
				loop = false;
			}
			//System.out.println(9);
		}
		Word tmp = A.get(i);
		A.set(i, A.get(pivot));
		A.set(pivot, tmp);
		
		return i;
	}
	
	public void QuickSort(LinkedList<Word> A, int first, int last){
		if(first<last){
			if((last-first) <= 2){
				insertionSort(A);
			}
			else{
				int pivot = getPivot(A, first, last);
				int splitPoint = partition(A, first, last, pivot);
				QuickSort(A, first, splitPoint-1);
				QuickSort(A, splitPoint +1, last);
			}
		}
	}
	
	public void insertionSort(LinkedList<Word> A){
		for(int i=0; i<A.size(); i++){
			Word currentWord = A.get(i);
			int j = i-1;
			
			while(j>=0 && (A.get(j).compareTo(currentWord) > 0)){
				A.set(j+1, A.get(j));
				j = j-1;
			}
			A.set(j+1, currentWord);
		}
	}
}
