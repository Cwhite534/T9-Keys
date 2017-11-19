import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Casey on 2/14/2017.
 * this class is used to search for valid words and prefixes
 */
public class DictionaryTrie {
    private TrieNode root;

//ctor
    public DictionaryTrie(){
            this.root = new TrieNode();
        }


//insert to dictionary method inserts the word into the tree
    public void insert(String toAdd){           //ensures the string is in the trie, used to load the dictionary into the trie
        TrieNode cur = root;

        for(char ch : toAdd.toCharArray()){
            Map<Character, TrieNode> children = cur.children;
            if(children.containsKey(ch)) {                                          //if the next letter in the word is already in the children map, go to it
                cur = children.get(ch);
            }
            else{
                TrieNode nextChild = new TrieNode();
                cur.children.put(ch,nextChild);		                                //else create a child for the letter and go to it
                cur = nextChild;
            }
        }
        cur.isWord = true;
    }//end insert


//Contains method returns true if a word or prefix  is contained in the tree
        public TrieNode contains(String toSearch) {
            if(toSearch.isEmpty() || toSearch.length() < 1)
                return null;
            Map<Character, TrieNode> children = root.children;
            TrieNode nextChild = null;
            for(int i=0; i<toSearch.length(); i++){
                char ch = toSearch.charAt(i);
                if(children.containsKey(ch)){                           //if the next letter in the word is in the children map, go to it
                    nextChild = children.get(ch);
                    children = nextChild.children;
                }
                else{                                                   //if not then the tree does not contain the word
                    return null;
                }
            }
            return nextChild;
        }



//contains word
    public boolean containsWord(String toSearch){
        TrieNode endWord = contains(toSearch);
        if(endWord != null && (endWord.isLeaf() || endWord.isWord))
            return true;
        else
            return false;
    }

//returns true if the string being passed is a prefix to a valid word
        public boolean isPrefix(String prefix){
            TrieNode endWord = contains(prefix);
            if(endWord != null)
                return true;
            else
                return false;
        }




//TrieNode class
        private class TrieNode{
            boolean isWord;
            Map<Character, TrieNode> children = new TreeMap<>();                                                             //similar to haschTable but keys are sorted (key,value)
            public boolean isLeaf(){
                return (children.isEmpty());
            }
        }
}

