package com.module.datamuse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import java.io.IOException;

import com.json.utils.JSONUtility;
import org.json.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Datamuse {


    static JSONParse parser = new JSONParse();
    static DataMuseQueryWithParser data = new DataMuseQueryWithParser();
    static Scanner s = new Scanner(System.in);
    static boolean goAgain = true;

    /**
     * brought in from google; changes color of output text
     */
    // Declaring the color
    // Custom declaration
    // Declaring ANSI_RESET so that we can reset the color
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\033[0;91m";
    public static final String ANSI_ORANGE = "\033[0m";
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String ANSI_GREEN = "\033[0;92m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_BRITEPURPLE = "\033[0;95m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) throws JSONException {
        Scanner s = new Scanner(System.in);

        System.out.println("Welcome to our system! You will participate in 4 activities. First: Haiku generator! Ready? (press enter)");
        s.nextLine();
        haiku();
        System.out.println();
        System.out.println("Next: Alliteration Alternator! Ready? (press enter)");
        s.nextLine();
        alliterationGenerator();
        System.out.println();
        System.out.println("Next: Wicked Wordl! Ready? (you know what to do)");
        s.nextLine();
        wordl();
        System.out.println();
        System.out.println("Finally: Robot Sentence! Ready? (you guessed it, press E to the NTE to the R)");
        s.nextLine();
        robotSentence();
        System.out.println();
        System.out.println("WOW! That was a movie. Thanks for playing.");
        rainbowWrite("Cya!", true);
    }

    private static String[] getLC(String word) {
        DataMuseQueryWithParser dMuse = new DataMuseQueryWithParser();
        JSONParse parse = new JSONParse();
        String[] returned = parse.parseWords(dMuse.leftContext(word));
        for (int i = 0; i < returned.length; i++) {
            if (returned[i].equals(".")) {
                if (i == returned.length - 1)
                    returned[i] = returned[i - 1];
                else
                    returned[i] = returned[i + 1];
            }
        }
        return returned;
    }








    public static ArrayList<String> getPoS(ArrayList<String> nouns) {
        int begin = 0;
        int end = 0;
        boolean reachedQuote = false;
        ArrayList<String> listofNouns = new ArrayList<>();
        ArrayList<String> listofn = new ArrayList<>();

        for (String n : nouns) {
            //returns where the word begins
            begin = getStartIndex(n);
            if (begin != -1) {
                end = begin + 1;
                while (!reachedQuote) {
                    //gives us where a quotation is (indicates end of a word)
                    if (n.charAt(end) == '"')
                        reachedQuote = true;
                    else
                        end++;
                }
                reachedQuote = false;
                listofNouns.add(n.substring(begin, end));
//                for (int i = 0; i < listofNouns.size(); i++){
//                    for (int j = 1; j < listofNouns.size(); j++){
//                        if (listofNouns.get(i).substring(i,j).equals("n")){
//                                listofn.add(listofNouns.get(i));
//                        }
//                    }
//                }
            }
        }
        return listofNouns;
    }



//    //randomizes index for array
//    public static int randomIndex(ArrayList<String> n){
//        return (int)(Math.random() * n.size()) + 1;
//    }



    private static String [] getRC(String word){
        DataMuseQueryWithParser dMuse = new DataMuseQueryWithParser();
        JSONParse parse = new JSONParse();
        String [] returned = parse.parseWords(dMuse.rightContext(word));
        for(int i = 0; i < returned.length; i++){
            if (returned[i].equals(".")){
                //TODO chance for error because word is repeated back to back in list (dont draw back to back from returned)
                if(i == returned.length-1)
                    returned[i] = returned[i-1];
                else
                    returned[i] = returned[i+1];
            }
        }
        return returned;
    }


    private static void printList(String [] list) {
        for(String word: list) {
            System.out.print(word + ", ");
        }
    }

    private static void printIntList(int [] list) {
        for(int i : list) {
            System.out.print(i + ", ");
        }
    }
    public static int getStartIndex(String chunk){
        int index = 0;
        while(index +4 < chunk.length()){
            if(chunk.substring(index, index+4).equals("word")) {
                return index + 7;
            }
            else
                index++;
        }
        //if you get this far word is not in the chunk
        //System.out.println("Looks like the chunk does not have the word 'word' in it");
        return -1;
    }

    /**
     *
     * @param array array
     * @return a value at a random index
     */
    public static String getRandom(ArrayList<String> array){
        return array.get(random(array.size())-1);
    }
















    //had to make index static to use it in main, but still be able to
    //edit it in other methods... maybe we can toy with other ways to do index
//    public static void main(String[] args)throws JSONException, IOException {
//        //this object has our hard coded "parser" that uses substring
//        //to get extract just the words from the returned list
//        DatamuseQuery data = new DatamuseQuery();
//        //this one can be used with the JSON parser we imported
//        DataMuseQueryWithParser dMuse = new DataMuseQueryWithParser();
//        JSONParse parse = new JSONParse();
//        String [] wordsR = parse.parseWords(dMuse.findSimilar("great"));
//        for(String word: wordsR){
//            System.out.print(word);
//        }
//        Scanner s = new Scanner(System.in);
//        ArrayList<String> words = new ArrayList<>();
////        ArrayList<String> prefixSuggestions = data.prefixHintSuggestions()
////        ArrayList<String> Synonyms = data.findSimilar();
////        ArrayList<String> rhymeWords = new ArrayList<>();
//////        ArrayList<String>
//
//
//        //goes through each word that was added and create equivelant
//        //rhyming words, similar words, autocompletes, etc.
//
////        while(words.size() < 4) {
////            System.out.println("Give me a 4 + letter word");
////            words.add(s.nextLine());
////        }
//
//        System.out.println(data.rhymesWith("flank"));
//        System.out.println(data.findSynonym("great"));
//        System.out.println(data.findSimilar("great"));
//
//
//
//    }

	private String url = "";

    /**
     * Starts the first step in the builder process.
     *
     * @return Datamuse
     */
    public Datamuse QueryBuilder() {
    	this.url = "http://api.datamuse.com/words?";
    	return this;
    }

    /**
     * Adds the 'ml' (means like) endpoint to the query.
     *
     * @param word The word to query, as a String
     * @return Datamuse
     */
    public Datamuse meansLike(String word) {
    	word = word.replaceAll(" ", "+");
    	if (this.url.equals("http://api.datamuse.com/words?")) {
    		this.url += "ml=" + word;
    	} else {
    		this.url += "&ml=" + word;
    	}
    	return this;
    }

    /**
     * Adds a max word limit endpoint to the query.
     *
     * @param limit The maximum number of words to be returned, as an int
     * @return Datamuse
     */
    public Datamuse max(int limit) {
        if (this.url.equals("http://api.datamuse.com/words?")) {
            this.url += "max=" + limit;
        } else {
            this.url += "&max=" + limit;
        }
        return this;
    }

    /**
     * Build the query and retrieve the array of JSON objects the is resulted.
     *
     * @return DatamuseObject[]
     */
    public DatamuseObject[] build() {
    	return parseJson(url);
    }

    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    public static class DatamuseObject {
        public String word;
        public int score;
        public List<String> tags;
    }

    public static int random(int max){
        return  (int)(Math.random()*max+1);
    }

    /**
     * another print method that writes in rainbow
     * @param chunk the thing they want to print
     * @param printLn whether wants to be printed with println or just print
     */

    public static void rainbowWrite(String chunk, boolean printLn){
        //ArrayList of the colors of the rainbow, length so that it can write full chunk
        ArrayList<String> Colors = new ArrayList<String>();
        //the chunk to be returned
        String rainbowChunk = "";
        //int to ensure that even with a blank space the color won't be skipped
        int offset = 0;
        //adding colors of the rainbow to Colors
        for(int i = 0; i < (chunk.length()/6 + 6); i++){
            Colors.add(ANSI_RED);
            Colors.add(ANSI_YELLOW);
            Colors.add(YELLOW_BRIGHT);
            Colors.add(ANSI_GREEN);
            Colors.add(ANSI_BLUE);
            Colors.add(ANSI_PURPLE);
        }
        for(int i = 0; i < chunk.length(); i++){
            //do not do anything if it is just a blank space baby
            if(chunk.charAt(i) == ' ') {
                rainbowChunk += chunk.charAt(i);
                offset++;
            }
            else
                rainbowChunk += (Colors.get(i-offset) + chunk.charAt(i) + ANSI_RESET);
            //^adding color to just the substring of one char, so that chunk changes colors each letter
        }
        //boolean for println vs. print
        if(printLn)
            System.out.println(rainbowChunk);
        else
            System.out.print(rainbowChunk);
    }

    /**
     * very similar to rainbowWrite but by changing the one **indicated** area we can implement different color patterns
     * @param chunk what they want printed
     * @param printLn whether they want it println or print
     */
    public static void christmasWrite(String chunk, boolean printLn){
        //ArrayList of the colors of the rainbow, length so that it can write full chunk
        ArrayList<String> Colors = new ArrayList<String>();
        //the chunk to be returned
        String christmasChunk = "";
        //int to ensure that even with a blank space the color won't be skipped
        int offset = 0;
        //adding colors of the rainbow to Colors, change numbers to how often you will alternate between colors
        for(int i = 0; i < (chunk.length()/2 + 2); i++){
            Colors.add(ANSI_RED);
            Colors.add(ANSI_GREEN);
        }
        for(int i = 0; i < chunk.length(); i++){
            //do not do anything if it is just a blank space baby
            if(chunk.charAt(i) == ' ') {
                christmasChunk += chunk.charAt(i);
                offset++;
            }
            else
                christmasChunk += (Colors.get(i-offset) + chunk.charAt(i) + ANSI_RESET);
            //^adding color to just the substring of one char, so that chunk changes colors each letter
        }
        //boolean for println vs. print
        if(printLn)
            System.out.println(christmasChunk);
        else
            System.out.print(christmasChunk);
    }

    /**
     * very similar to rainbowWrite but by changing the one **indicated** area we can implement different color patterns
     * @param chunk what they want printed
     * @param printLn whether they want it println or print
     */
    public static void patrioticWrite(String chunk, boolean printLn){
        //ArrayList of the colors of the rainbow, length so that it can write full chunk
        ArrayList<String> Colors = new ArrayList<String>();
        //the chunk to be returned
        String patrioticChunk = "";
        //int to ensure that even with a blank space the color won't be skipped
        int offset = 0;
        //adding colors of the rainbow to Colors, change numbers to how often you will alternate between colors
        for(int i = 0; i < (chunk.length()/2 + 2); i++){
            Colors.add(ANSI_RED);
            Colors.add(ANSI_WHITE);
            Colors.add(ANSI_BLUE);
        }
        for(int i = 0; i < chunk.length(); i++){
            //do not do anything if it is just a blank space baby
            if(chunk.charAt(i) == ' ') {
                patrioticChunk += chunk.charAt(i);
                offset++;
            }
            else
                patrioticChunk += (Colors.get(i-offset) + chunk.charAt(i) + ANSI_RESET);
            //^adding color to just the substring of one char, so that chunk changes colors each letter
        }
        //boolean for println vs. print
        if(printLn)
            System.out.println(patrioticChunk);
        else
            System.out.print(patrioticChunk);
    }

    private DatamuseObject[] parseJson(String url) {
        String json = null;
		try {
			json = readUrl(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
        Gson gson = new Gson();
        DatamuseObject[] page = gson.fromJson(json, DatamuseObject[].class);
        return page;
    }




}
