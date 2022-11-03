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
     * A JSON parser for the words returned in the data.
     *
     * @param in JSON data returned from the DatamuseQuery class.
     * @return An array of the words.
     */
    public String[] parseWords(String in) {
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();
        Map jsonData = parser.parseJson(in);
        List al = (List) jsonData.get("root");
        String[] results = new String[al.size()];
        for (int i = 0; i < al.size(); i++) {
            results[i] = (String) ((Map) al.get(i)).get("word");
        }
        return results;
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

    public int[] parseSyllables(String in) {
        JsonParserFactory factory = JsonParserFactory.getInstance();
        JSONParser parser = factory.newJsonParser();
        Map jsonData = parser.parseJson(in);
        List al = (List) jsonData.get("root");
        int[] results = new int[al.size()];
        for (int i = 0; i < al.size(); i++) {
            results[i] = Integer.parseInt((String) ((Map) al.get(i)).get("numSyllables"));
        }
        return results;
    }

    /**
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


                        if (word.substring(begin, end).equals(posIndicated)) {
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



        if (chooseToRemoveConjunctionWords && returned.size() > 0) {
            for (int i = 0; i < returned.size(); i++){
                for (int j = 0; j < conjunctionVerbs.size(); j++){
                    if (returned.get(i).equals(conjunctionVerbs.get(j))){
                        returned.remove(i);
                        if(i != 0)
                            i--;
//                        System.out.println(returned);
                    }
                }
            }
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