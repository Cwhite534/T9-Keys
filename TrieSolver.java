import java.util.*;

/**
 * Created by Casey on 2/7/2017.
 * this class creates a trie of all possible words for the input
 */
public class TrieSolver {

    private TrieNode root;


//ctor
    public TrieSolver(ArrayList<Integer> input){
        this.root = new TrieNode();
        buildTree(input);

    }


//builds the tree
    private void buildTree(ArrayList<Integer> input){
        assert input != null: "Input is null";
        assert input.size() > 0: "Input size is < 1";
        buildTreeRecursive(this.root,input,0);
    }

//recursive helper to build tree
    private void buildTreeRecursive(TrieNode cur, ArrayList<Integer> input, int depth){
        if(depth >= input.size())
            return;
        char[] curIntChars = MyDictionary.getNumToChar().get(input.get(depth++)).toCharArray();
        for (char ch : curIntChars) {
            TrieNode nextNode = new TrieNode();
            cur.children.put(ch, nextNode);
           buildTreeRecursive(nextNode, input, depth);
        }
    }


//gets all valid words using hashtable dictionary
    public ArrayList<String> getAllValidWordsHash(){
        if(this.root.children.isEmpty())
            return new ArrayList<>();
        return getAllValidWordsHash(this.root, new ArrayList<String>() , "");
    }

//get all valid recursive helper
    private ArrayList<String> getAllValidWordsHash(TrieNode cur, ArrayList<String> words, String word){
        if(cur.isLeaf() && MyDictionary.getHashDictionary().containsKey(word))
            words.add(word);
        if(cur.children.isEmpty())
            return words;

        Iterator<Character> itr = cur.children.keySet().iterator();
        Map<Character, TrieNode> children = cur.children;
        while (itr.hasNext()){
            Character childLetter = itr.next();
            getAllValidWordsHash(cur.children.get(childLetter), words, word + childLetter);
        }

        return words;
    }


    //gets all valid words using trie dictionary
    public ArrayList<String> getAllValidWordsTrie(){
        if(this.root.children.isEmpty())
            return new ArrayList<>();
        return getAllValidWordsTrie(this.root, new ArrayList<String>() , "");
    }

    //get valid words recursive helper
    private ArrayList<String> getAllValidWordsTrie(TrieNode cur, ArrayList<String> words, String word){
        if(cur.isLeaf() && MyDictionary.getTrieDictionary().containsWord(word))
            words.add(word);
        if(cur.children.isEmpty())
            return words;

        Iterator<Character> itr = cur.children.keySet().iterator();
        while (itr.hasNext()){
            Character childLetter = itr.next();
            getAllValidWordsTrie(cur.children.get(childLetter), words, word + childLetter);
        }

        return words;
    }


//TrieNode class
    private class TrieNode{
        TreeMap<Character,TrieNode> children = new TreeMap<>();                                                             //similar to haschTable but keys are sorted (key,value)
        public boolean isLeaf(){
            return (children.isEmpty());
        }
    }
}
