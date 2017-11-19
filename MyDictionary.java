import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Created by Casey on 2/12/2017.
 * this class is designed to load the dictionary from file for both the hashTable and the prefix trie
 * as well as contain the integer to letter converter
 */
public class MyDictionary {

    private static Hashtable<String,String> hashDictionary;
    private static DictionaryTrie trieDictionary;
    private static HashMap<Integer,String> numToString = makeNumtoString();



    public static boolean loadDictionary(){
        boolean loadSuccess = true;
        hashDictionary = new Hashtable<>();
        trieDictionary = new DictionaryTrie();

        try{
            File myFile = new File("dictionary.txt");
            Scanner fin = new Scanner(myFile);				        //loads a scanner with file so fin can read line by line
            if(myFile.exists())
                while(fin.hasNextLine()){
                    String currentLine = fin.nextLine();
                    String currentWord = currentLine.substring(0,currentLine.indexOf(","));
                    hashDictionary.put(currentWord,currentWord);
                    trieDictionary.insert(currentWord);
                }
            fin.close();
        }
        catch (Exception e){
            loadSuccess = false;
            System.out.println("Fatal error loading dictionary from file.");
        }
        return loadSuccess;

    }

    private static HashMap<Integer,String> makeNumtoString(){
        HashMap<Integer,String> numToStrings = new HashMap<Integer,String>();
        numToStrings.put(0,"");
        numToStrings.put(1,"");
        numToStrings.put(2,"abc");
        numToStrings.put(3,"def");
        numToStrings.put(4,"ghi");
        numToStrings.put(5,"jkl");
        numToStrings.put(6,"mno");
        numToStrings.put(7,"pqrs");
        numToStrings.put(8,"tuv");
        numToStrings.put(9,"wxyz");
        return numToStrings;
    }



    public static Hashtable<String, String> getHashDictionary() {
        return hashDictionary;
    }

    public static DictionaryTrie getTrieDictionary() {
        return trieDictionary;
    }

    public static HashMap<Integer, String> getNumToChar() { return numToString; }
}
