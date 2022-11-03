package com.module.datamuse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import java.io.IOException;
import org.json.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Datamuse {


    public static void main(String[] args) throws JSONException {
        Scanner s = new Scanner(System.in);
        JSONParse parser = new JSONParse();
        DataMuseQueryWithParser data = new DataMuseQueryWithParser();
        System.out.println("Give us a noun");
        String noun = s.nextLine();
        System.out.println("Give us another noun");
        String noun1 = s.nextLine();
        String adj = parser.parseWords(data.rcAdj(noun))[random(parser.parseWords(data.rcAdj(noun)).length)];
        String verb = getRandom(parser.parsePartsofSpeech(data.wordsRelatedtoinput(noun),"v",true));
        System.out.println("At night, the " + adj + " " + noun + " is " + verb);
        String adj1 = parser.parseWords(data.rcAdj(noun1))[random(parser.parseWords(data.rcAdj(noun1)).length)];
        String verb1 = getRandom(parser.parsePartsofSpeech(data.rhymesWith(verb,noun1),"v",true));
        System.out.println("Yet at day the " + adj1 + " " + noun1 + " is " + verb1);
//        ArrayList<String> PoS = parser.parsePartsofSpeech(data.leftContext(noun),"n",true);
//        System.out.println("POS: " + PoS);

       // String adj2 = parser.parseWords(data.rcAdj(noun))[2];
//        System.out.println("The " + adj + " " + noun + " is " + adj1);
//        System.out.println("The " + adj1 + " " + noun + " is " + PoS.get(randomIndex(PoS)));
//        System.out.println("The " + adj + " " + noun + " is " + parser.parseWords(data.lcVerbs(noun))[0]);




//        String verb =
//                System.out.println("The " + adj + " " + noun + " is " + parser.parseWords(data.lcVerbs(pNoun))[0]);


//        DataMuseQueryWithParser dMuse = new DataMuseQueryWithParser();
//        JSONParse parse = new JSONParse();
//        System.out.println(dMuse.leftContext("when"));
//        printIntList(parse.parseSyllables(dMuse.leftContext("when")));
        //how do I get the individual parts of speech so that I can compare them (should I rewrite the .equals method in the object class)


//        String[] leftContexts = (getLC("do"));
//        System.out.println("When ");
//        String finalWord = "";
//        String lastWord = "";
//        String oWord = "smile";
//        lastWord = getLC(oWord)[0];
//        finalWord = oWord + " " + lastWord;
//        for (int i = 0; i < 8; i++) {
//            finalWord = finalWord + " " + getLC(lastWord)[0];
//            lastWord = getLC(lastWord)[0];
//            System.out.println(finalWord);
//        }


    }

    private static String[] getLC(String word) {
        DataMuseQueryWithParser dMuse = new DataMuseQueryWithParser();
        JSONParse parse = new JSONParse();
        String[] returned = parse.parseWords(dMuse.leftContext(word));
        for (int i = 0; i < returned.length; i++) {
            if (returned[i].equals(".")) {
                //TODO chance for error because word is repeated back to back in list (dont draw back to back from returned)
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



    //randomizes index for array
    public static int randomIndex(ArrayList<String> n){
        return (int)(Math.random() * n.size()) + 1;
    }



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
