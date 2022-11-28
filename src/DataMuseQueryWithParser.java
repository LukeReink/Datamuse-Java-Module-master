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
public class DataMuseQueryWithParser {

    public DataMuseQueryWithParser() {

    }

    /**
     * Returns a list of similar words to the word/phrase supplied.
     *
     * @param word A word of phrase.
     * @return A list of similar words.
     */
    public String findSimilar(String word) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?rd=" + s);
    }

    public String rhymesWith(String s, String d) {
        return getJSON("http://api.datamuse.com/words?rel_rhy=" + s + "&md=sp" + "&topics=" + d);
    }

    public String rhymesWithforWordl(String s) {
        //max = 1000, returns 1000 max results, higher than the default, 50
        return getJSON("https://api.datamuse.com/words?rel_rhy=" + s + "&max=1000");
    }

    public String SynonymsforWordl(String s) {
        String w = s.replaceAll(" ", "+");
        //max = 1000, returns 1000 max results, higher than the default, 50
        return getJSON("http://api.datamuse.com/words?rel_syn=" + w + "&max=1000");
    }

    public String meansLike(String s) {
        return getJSON("http://api.datamuse.com/words?ml=" + s + "&max=50");
    }


    public String meansLikeforRhetoric(String s) {
        return getJSON("http://api.datamuse.com/words?ml=" + s + "&max=250");
    }

    public String soundsSimilarforWordl(String s) {
        //max = 1000, returns 1000 max results, higher than the default, 50
        return getJSON("http://api.datamuse.com/words?sl=" + s + "&max=1000");
    }

    public String AntonymsforWordl(String s) {
        //max = 1000, returns 1000 max results, higher than the default, 50
        return getJSON("http://api.datamuse.com/words?rel_ant=" + s + "&max=1000");
    }

    public String rCforWordl(String s) {
        return getJSON("http://api.datamuse.com/words?lc=" + s + "&max=1000");
    }

    public String leftContext(String lC) {
        return getJSON("http://api.datamuse.com/words?lc=" + lC + "&md=sp");
        //md = metadata, letters indicate what you want returned
    }

    public String leftContextforRobotSentence(String lC) {
        int randomNum = (int) (Math.random() * 6 + 1);
        String topic = "";
        if (randomNum == 1)
            topic = "robots";
        else if (randomNum == 2)
            topic = "arm";
        else if (randomNum == 3)
            topic = "brain";
        else if (randomNum == 4)
            topic = "machine";
        else if (randomNum == 5)
            topic = "cyborg";
        else if (randomNum == 6)
            topic = "simulate";

        return getJSON("http://api.datamuse.com/words?lc=" + lC + "&md=sp&topics=" + topic);
        //md = metadata, letters indicate what you want returned
    }

    /**
     * Returns adj that modifies word to the right often
     *
     * @param rC word to the right
     * @return adj modifying rC
     */

    public String rcAdj(String rC) {
        String s = rC.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?rel_jjb=" + s + "&md=sp" + "max=300");
    }

     /** Returns adj that modifies word to the right often; adjusted for larger results for rhetorical device generator
     * @param rC word to the right
     * @return adj modifying rC
     **/

    public String rcAdjforRhetoric(String rC) {
        String s = rC.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?rel_jjb=" + s + "&md=sp" + "&max=1000");
    }

    public String wordsRelatedtoinput(String noun) {
        String s = noun.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?rel_trg=" + s + "&md=sp" + "&max=350");
    }


    public String rightContext(String RC) {
        return getJSON("http://api.datamuse.com/words?lc=" + RC + "&md=sp");
    }


    /**
     * Returns a list of similar words to the word/phrase supplied beginning with the specified letter(s).
     *
     * @param word        A word or phrase.
     * @param startLetter The letter(s) the similar words should begin with.
     * @return A list of similar words.
     */
    public String findSimilarStartsWith(String word, String startLetter) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?rd=" + s + "&sp=" + startLetter + "*&max=1000");
    }

    /**
     * Returns a list of similar words to the word/phrase supplied ending with the specified letter(s).
     *
     * @param word      A word or phrase.
     * @param endLetter The letter(s) the similar words should end with.
     * @return A list of similar words.
     */
    public String findSimilarEndsWith(String word, String endLetter) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?rd=" + s + "&sp=*" + endLetter);
    }

    /**
     * Returns a list of words beginning and ending with the specified letters and with the specified number of letters
     * in between.
     *
     * @param startLetter   The letter(s) the similar words should start with.
     * @param endLetter     The letter(s) the similar words should end with.
     * @param numberMissing The number of letters between the start and end letters
     * @return A list of matching words.
     */
    public String wordsStartingWithEndingWith(String startLetter, String endLetter, int numberMissing) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberMissing; i++) {
            sb.append("?");
        }
        return getJSON("http://api.datamuse.com/words?sp=" + startLetter + sb + endLetter);
    }

    /**
     * Returns a list of words beginning and ending with the specified letters and with an unspecified number of letters
     * in between.
     *
     * @param startLetter The letter(s) the similar words should start with.
     * @param endLetter   The letter(s) the similar words should end with.
     * @return A list of matching words.
     */
    public String wordsStartingWithEndingWith(String startLetter, String endLetter) {
        return getJSON("http://api.datamuse.com/words?sp=" + startLetter + "*" + endLetter + "&md=p&max=300");
    }


    public String wordsStartingWith(String startLetter) {
        return getJSON("http://api.datamuse.com/words?sp=" + startLetter + "*&md=p&max=300");
    }


    /**
     * Find words which sound the same as the specified word/phrase when spoken.
     *
     * @param word A word or phrase.
     * @return A list of words/phrases which sound similiar when spoken.
     */
    public String soundsSimilar(String word) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?sl=" + s);
    }

    /**
     * Find words which are spelt the same as the specified word/phrase.
     *
     * @param word A word or phrase.
     * @return A list of words/phrases which are spelt similar.
     */
    public String speltSimilar(String word) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/words?sp=" + s);
    }

    /**
     * Returns suggestions for what the user may be typing based on what they have typed so far. Useful for
     * autocomplete on forms.
     *
     * @param word The current word or phrase.
     * @return Suggestions of what the user may be typing.
     */
    public String prefixHintSuggestions(String word) {
        String s = word.replaceAll(" ", "+");
        return getJSON("http://api.datamuse.com/sug?s=" + s);
    }

    /**
     * Query a URL for their source code.
     *
     * @param url The page's URL.
     * @return The source code.
     */
    private String getJSON(String url) {
        URL datamuse;
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
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s != null ? s.toString() : null;
    }


    /**
     * * @param word the word inputted
     *
     * @return number of syllables of the word
     */
    public int hemidevisemiquaver(String word) {
        String n = getJSON("https://api.datamuse.com/words?sp=" + word + "&qe=sp&md=s&max=1");
        int o = 0;
        for (int i = 0; i < n.length(); i++) {
            if (n.substring(i, i + 12).equals("numSyllables")) {
                //TODO: only works for words 9 syllables and under
                o = Integer.parseInt(n.substring(i + 14, i + 15));
                i = n.length();
            }
        }
        if (n.equals("[]")) {
            /**
             * COPIED from Google - Syllable Counter for When the Word is not in the DataMuse Database (https://stackoverflow.com/questions/9154027/java-writing-a-syllable-counter-based-on-specifications)
             */
            int count = 0;
            word = word.toLowerCase();
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == '\"' || word.charAt(i) == '\'' || word.charAt(i) == '-' || word.charAt(i) == ',' || word.charAt(i) == ')' || word.charAt(i) == '(') {
                    word = word.substring(0, i) + word.substring(i + 1, word.length());
                }
            }
            boolean isPrevVowel = false;
            for (int j = 0; j < word.length(); j++) {
                if (word.contains("a") || word.contains("e") || word.contains("i") || word.contains("o") || word.contains("u")) {
                    if (isVowel(word.charAt(j)) && !((word.charAt(j) == 'e') && (j == word.length() - 1))) {
                        if (!isPrevVowel) {
                            count++;
                            isPrevVowel = true;
                        }
                    } else {
                        isPrevVowel = false;
                    }
                } else {
                    count++;
                    break;
                }
            }
            return count;
        }

        return o;
    }

    /**
     * COPIED from Google - Syllable Counter for When the Word is not in the DataMuse Database (https://stackoverflow.com/questions/9154027/java-writing-a-syllable-counter-based-on-specifications)
     **/
    public boolean isVowel(char c) {
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }


    /**
     * get parts of speech for a word
     *
     * @param word the word
     * @return ArrayList of possile parts of speech of the word inputted
     */


    public ArrayList<String> getPartsofSpeech(String word) {
        String n = getJSON("https://api.datamuse.com/words?sp=" + word + "&qe=&md=p&max=1");
        int begin = 0;
        int end = 0;
        ArrayList<String> listofWords = new ArrayList<String>();
        boolean reachedQuote = false;
        boolean stillGoing = true;
        try {
            for (int i = 1; i < n.length(); i++) {
                if (n.charAt(i) == '[') {
                    begin = i + 2;
                    while (stillGoing) {
                        end = begin + 1;
                        while (!reachedQuote) {
                            //gives us where a quotation is (indicates end of a word)
                            if (n.charAt(end) == '"')
                                reachedQuote = true;
                            else
                                end++;
                        }
                        reachedQuote = false;
                        listofWords.add(n.substring(begin, end));
                        begin = end + 3;
                        if (n.charAt(end + 2) != '"')
                            stillGoing = false;
                    }
                }
            }
            return listofWords;
        } catch (Exception e) {
            ArrayList<String> invalid = new ArrayList<>();
            invalid.add(" ");
            return invalid;
        }
    }
}