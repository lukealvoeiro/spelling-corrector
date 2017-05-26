# spelling-corrector
Java program that finds the misspelled words in a text file, generating three outputs.

  (1) text file containing all the misspelled words in the order they appear in the inputted file.
  (2) text file showing all the words the user decided to modify.
  (3) text file showing all the misspelled words sorted alphabetically.
  
 
The misspelled words were stored in a user-created Separate Chaining Hash Table. This allowed a BucketSort to be 
performed, sorting each of the LinkedLists with QuickSort. The sorting functions were also user-created. 
