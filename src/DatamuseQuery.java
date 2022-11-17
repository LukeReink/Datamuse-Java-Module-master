/*
 * Copyright © 2015 S.J. Blair <https://github.com/sjblair>
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */

package com.module.datamuse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * A handler for making calls to the Datamuse RESTful API.
 *
 * @author sjblair
 * @since 21/02/15
 */
public class DatamuseQuery {

    public DatamuseQuery() {

    }

    /**
     * Returns a list of similar words to the word/phrase supplied.
     * @param word A word of phrase.
     * @return A list of similar words.
     */
    public ArrayList<String> findSimilar(String word) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?rd="+s);
    }

    /**
     * like method above but from a different database - Word Net database
     * @param word word
     * @return synonyms for word
     */

    public ArrayList<String> findSynonym(String word){
        return getJSON("http://api.datamuse.com/words?rel_syn=" + word);
    }





    /**
     * finds word with the same consonant in same positions as parameter
     * @param word word
     * @return words with same consonants
     */

    public ArrayList<String> findConsonantMatch(String word){
        return getJSON("http://api.datamuse.com/words?rel_cns=" + word);
    }

    /**
     * Returns a list of similar words to the word/phrase supplied beginning with the specified letter(s).
     * @param word A word or phrase.
     * @param startLetter The letter(s) the similar words should begin with.
     * @return A list of similar words.
     */
    public ArrayList<String> findSimilarStartsWith(String word, String startLetter) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?rd="+s+"&sp="+startLetter+"*");
    }

    /**
     * Returns a list of similar words to the word/phrase supplied ending with the specified letter(s).
     * @param word A word or phrase.
     * @param endLetter The letter(s) the similar words should end with.
     * @return A list of similar words.
     */
    public ArrayList<String> findSimilarEndsWith(String word, String endLetter) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?rd="+s+"&sp=*"+endLetter);
    }

    /**
     * Returns a list of words beginning and ending with the specified letters and with the specified number of letters
     * in between.
     * @param startLetter The letter(s) the similar words should start with.
     * @param endLetter The letter(s) the similar words should end with.
     * @param numberMissing The number of letters between the start and end letters
     * @return A list of matching words.
     */
    public ArrayList<String>wordsStartingWithEndingWith(String startLetter, String endLetter, int numberMissing) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberMissing; i++) {
            sb.append("?");
        }
        return getJSON("http://api.datamuse.com/words?sp=" + startLetter + sb + endLetter);
    }

    /**
     * Returns a list of words beginning and ending with the specified letters and with an unspecified number of letters
     * in between.
     * @param startLetter The letter(s) the similar words should start with.
     * @param endLetter The letter(s) the similar words should end with.
     * @return A list of matching words.
     */
    public ArrayList<String> wordsStartingWithEndingWith(String startLetter, String endLetter) {
        return getJSON("http://api.datamuse.com/words?sp=" + startLetter + "*" + endLetter);
    }

    /** implemented our own method for rhymes with
     *
     * @param s is word given
     * @return list of words rhyming with
     */

    public ArrayList<String> rhymesWith (String s) {
        return getJSON("http://api.datamuse.com/words?rel_rhy=" + s);
    }


    /**
     * Find words which sound the same as the specified word/phrase when spoken.
     * @param word A word or phrase.
     * @return A list of words/phrases which sound similiar when spoken.
     */
    public ArrayList<String> soundsSimilar(String word) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?sl=" + s);
    }

    /**
     * Find words which are spelt the same as the specified word/phrase.
     * @param word A word or phrase.
     * @return A list of words/phrases which are spelt similar.
     */
    public ArrayList<String> speltSimilar(String word) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?sp=" + s);
    }

    /**
     * Returns suggestions for what the user may be typing based on what they have typed so far. Useful for
     * autocomplete on forms.
     * @param word The current word or phrase.
     * @return Suggestions of what the user may be typing.
     */
    public ArrayList<String> prefixHintSuggestions(String word) {
        String s = word.replaceAll(" ", "+");
        System.out.println(s);
        return getJSON("http://api.datamuse.com/sug?s=" + s);
    }

    /**
     * Query a URL for their source code.
     * @param url The page's URL.
     * @return The source code.
     */
    private ArrayList<String> getJSON(String url) {
        ArrayList<String> listofWords = new ArrayList<String>();
        URL datamuse;
        int begin = 0;
        boolean reachedQuote = false;
        int end = 0;
        URLConnection dc;
        StringBuilder s = null;
        try {
            datamuse = new URL(url);
            dc = datamuse.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(dc.getInputStream(), "UTF-8"));
            String inputLine;
            s = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                s.append(inputLine);
            //for each loop below is used to isolate each word into a string which we can add to the array list we have
            //only works for words with three part things
            in.close();
            String st = s.toString();
            //splits bigger chunks at the commas
            String [] words = st.split(",");
            for(String word: words)
                System.out.print(word);
            System.out.println();
            for (String word: words) {
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
                    listofWords.add(word.substring(begin, end));
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listofWords;
    }
    //takes the spliced string input (chunk) and outputs at what index the word is really occuring
    //based on the key word "word"
    private int getStartIndex(String chunk){
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
}