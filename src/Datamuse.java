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

    public static void robotSentence() {

        patrioticWrite("Welcome to Robot Sentence!", true);
        Scanner s = new Scanner(System.in);
        ArrayList<String> n = new ArrayList<>();

        System.out.println("Enter a starting word.");
        String startWord = s.nextLine();
        n.add(startWord);


        String finalWord;
        String lastWord;
        lastWord = getListRandom(getLC(startWord));
        finalWord = startWord + " " + lastWord;

        System.out.print(startWord + " ");
        for (int i = 0; i < 5; i++) {
            try {
                if(i != 4)
                    System.out.print(getRandom(parser.parsePartsofSpeech(data.leftContextforRobotSentence(startWord), "adj", true)) + " ");
                else
                    System.out.print(getRandom(parser.parsePartsofSpeech(data.leftContextforRobotSentence(startWord), "adj", true)) + ".");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
//            lastWord = getListRandom(parser.parseWords(data.leftContextforRobotSentence(startWord)));
//            finalWord = finalWord + " " + getListRandom(getLC(lastWord));
//            System.out.println(finalWord);
        }
    }

    public static void wordl() throws JSONException {
        //TODO: Make random hint each time there is a guess such as LC or a word that Sounds Similar (maybe different one each guess time but in a different random order each time too)
        //TODO: if you want to be able to draw from a larger database for the rhymes, you can use any previous rhymes to compare the new guess with too... same with the other words
        //TODO: Make a starts with same letter parameter aswell; Also remove synonyms that have the actual word in them for hints
        //TODO: remove the hint like rhymes with if that word has already been used!
        //list of words to guess
        DataMuseQueryWithParser dMuse = new DataMuseQueryWithParser();
        JSONParse parse = new JSONParse();
        Scanner s = new Scanner(System.in);
        String[] words = {"cigar", "rebut", "great", "humph", "awake", "blush", "focal", "evade", "naval", "serve", "heath", "dwarf", "model", "karma", "stink", "grade", "quiet", "bench", "abate", "feign", "major", "death", "fresh", "crust", "stool", "colon", "abase", "marry", "react", "batty", "pride", "floss", "helix", "croak", "staff", "paper", "unfed", "whelp", "trawl", "outdo", "adobe", "crazy", "sower", "repay", "digit", "crate", "cluck", "spike", "mimic",
                "pound", "maxim", "linen", "unmet", "flesh", "booby", "forth", "first", "stand", "belly", "ivory", "seedy", "print", "yearn", "drain", "bribe", "stout", "panel", "crass", "flume", "offal", "agree", "error", "swirl", "argue", "bleed", "delta", "flick", "totem", "wooer", "front", "shrub", "parry", "biome", "lapel", "start", "greet", "goner", "golem", "lusty", "loopy", "round", "audit", "lying", "gamma", "labor", "islet", "civic", "forge", "corny", "moult", "basic", "salad", "agate", "spicy", "spray", "essay", "fjord", "spend", "kebab", "guild", "aback", "motor", "alone", "hatch", "hyper", "thumb", "dowry", "ought", "belch", "dutch", "pilot", "tweed", "comet", "jaunt", "enema", "steed",
                "abyss", "growl", "fling", "dozen", "boozy", "erode", "world", "gouge", "click", "briar", "great", "altar", "pulpy", "blurt", "coast", "duchy", "groin", "fixer", "group", "rogue", "badly", "smart", "pithy", "gaudy", "chill", "heron", "vodka", "finer", "surer", "radio", "rouge", "perch", "retch", "wrote", "clock", "tilde", "store", "prove", "bring", "solve", "cheat", "grime", "exult", "usher", "epoch",
                "triad", "break", "rhino", "viral", "conic", "masse", "sonic", "vital", "trace", "using", "peach", "champ", "baton", "brake", "pluck", "craze", "gripe", "weary", "picky", "acute", "ferry", "aside", "tapir", "troll", "unify", "rebus", "boost",
                "truss", "siege"};

        //word to guess
        String word = words[random(words.length)];
        //user's guess
        String guess = "";
        int guesses = 0;

        rainbowWrite("Welcome to wordl©", true);
        System.out.println();
        System.out.println("You will be prompted to enter a 5 letter guess. You are trying to guess one target word.\nEach time you guess, information about your guess in relation to the target word will be given to you. Text color of word under category indicates hot or cold. \nGreen = Correct/Perfect Match to Parameter; Yellow = Close Match to Parameter; Gray = Incorrect/Not a Match to Parameter ");
        System.out.println();
        System.out.println("Press Enter to Continue...");
        s.nextLine();
        System.out.println("Important: Try to use what you have (what is green/orange), not what is absent. If a word is not showing up as a rhyme for example it could just not be in our database, \nand still be a rhyme :).");
        System.out.println("Ready? Press Enter.");
        s.nextLine();
        while (!guess.equals(word)) {
            guess = wordlInputValidation();
            if (guess.equals(word)) {
                patrioticWrite("Great Job! The word was " + word + ".", true);
            } else {
                System.out.println("Syllable Count || Part of Speech || Rhymes With || Sounds Like || Synonym || Antonym");
                wordlSyllableCheck(guess, word);
                wordlPoSCheck(guess, word);
                wordlRhymesCheck(guess, word);
                wordlSoundsLikeCheck(guess, word);
                wordlSynonymCheck(guess, word);
                wordlAntonymCheck(guess, word);
//            if(guesses%2 == 1)
                wordlHintGenerator(guess, word);
                System.out.println();
                //  System.out.println(word);
                guesses++;
                if (guesses == 8)
                    System.out.println("The word was " + word + "!");
            }
        }

        //one 5 letter word they are trying to guess
        //they input any word
        // __ different columns where they are rated how close they are based on color:
        // || pOs (either gray (incorrect) or green (correct) ) || sounds like (yellow, green, gray) || is a synonym (yellow, green, gray) || is an antonym || syllable count (gray or green) || close to a rhyme?


    }


    /**
     *
     * @return 
     */
    public static String wordlInputValidation() {
        String guess = "";
        while (guess.length() != 5) {
            System.out.println("Please enter a five letter guess...");
            guess = s.nextLine();
        }
        return guess;
    }


    public static void wordlSyllableCheck(String input, String wordToGuess) {
        //checks if syllable count is equal for guess and word to guess
//      debugging:  System.out.println("Sylablle of " + input + " " + data.hemidevisemiquaver(input));
//      debugging:  System.out.println("Syllable of " + wordToGuess + " " + data.hemidevisemiquaver(wordToGuess));
        if (data.hemidevisemiquaver(input) == data.hemidevisemiquaver(wordToGuess))
            //prints green text if the syllable count is equal
            System.out.print(ANSI_GREEN + input + " (" + data.hemidevisemiquaver(input) + ")     " + ANSI_RESET);
            //empty text otherwise (text remains gray)
        else
            System.out.print(input + "         ");
    }

    public static void wordlPoSCheck(String input, String wordToGuess) {
        ArrayList<String> wordToGuessPoS = data.getPartsofSpeech(wordToGuess);
        ArrayList<String> inputPoS = data.getPartsofSpeech(input);
        boolean correctPoS = false;
        if (wordToGuessPoS.size() == 0 || inputPoS.size() == 0)
            System.out.print(" || NA           ");
        else {
            for (int i = 0; i < inputPoS.size(); i++) {
                for (int j = 0; j < wordToGuessPoS.size(); j++) {
                    if (inputPoS.get(i).equals(wordToGuessPoS.get(j))) {
                        //if the PoS of the guess is the same PoS that the wordToGuess can be used as, then print the guess in green
                        if (wordToGuessPoS.get(j).equals("adj"))
                            System.out.print(ANSI_GREEN + " || " + input + "(" + wordToGuessPoS.get(j) + ")    " + ANSI_RESET);
                        else
                            System.out.print(ANSI_GREEN + " || " + input + "(" + wordToGuessPoS.get(j) + ")      " + ANSI_RESET);
                        correctPoS = true;
                        i = inputPoS.size();
                        j = wordToGuessPoS.size();
                    }
                }
            }
            //else print it in gray
            if (!correctPoS)
                System.out.print(" || " + input + "         ");
        }
    }

    public static void wordlSoundsLikeCheck(String input, String wordToGuess) {
        String[] SoundsLikeWordtoGuess = parser.parseWords(data.soundsSimilarforWordl(wordToGuess));
        boolean foundSoundsLike = false;
        int length = SoundsLikeWordtoGuess.length;
        for (int i = 0; i < length / 3; i++) {
            //highest indexes indicate better words based on parameter, so first 1/3 are printed as green (close match)
            if (SoundsLikeWordtoGuess[i].equals(input)) {
                System.out.print(ANSI_GREEN + " || " + input + "      " + ANSI_RESET);
                foundSoundsLike = true;
            }
        }
        for (int i = length / 3; i < length; i++) {
            //yellow for last 2/3s (closish match)
            if (SoundsLikeWordtoGuess[i].equals(input)) {
                System.out.print(ANSI_YELLOW + " || " + input + "      " + ANSI_RESET);
                foundSoundsLike = true;
            }
        }
        if (!foundSoundsLike)
            System.out.print(" || " + input + "      ");
    }

    public static void wordlSynonymCheck(String input, String wordToGuess) {
        String[] SynonymstoWordtoGuess = parser.parseWords(data.SynonymsforWordl(wordToGuess));
        boolean foundSynonym = false;
        int length = SynonymstoWordtoGuess.length;
        for (int i = 0; i < length / 2; i++) {
            //highest indexes indicate better words based on parameter, so first 1/3 are printed as green (close match)
            if (SynonymstoWordtoGuess[i].equals(input)) {
                System.out.print(ANSI_GREEN + " || " + input + "  " + ANSI_RESET);
                foundSynonym = true;
            }
        }
        for (int i = length / 2; i < length; i++) {
            //yellow for last 2/3s (closish match)
            if (SynonymstoWordtoGuess[i].equals(input)) {
                System.out.print(ANSI_YELLOW + " || " + input + "  " + ANSI_RESET);
                foundSynonym = true;
            }
        }
        if (!foundSynonym)
            System.out.print(" || " + input + "  ");
    }

    public static void wordlAntonymCheck(String input, String wordToGuess) {
        String[] AntonymstoWordtoGuess = parser.parseWords(data.AntonymsforWordl(wordToGuess));
        boolean foundAntonym = false;
        int length = AntonymstoWordtoGuess.length;
        for (int i = 0; i < length / 2; i++) {
            //highest indexes indicate better words based on parameter, so first 1/3 are printed as green (close match)
            if (AntonymstoWordtoGuess[i].equals(input)) {
                System.out.print(ANSI_GREEN + " || " + input + " " + ANSI_RESET);
                foundAntonym = true;
            }
        }
        for (int i = length / 2; i < length; i++) {
            //yellow for last 2/3s (closish match)
            if (AntonymstoWordtoGuess[i].equals(input)) {
                System.out.print(ANSI_YELLOW + " || " + input + " " + ANSI_RESET);
                foundAntonym = true;
            }
        }
        if (!foundAntonym)
            System.out.print(" || " + input + " ");
    }

    public static void wordlRhymesCheck(String input, String wordToGuess) {
        String[] RhymesWithWordtoGuess = parser.parseWords(data.rhymesWithforWordl(wordToGuess));
        boolean foundRhymesWith = false;
        int length = RhymesWithWordtoGuess.length;
        for (int i = 0; i < length / 2; i++) {
            //highest indexes indicate better words based on parameter, so first 1/3 are printed as green (close match)
            if (RhymesWithWordtoGuess[i].equals(input)) {
                System.out.print(ANSI_GREEN + " || " + input + "     " + ANSI_RESET);
                foundRhymesWith = true;
            }
        }
        for (int i = length / 2; i < length; i++) {
            //yellow for last 2/3s (closish match)
            if (RhymesWithWordtoGuess[i].equals(input)) {
                System.out.print(ANSI_YELLOW + " || " + input + "      " + ANSI_RESET);
                foundRhymesWith = true;
            }
        }
        if (!foundRhymesWith)
            System.out.print(" || " + input + "      ");
    }

    public static void wordlHintGenerator(String input, String wordToGuess) {
        //Hint for words that often precede the word to guess
        ArrayList<String> hints = parser.parseWords(data.rCforWordl(wordToGuess), true);
        //checks if the returned list is not empty
        int hintNum = random(4);
        if (hintNum == 0) {
            if (!(hints.get(0).equals("inválido"))) {
                System.out.print("  Hint: this word often follows the word " + hints.get(random(hints.size())) + ".");
            }
            else
                hintNum++;
        }
        boolean b = false;

        if (hintNum == 1) {
            //Hinto for adjectives that often describe the word to guess if it is a noun
            for (int i = 0; i < data.getPartsofSpeech(wordToGuess).size(); i++) {
                if (data.getPartsofSpeech(wordToGuess).get(i).equals("n"))
                    b = true;
            }
            if (b) {
                hints = parser.parseWords(data.rcAdj(wordToGuess), true);
                if (!(hints.get(0).equals("inválido"))) {
                    System.out.print("  Hint: this word is often modified by the adj " + hints.get(random(hints.size())) + ".");
                }
            } else
                hintNum++;
        }
        if (hintNum == 2 && goAgain) {
            //Hinto for adjectives that often describe the word to guess if it is a noun
            System.out.print("  Hint: this word starts with the letter " + wordToGuess.charAt(0) + ".");
            //boolean for if this particular hint has already been used
            goAgain = false;
        }
        if (hintNum == 3) {
            String[] hintys = parser.parseWords(data.rhymesWithforWordl(wordToGuess));
            if (!(hintys[0].equals("inválido"))) {
                System.out.println("  Hint: '" + hintys[random(hintys.length) - 1] + "' is a rhyme to the word.");
            }
            else
                hintNum++;
        }
        if (hintNum == 4) {
            String[] hintys = parser.parseWords(data.SynonymsforWordl(wordToGuess));
            if (!(hintys[0].equals("inválido"))) {
                System.out.println("  Hint: " + hintys[random(hintys.length) - 1] + " is a synonym to the word.");
            }
            else
                System.out.println("  Hint: The word ends with " + wordToGuess.charAt(wordToGuess.length()-1));
        }


    }


    public static void alliterationGenerator() throws JSONException {
        Scanner s = new Scanner(System.in);
        JSONParse parser = new JSONParse();
        DataMuseQueryWithParser data = new DataMuseQueryWithParser();

        String noun;
        rainbowWrite("Welcome to alliteration generator", true);

        noun = inputValidation(2);


        //second and third adjectives in sentence that must be initialized
        String adjective1;
        String adjective2;
        String verb2;
        //gets a random verb that start with the same letter of the noun
        String verb = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "v", true));
        //random adjective that starts with same letter as the noun
        String adjective = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "adj", true));
        //second random verb that starts with same letter with validation that it is not the same verb as the previous one
        verb2 = verb;
        while (verb2.equals(verb)) {
            verb2 = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "v", true));
        }
        //second and third random adjective with validation loop so that it is not the same as the other one
        adjective1 = adjective;
        while (adjective.equals(adjective1)) {
            adjective1 = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "adj", true));
        }
        adjective2 = adjective;
        while (adjective2.equals(adjective1) || adjective2.equals(adjective)) {
            adjective2 = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "adj", true));
        }

        System.out.println(adjective + " " + noun + " " + adjective1 + " " + verb + " " + adjective2 + " " + verb2);
    }


    public static void haiku() throws JSONException {
        Scanner s = new Scanner(System.in);
        JSONParse parser = new JSONParse();
        DataMuseQueryWithParser data = new DataMuseQueryWithParser();
        String noun2 = "";
        String noun1 = "";
        String noun = "";
        boolean sylabbleCorrect = false;
        rainbowWrite("Welcome To Haiku!", true);

        //calls method which takes input validation for starting nouns
            noun = inputValidation(1);
            noun1 = inputValidation(2);
            noun2 = inputValidation(2);

        //one syllablle adj that often modifies noun
        String oneSyllableAdj = getRandom(parser.parseSyllables(data.rcAdj(noun), 1));

        oneSyllableAdj = checkEmpty(oneSyllableAdj, noun, false, 1);

        //two syllable verb that often is asscoiated with the noun
        String verbsylabble2 = getRandom(parser.parseSyllables(data.wordsRelatedtoinput(noun), 2));
        verbsylabble2 = checkEmpty(verbsylabble2, noun, true, 2);

        //1 sylabble verbs that are often related to the second noun
        String verbsylabble32 = getRandom(parser.parseSyllables(data.wordsRelatedtoinput(noun1), 1));
        verbsylabble32 = checkEmpty(verbsylabble32, noun1, true, 1);

        //2 sylabble adj related to the second noun
        String adj1 = getRandom(parser.parseSyllables(data.rcAdj(noun1), 2));
        adj1 = checkEmpty(adj1, noun1, false, 2);

        String oneSyllableAdj1 = getRandom(parser.parseSyllables(data.rcAdj(noun2), 1));
        oneSyllableAdj1 = checkEmpty(oneSyllableAdj1, noun2, false, 1);

        String verbsylabble23 = getRandom(parser.parseSyllables(data.wordsRelatedtoinput(noun2), 1));
        verbsylabble23 = checkEmpty(verbsylabble23, noun2, true, 1);

        //Printing of the final haiku
        System.out.println("A " + oneSyllableAdj + " " + noun + " " + verbsylabble2);
        System.out.println("The " + adj1 + " " + noun1 + " is " + verbsylabble32);
        System.out.println("Yet " + oneSyllableAdj1 + " " + noun2 + " " + verbsylabble23);
    }

    /**
     * This method validates that the user input is the correct part of speech
     *
     * @param pos            string of the part of speech for the word
     * @param posCheckingfor pos that we are checking for that the word can be used as
     * @return boolean whether pos includes posCheckingfor
     */
    public static boolean checkIfPartofSpeech(ArrayList<String> pos, String posCheckingfor) {
        for (int i = 0; i < pos.size(); i++) {
            if (pos.get(i).equals(posCheckingfor))
                return true;
        }
        return false;
    }

    /**
     * Method that checks if there are no verbs/adjectives that are the correct number of sylabbles and can be found as associated with the noun
     *
     *
     * @param noun
     * @return
     */
    public static String checkEmpty(String input, String noun, boolean q, int numSylabbles) {
        boolean b = true;
        String needed = "";
        String neededinShorterTerms = "";
        String wording = "";
        //if q is true we are checking for empty sets on the verb getter, else for adjectives, wording is just to fix the wording based on if it is an adj or n
        if (q) {
            needed = "verb";
            wording = "be associated with";
            neededinShorterTerms = "v";
        } else {
            needed = "adjective";
            wording = "modify";
            neededinShorterTerms = "adj";
        }


        while (b) {
            if (input.equals("inválido")) {
                System.out.println("Our system does not have any " + needed + "s that would often " + wording + " " + noun + "! Enter another " + numSylabbles + " syllable " + needed + " that would often " + wording + " " + noun + ":");
                input = s.nextLine();
                while (data.hemidevisemiquaver(input) != numSylabbles || !(checkIfPartofSpeech(data.getPartsofSpeech(input), neededinShorterTerms))) {
                    System.out.println("This is not a " + numSylabbles + " syllable " + needed + ". Give us a word that matches the parameters:");
                    input = s.nextLine();
                }
            } else {
                b = false;
            }
        }
        return input;
    }

    /**
     * input validation for the noun inputs, that it is the correct number of sylabbles and part of speech
     * @param numSylablles
     * @return
     */

    public static String inputValidation (int numSylablles) {
        boolean validWord = true;
        String noun = "";
        while (validWord) {
            System.out.println("Give us a " + numSylablles + " syllable noun.");
            noun = s.nextLine();
            if (data.hemidevisemiquaver(noun) != numSylablles || !(checkIfPartofSpeech(data.getPartsofSpeech(noun), "n")) || noun.length() == 1) {
                System.out.println("This is not a " + numSylablles + " syllable noun");
            } else {
                validWord = false;
            }
        }
        return noun;
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
    public static String getListRandom(String [] array){
        return array[random(array.length-1)];
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
