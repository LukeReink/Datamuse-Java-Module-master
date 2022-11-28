/*
 * Copyright © 2015 S.J. Blair <https://github.com/sjblair>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package com.module.datamuse;

import com.json.parsers.JsonParserFactory;
import com.json.parsers.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * A JSON parser for the data returned from Datamuse.
 *
 * @author sjblair
 * @since 21/02/15
 */
public class JSONParse {

    public static int[] indexOpOs = new int[1000];

    public JSONParse() {
        Arrays.fill(indexOpOs, -1);
    }

    /**
     * A JSON parser for the words returned in the data. Taken from open source.
     *
     * @param in JSON data returned from the DatamuseQuery class.
     * @return An array of the words.
     */
    public String [] parseWords(String in) {
        //"conjunctionVerbs" is really just any words we would like never returned in our program
        ArrayList<String> conjunctionVerbs = new ArrayList<>();
        conjunctionVerbs.add(".");
        //because of rhetorical device generator we do not want this
        conjunctionVerbs.add("like");
        try {
            //if string returned is empty, return spanish so that errors aren't thrown
            if (in.equals("[]")) {
                String[] invalid = {"inválido"};
                return invalid;
            }
        }
        catch(Exception e) {
                String[] invalid = {"inválido"};
                return invalid;
            }
            JsonParserFactory factory = JsonParserFactory.getInstance();
            JSONParser parser = factory.newJsonParser();
            Map jsonData = parser.parseJson(in);
            List al = (List) jsonData.get("root");
            String[] results = new String[al.size()];
            for (int i = 0; i < al.size(); i++) {
                results[i] = (String) ((Map) al.get(i)).get("word");
            }
            //remove conjunction verbs by setting values at indexes with original conjunction verbs to a list then not adding them to the final returned list
        //count how many conjunction verbs there are
        int count = 0;
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < conjunctionVerbs.size(); j++) {
                if (results[i].equals(conjunctionVerbs.get(j))) {
                    results[i] = "nah";
                    count++;
                }
            }
        }
        //use new index to count where we are adding the new words onto the returned list
        int index = 0;
        //new list length of OG list minus number of conjunction verbs so that you can add all other words to the
        String [] returned = new String[results.length-count];
        for(int i = 0; i < results.length; i++){
            if(!(results[i].equals("nah"))){
                returned[index] = results[i];
                index++;

            }

        }
            return results;
    }



    /**
     * Overloaded method for parsewords which removes any conjunction words we can think of... this one returns an arraylist instead of a normal list
     * @param in
     * @param f
     * @return
     */
    public ArrayList<String> parseWords(String in, boolean f) {
        ArrayList<String> conjunctionVerbs = new ArrayList<>();
        conjunctionVerbs.add(".");
        conjunctionVerbs.add("and");
        conjunctionVerbs.add("is");
        conjunctionVerbs.add("with");
        conjunctionVerbs.add("in");
        conjunctionVerbs.add("for");
        conjunctionVerbs.add("would");
        conjunctionVerbs.add("of");
        conjunctionVerbs.add("to");
        conjunctionVerbs.add("for");
        conjunctionVerbs.add("would");
        conjunctionVerbs.add("of");
        conjunctionVerbs.add("to");
        conjunctionVerbs.add("was");
        conjunctionVerbs.add("did");
        conjunctionVerbs.add("were");
        conjunctionVerbs.add("or");
        conjunctionVerbs.add(".");
        conjunctionVerbs.add("the");
        conjunctionVerbs.add("a");
        conjunctionVerbs.add("that");
        conjunctionVerbs.add("all");
        conjunctionVerbs.add("like");
        if (in.equals("[]")) {
            //if string returned is empty, return spanish so that errors aren't thrown
            ArrayList<String> invalid = new ArrayList<String>();
            invalid.add("inválido");
            return invalid;
        } else {
            JsonParserFactory factory = JsonParserFactory.getInstance();
            JSONParser parser = factory.newJsonParser();
            Map jsonData = parser.parseJson(in);
            List al = (List) jsonData.get("root");
            ArrayList<String> results = new ArrayList<>();
            for (int i = 0; i < al.size(); i++) {
                results.add((String) ((Map) al.get(i)).get("word"));
            }
            for (int i = 0; i < results.size(); i++) {
                for (int j = 0; j < conjunctionVerbs.size(); j++) {
                    if (results.get(i).equals(conjunctionVerbs.get(j))) {
                        results.remove(i);
                        if (i != 0)
                            i--;
//                        System.out.println(returned);
                    }
                }
            }
            return results;
        }
    }

    /**
     * A JSON parser for the word scores returned in the data.
     *
     * @param in JSON data returned from the DatamuseQuery class.
     * @return An array of the scores.
     */
    public int[] parseScores(String in) {
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();
        Map jsonData = parser.parseJson(in);
        List al = (List) jsonData.get("root");
        int[] results = new int[al.size()];
        for (int i = 0; i < al.size(); i++) {
            results[i] = Integer.parseInt((String) ((Map) al.get(i)).get("score"));
        }
        return results;
    }

    /**
     * Returns an array list of all of the words that are a part of the returned Json file that match the syllable count indicated (n)
     * @param in
     * @param n number of sylabbles want returned
     * @return
     */

    public ArrayList<String> parseSyllables(String in, int n){
            JsonParserFactory factory = JsonParserFactory.getInstance();
            ArrayList<String> conjunctionVerbs = new ArrayList<>();
            conjunctionVerbs.add("and");
            conjunctionVerbs.add("is");
            conjunctionVerbs.add("with");
            conjunctionVerbs.add("in");
            conjunctionVerbs.add("for");
            conjunctionVerbs.add("would");
            conjunctionVerbs.add("of");
            conjunctionVerbs.add("to");
            conjunctionVerbs.add("for");
            conjunctionVerbs.add("would");
            conjunctionVerbs.add("of");
            conjunctionVerbs.add("to");
            conjunctionVerbs.add("was");
            conjunctionVerbs.add("did");
            conjunctionVerbs.add("were");
            conjunctionVerbs.add("or");
            int index = 0;
            String[] wordList = parseWords(in);
            ArrayList<String> resulted = new ArrayList<String>();
            JSONParser parser = factory.newJsonParser();
            //checks if the returned is null for try catch errors
            if(in.equals("[]"))
                resulted.add("inválido");
            else {

                Map jsonData = parser.parseJson(in);
                List al = (List) jsonData.get("root");
                for (int i = 0; i < al.size(); i++) {
                    if (Integer.parseInt((String) ((Map) al.get(i)).get("numSyllables")) == n)
                        resulted.add(wordList[index]);
                    index++;
                }
                for (int i = 0; i < resulted.size(); i++) {
                    for (int j = 0; j < conjunctionVerbs.size(); j++) {
                        if (resulted.get(i).equals(conjunctionVerbs.get(j))) {
                            resulted.remove(i);
                            if (i != 0)
                                i--;
//                        System.out.println(returned);
                        }
                    }
                }
                if(resulted.size() == 0)
                    resulted.add("inválido");
            }
                return resulted;
    }

    /** Returns an array list of all of the words that are a part of the returned Json file that match the part of speech indicated (posIndicated)
     * @param in           huge json string
     * @param chooseToRemoveConjunctionWords if == 1 then it will remove conjunction words in the returned lists if == 0, otherwise
     * @param posIndicated indicated part of speech
     * @return all words in the string in that belong to the indicated part of speech
     * @throws JSONException
     */

    public ArrayList<String> parsePartsofSpeech(String in, String posIndicated, boolean chooseToRemoveConjunctionWords) throws JSONException {
        String[] wordList = parseWords(in);
//        System.out.println(in);
//        JSONObject obj = new JSONObject();
        int beginIndex;
        int begin;
        int end;
        int index = 0;
        int indexOfWord = 0;
        //ArrayList of conjunctions that if you choose you can remove from the returned ArrayList
        ArrayList<String> conjunctionVerbs = new ArrayList<>();


        boolean reachedQuote = false;
        boolean bracky = true;
        ArrayList<String> returned = new ArrayList<String>();
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();
        conjunctionVerbs.add("and");
        conjunctionVerbs.add("is");
        conjunctionVerbs.add("with");
        conjunctionVerbs.add("in");
        conjunctionVerbs.add("for");
        conjunctionVerbs.add("would");
        conjunctionVerbs.add("of");
        conjunctionVerbs.add("to");
        conjunctionVerbs.add("for");
        conjunctionVerbs.add("would");
        conjunctionVerbs.add("of");
        conjunctionVerbs.add("to");
        conjunctionVerbs.add("was");
        conjunctionVerbs.add("did");
        conjunctionVerbs.add("were");
        conjunctionVerbs.add("or");



        //if the json string has reached "tags" (the thing that always preceedes part of speech), parse the parts of speech using substring and quotation recognition
        for (int i = 0; i < in.length() - 6; i++) {
            if (in.substring(i, i + 4).equals("tags")) {
                beginIndex = i + 6;
                while (bracky) {
                    if (in.charAt(i) == '}')
                        bracky = false;
                    else
                        i++;
                }
                bracky = true;
                String[] words = in.substring(beginIndex, i).split(",");
                indexOpOs[index] = words.length;
                for (String word : words) {
                    //returns where the word begins
                    begin = getStartIndex(word);
                    if (begin != -1) {
                        end = begin + 1;
                        while (!reachedQuote) {
                            //gives us where a quotation is (indicates end of a word)
                            if (word.charAt(end) == '"')
                                reachedQuote = true;
                            else
                                end++;
                        }
                        reachedQuote = false;
//                 Debugging: System.out.println("Word in question: " + word.substring(begin,end));


                        if (word.substring(begin, end).equals(posIndicated) && indexOfWord < wordList.length) {
//                 Debugging: System.out.println("The word: " + wordList[indexOfWord-1]);
//                            System.out.println("The index: " + (indexOfWord-1));
//                            System.out.println("The chunk: " + in);
                            returned.add(wordList[indexOfWord - 1]);
                        }
//                    used for just getting parts of speech not for indicated (would also get rid of if statement)
                        //                    returned.add(word.substring(begin, end));

                    }
                }
                index++;
            } else if (in.substring(i, i + 4).equals("word"))
                indexOfWord++;
        }


        //removes all conjunction words from the returned list
        if (chooseToRemoveConjunctionWords && returned.size() > 0) {
            for (int i = 0; i < returned.size(); i++){
                for (int j = 0; j < conjunctionVerbs.size(); j++){
                    if (returned.get(i).equals(conjunctionVerbs.get(j))){
                        returned.remove(i);
                        if(i != 0)
                            i--;
                    }
                }
            }
        }
        //if returned is null add our invalido key word to the returned list
        if(returned.size() == 0) {
            returned.add("inválido");
        }
        if(returned.get(0).equals("[]")) {
            returned.add("inválido");
        }
        return returned;
    }
        private int getStartIndex (String chunk){
            int index = 0;
            while (index < chunk.length()) {
                if (chunk.charAt(index) == '"') {
                    return index + 1;
                } else
                    index++;
            }
            //if you get this far word is not in the chunk
            //System.out.println("Looks like the chunk does not have the word 'word' in it");
            return -1;
        }



    }