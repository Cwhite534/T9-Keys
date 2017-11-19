import java.util.*;

/**
 * Created by Casey on 2/7/2017.
 * This class is used to test the functionality of t9 solver using the trie dictionary the hash dictionary and the branch and bounds method
 *
 * 8378464 ->> testing
 * 99226363235 ->> zwaanendael
 * 78737266788464 -> supercomputing
 * 78737342425 -> superficial
 */
public class Tester {

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        MyDictionary.loadDictionary();                                                                                  //loads the dictionaries from the file
        long endTime = System.currentTimeMillis();
        Long loadTime = endTime-startTime;
        System.out.printf("Time to load dictionary for HashTable and Prefix trie: %,d milli-seconds\n",loadTime);

        int menuOption = 0;

//testTrie(false);
        while(menuOption != 6){
            displayMenu();
            menuOption = menuInput();
            menuLogic(menuOption);
        }

    }





    //displays the menu to the user
    private static void displayMenu(){
        System.out.print("T9 words menu" +
                "\n1.) Test using a hashTable dictionary\n" +
                "2.) Test a trie dictionary\n" +
                "3.) Test Branch and Bounds\n" +
                "4.) Run Complete Comparative test\n" +
                "5.) Test dictionary(enter word)\n" +
                "6.) Exit\n" +
                "Please enter a menu option:");
    }

//performs the option the user has entered
    private static void menuLogic(int option){
        switch (option){
            case 1:
                testTrieorHash(intSequenceInput(),false);
                break;
            case 2:
                testTrieorHash(intSequenceInput(),true);
                break;
            case 3:
                testTrieBounds(intSequenceInput());
                break;
            case 4:
                completeTest();
                break;
            case 5:
                testDictionary();
                break;
            case 6:
                System.out.println("Exiting... Goodbye\n");
                break;
            default:
                System.out.println("Invalid menu option, Please re-enter the number:");
        }
    }


//Tests the time of the trie
    private static double testTrieorHash(ArrayList<Integer> input, boolean checkTrie){
        if (input.size() >= 15){
            System.out.printf("You must use branch and bounds for words of length 15 or greater. this sequence creates %,.0f nodes, and out of memory exception is produced\n\n",Math.pow(4,input.size()));
            return 0;
        }

    //checks time to build trie
        long startTime = System.nanoTime();
        TrieSolver solver = new TrieSolver(input);
        long endTime = System.nanoTime();
        Long buildTime = endTime-startTime;



    //checks time to retrieve words
        ArrayList<String> allWords = new ArrayList<>();
        String check = "HashTable Dictionary";
        startTime = System.nanoTime();
        if(checkTrie) {                                                                         //for either hash or trieallWords = solver.getAllValidWordsTrie();
            check = "Trie Dictionary";
            allWords = solver.getAllValidWordsTrie();
        }
        else
            allWords = solver.getAllValidWordsHash();
        endTime = System.nanoTime();
        long getWordsTime = endTime - startTime;
        double totalTime = (double)(buildTime+getWordsTime+0) / 1000000.0;

     //prints time to perform operations
        System.out.println("\nTesting using " + check);
        System.out.printf("%-30.30s %-30.30s\n","   Method Ran", "   Time Elapsed");
        System.out.printf("%-30.30s %,10d nano-seconds\n","Build time",buildTime);
        System.out.printf("%-30.30s %,10d nano-seconds\n","Get all Words",getWordsTime);
        //System.out.printf("%-30.30s %,10d nano-seconds\n","Check " + check,validWordTime);
        System.out.printf("%-30.30s %10.3f milli-seconds\n\n","Total", totalTime);

        printWords(allWords);
        return totalTime;
    }


//Tests the time of the trie with branch and bounds
    private static double testTrieBounds(ArrayList<Integer> input){

    //checks time to build trie
        long startTime = System.nanoTime();
        TrieBranchAndBounds solver = new TrieBranchAndBounds(input);
        long endTime = System.nanoTime();
        Long buildTime = endTime-startTime;


    //checks time to retrieve all possible words
        startTime = System.nanoTime();
        ArrayList<String> allWords = solver.getAllWords();
        endTime = System.nanoTime();
        long getWordsTime = endTime - startTime;

        double totalTime = (double)(buildTime+getWordsTime) / 1000000.0;

        System.out.println("\nTesting using Branch and bounds");
        System.out.printf("%-30.30s %-30.30s\n","   Method Ran", "   Time Elapsed");
        System.out.printf("%-30.30s %,10d nano-seconds\n","Branch And Bounds Build Time:",buildTime);
        System.out.printf("%-30.30s %,10d nano-seconds\n","Get all Valid Words",getWordsTime);
        System.out.printf("%-30.30s %10.3f milli-seconds\n\n","Total", totalTime);

        printWords(allWords);
        return totalTime;
    }

//runs complete test comparing times for hashtable dictionary trie dictionary and branch and bounds
    private static void completeTest(){
        System.out.println("******Running 5 tests using all 3 implementations of the solver******");
        double totalTimeHash = 0;
        double totalTimeTrie = 0;
        double totalTimeBranch = 0;

    //creates all test integer sequences
        ArrayList<ArrayList<Integer>> myTester = new ArrayList<>();
        ArrayList<Integer> test1 = new ArrayList<>();
        test1.add(2); test1.add(2); test1.add(2);
        ArrayList<Integer> test2 = new ArrayList<>();
        test2.add(3); test2.add(3); test2.add(3); test2.add(3);
        ArrayList<Integer> test3 = new ArrayList<>();
        test3.add(8); test3.add(3); test3.add(7); test3.add(8); test3.add(4); test3.add(6); test3.add(4);
        ArrayList<Integer> test4 = new ArrayList<>();
        test4.add(9);  test4.add(9);  test4.add(2);  test4.add(2);  test4.add(6);  test4.add(3);  test4.add(6);  test4.add(3);  test4.add(2);  test4.add(3);  test4.add(5);
        ArrayList<Integer> test5 = new ArrayList<>();
        test5.add(7); test5.add(8); test5.add(7); test5.add(3); test5.add(7); test5.add(3); test5.add(4); test5.add(2); test5.add(4); test5.add(2); test5.add(5);
        myTester.add(test1); myTester.add(test2); myTester.add(test3); myTester.add(test4); myTester.add(test5);

        for ( ArrayList cur : myTester) {
            System.out.println("Testing sequence: " + cur.toString());
            totalTimeHash += testTrieorHash(cur,false);  //test hash
            totalTimeTrie += testTrieorHash(cur,true);   //test trie
            totalTimeBranch += testTrieBounds(cur);        //test branch and bounds
        }

        System.out.printf("Average time using hash dictionary: %.3f\n" +
                "Average time using trie dictionary: %.3f\n" +
                "Average time using branch and bounds: %.3f\n" +
                "Branch and bounds is %.3f times faster than hashtable dictionary\n" +
                "Branch and bounds is %.3f times faster than trie dictionary\n\n",totalTimeHash/myTester.size(),totalTimeTrie/myTester.size(),totalTimeBranch/myTester.size(),
                (totalTimeHash/myTester.size())/(totalTimeBranch/myTester.size()), (totalTimeTrie/myTester.size())/(totalTimeBranch/myTester.size()));



    }

//tests the dictionary
    private static void testDictionary(){
        Hashtable<String,String> hashDictionary = MyDictionary.getHashDictionary();
        DictionaryTrie trieDictionary = MyDictionary.getTrieDictionary();
        String input = strInput();

        System.out.printf("checking if %s is a word\n",input);
        System.out.printf("Hash Table: %s\n",hashDictionary.containsKey(input));
        System.out.printf("Prefix Trie: %s\n\n",trieDictionary.containsWord(input));

    }


//prints all the valid words in tree by letter
    private static void printWords(ArrayList<String> validWords){
        if(validWords == null || validWords.isEmpty())
            System.out.println("There were no valid words for the sequence\n");
        else {
            System.out.println("Printing all valid words: ");

            char firstChar = validWords.get(0).charAt(0);
            for (String word : validWords) {
                if (firstChar != word.charAt(0))
                    System.out.println();
                firstChar = word.charAt(0);
                System.out.printf(word + " ");
            }
            System.out.println("\n");
        }
    }





//receives integer input
    private static int menuInput() {
        Scanner kb = new Scanner(System.in);
        int num = 0;

        try {
            num = kb.nextInt();
            System.out.println();
            kb.nextLine();
            if(num >-1)
                return num;
            else {
                System.out.print("That is not a valid menu Option, Please re-enter your choice:");
                return menuInput();
            }
        }
        catch(InputMismatchException e) {
            System.out.print("\nInvalid integer, Please re-enter the number:");
            kb.nextLine();
            return menuInput();
        }
    }

//recieves string input
    private static String strInput() {
        System.out.print("Please enter a string:");
        Scanner kb = new Scanner(System.in);
        String input = "";

        try {
            input = kb.nextLine();
            input = input.replaceAll("\\W", "");
            if(!input.isEmpty())
                return input;
            else {
                System.out.print("That is not a valid string.");
                return strInput();
            }
        }
        catch(InputMismatchException e) {
            System.out.print("\nInvalid integer, Please re-enter the number:");
            kb.nextLine();
            return strInput();
        }
    }

// receives integer sequence input
    private static ArrayList<Integer> intSequenceInput() {
        System.out.print("Enter a sequence of numbers with any format EG(\"328\",\"3,2,8\",\"3 2 8\"):");
        Scanner kb = new Scanner(System.in);
        String input = null;
        ArrayList<Integer> intSequence = new ArrayList();

        try {
            input = kb.nextLine();
            if(input != null && !input.isEmpty()) {
                input = input.replaceAll("\\D", "");
                char[] inputChar = input.toCharArray();
               for(char ch : inputChar){
                    intSequence.add(Character.getNumericValue(ch));
                }
                return intSequence;
            }
            else {
                System.out.print("That is not a valid number sequence\n");
                return intSequenceInput();
            }
        }
        catch(Exception e) {
            System.out.print("\nInvalid sequence, Please re-enter the number:");
            return intSequenceInput();
        }

    }







}
