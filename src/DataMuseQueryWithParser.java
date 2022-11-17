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
        return getJSON("https://api.datamuse.com/words?rel_rhy=" + s + "&max=1000");
    }
    public String SynonymsforWordl(String s) {
        return getJSON("http://api.datamuse.com/words?rel_syn=" + s + "&max=1000");
    }
    public String soundsSimilarforWordl(String s) {
        return getJSON("http://api.datamuse.com/words?sl=" + s + "&max=1000");
    }
    public String AntonymsforWordl(String s) {
        return getJSON("http://api.datamuse.com/words?rel_ant=" + s + "&max=1000");
    }
    public String rCforWordl(String s){
            return getJSON("http://api.datamuse.com/words?lc=" + s + "&max=1000");
    }

    public String leftContext(String lC) {
        return getJSON("http://api.datamuse.com/words?lc=" + lC + "&md=sp");
        //md = metadata, letters indicate what you want returned
    }

    public String leftContextforRobotSentence(String lC) {
        int randomNum = (int)(Math.random()*5+1);
        String topic = "";
        if(randomNum == 1)
            topic = "robots";
        else if(randomNum == 2)
            topic = "arm";
        else if(randomNum == 3)
            topic = "brain";
        else if(randomNum == 4)
            topic = "machine";
        else if(randomNum == 5)
            topic = "cyborg";

        return getJSON("http://api.datamuse.com/words?lc=" + lC + "&md=sp&topics="+topic);
        //md = metadata, letters indicate what you want returned
    }

    /**
     * Returns adj that modifies word to the right often
     *
     * @param rC word to the right
     * @return adj modifying rC
     */

    public String rcAdj(String rC) {
        return getJSON("http://api.datamuse.com/words?rel_jjb=" + rC + "&md=sp");
    }

    public String wordsRelatedtoinput(String noun) {
        return getJSON("http://api.datamuse.com/words?rel_trg=" + noun + "&md=sp");
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
        return getJSON("http://api.datamuse.com/words?rd=" + s + "&sp=" + startLetter + "*");
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
        return getJSON("http://api.datamuse.com/words?sp=" + startLetter + "*" + endLetter);
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
}