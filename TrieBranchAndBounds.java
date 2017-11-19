import java.util.*;

/**
 * Created by Casey on 2/7/2017.
 * this class solves the t9 problem and prunes non valid words while building to make the program faster. the effect is very noticeable with longer words
 */
public class TrieBranchAndBounds {

    private TrieNode root;
    private int maxDepth;


//ctor
    public TrieBranchAndBounds(ArrayList<Integer> input){
        this.root = new TrieNode();
        this.maxDepth = input.size();
        buildTree(input);
    }


//builds the tree
    private void buildTree(ArrayList<Integer> input){
        assert input != null: "Input is null";
        assert input.size() > 0: "Input size is < 1";
        buildTreeRecursive(this.root,input,0,"");

    }

//recursive helper to build tree
    private void buildTreeRecursive(TrieNode cur, ArrayList<Integer> input, int depth, String word){
        if(depth >= input.size())
            return;

        char[] curIntChars = MyDictionary.getNumToChar().get(input.get(depth++)).toCharArray();
        for (char ch : curIntChars) {
            if(MyDictionary.getTrieDictionary().isPrefix(word+ ch)) {                                                   //this is the branch and bounds. if the current path is not a valid prefix, dont create it
                TrieNode nextNode = new TrieNode();
                cur.children.put(ch, nextNode);
                buildTreeRecursive(nextNode, input, depth, word + ch);
            }
        }
    }


//gets all valid words
    public ArrayList<String> getAllWords(){
        if(this.root.children.isEmpty())
            return new ArrayList<>();
        return getAllWords(this.root, new ArrayList<String>() , "");
    }


//get all words recursive helper
    private ArrayList<String> getAllWords(TrieNode cur, ArrayList<String> words,String word){
        if(word.length() == maxDepth && MyDictionary.getTrieDictionary().containsWord(word))
            words.add(word);
        if(cur.children.isEmpty())
            return words;

        Iterator<Character> itr = cur.children.keySet().iterator();
        Map<Character, TrieNode> children = cur.children;
        while (itr.hasNext()){
            Character childLetter = itr.next();
            getAllWords(cur.children.get(childLetter), words, word + childLetter);
        }

        return words;
    }


//TrieNode class
    private class TrieNode{
        TreeMap<Character,TrieNode> children = new TreeMap<>();                                                             //similar to haschTable but keys are sorted (key,value)
    }
}
