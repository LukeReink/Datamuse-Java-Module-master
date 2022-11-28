package com.module.datamuse;
import org.json.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Datamuse {

    //TODO: validate that robot sentence input is not just a empty space bar


    //object that has various methods which 'parse' (extract) just the words from an API call
    //different methods have different parameters that can specify what kinds of words the system wants returned (syllable counts, pos indications, etc).
    static JSONParse parser = new JSONParse();
    //Object that has different methods used to ask for different calls to the API based on a word input, returns large JSON string or syllable/part of speech information
    static DataMuseQueryWithParser data = new DataMuseQueryWithParser();
    //scanner
    static Scanner s = new Scanner(System.in);
    //boolean used in wordl hint generator that has to be static because it should not reset to a cetain value over multiple method calls
    static boolean goAgain = true;
    //boolean used in wordl hint generator that has to be static because it should not reset to a cetain value over multiple method calls
    static boolean goAgainRhyme = true;

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
        boolean customerSatisfied = false;

        while (!customerSatisfied) {
            rhetoricalDeviceGenerator();
            //validate that the customer is satisfied
            System.out.println("Satisfied? Type anything. If you are not satisfied with these results type: " + YELLOW_BRIGHT + "'1'" + ANSI_RESET + " to try again...");
            try {
                //if they do not enter 1, move on, else stay in loop
                if (!(s.nextInt() == 1))
                    customerSatisfied = true;
            } catch (Exception e) {
                customerSatisfied = true;
            }
        }
        customerSatisfied = false;

        //format the system output so it is user friendly
        //call all 'activities' or uses of the API that we thought of and implemented as methods

        System.out.println("Welcome to our system! You will participate in 7 activities. First: Haiku generator! Ready? (press enter)");
        s.nextLine();
        haiku();
        System.out.println();
        System.out.println("Next: Alliteration Alternator! Ready? (press enter)");
        s.nextLine();
        alliterationAlternator();
        System.out.println();
        System.out.println(ANSI_BLACK+  "Next: Wicked Wordl! Ready? (you know what to do)" + ANSI_RESET);
        s.nextLine();
        wordl();
        System.out.println();
        System.out.println("Next: Robot Sentence! Ready? (you guessed it, press E to the NTE to the R)");
        s.nextLine();
        robotSentence();
        System.out.println();
        System.out.println("Next: Alliteration Sentence. This one might take a while... (ent)");
        alliterationSentence();
        System.out.println();
        rainbowWrite("Finally: Rhetorical Device Gen a ratar!!! (press e to the r)", true);
        while (!customerSatisfied) {
            rhetoricalDeviceGenerator();
            //validate that the customer is satisfied
            System.out.println("Satisfied? Type anything. If you are not satisfied with these results type: " + YELLOW_BRIGHT + "'1'" + ANSI_RESET + " to try again...");
            try {
                //if they do not enter 1, move on, else stay in loop
                if (!(s.nextInt() == 1))
                    customerSatisfied = true;
            } catch (Exception e) {
                customerSatisfied = true;
            }
        }
        System.out.println();
        System.out.println("WOW! That was a movie!");

        rainbowWrite("Cya!", true);
    }

    /**
     * takes 3 nouns from the user, validating that they are certain syllable counts, and fills in words that try to fit sentence structure and do fit the syllable structure
     * of a haiku poem
     *
     */
    public static void haiku() {
        DataMuseQueryWithParser data = new DataMuseQueryWithParser();
        String noun2 = "";
        String noun1 = "";
        String noun = "";
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
        //method explained below
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

        //put noun + back to ' ' if they input two words for a noun
        String finalNoun = noun.replaceAll(" ", "+");
        String finalNoun1 = noun1.replaceAll(" ", "+");
        String finalNoun2 = noun2.replaceAll(" ", "+");

        //Printing of the final haiku
        System.out.println("A " + oneSyllableAdj + " " + finalNoun + " " + verbsylabble2);
        System.out.println("The " + adj1 + " " + finalNoun1 + " is " + verbsylabble32);
        System.out.println("Yet " + oneSyllableAdj1 + " " + finalNoun2 + " " + verbsylabble23);
    }

    /**
     * Method that checks if there are no verbs/adjectives that are the correct number of sylabbles and can be found as associated with the noun that returned by the database
     * and either returns a word that fits the parameters or prints out the null statement, asking our user to input their own word that the API could not generate
     *
     * @param numSylabbles the number of syllables they want the new word to be
     * @param noun         their input
     * @param q boolean that indicates whether the system is asking for a verb associated with input 'noun' or an adjective that would modify 'noun'
     * @return
     */
    public static String checkEmpty(String input, String noun, boolean q, int numSylabbles) {
        boolean b = true;
        String needed = "";
        String neededinShorterTerms = "";
        String wording = "";
        //if q is true we are checking for empty sets on the verb getter, else for adjectives
        // wording is just to fix the wording based on if it is an adj or n that we are checking for
        if (q) {
            needed = "verb";
            wording = "be associated with";
            neededinShorterTerms = "v";
        } else {
            needed = "adjective";
            wording = "modify";
            neededinShorterTerms = "adj";
        }

        //checks if the parameter gave an empty set
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
     * input validation for the noun user input: checking that it is the correct number of sylabbles and part of speech that the system wants (which is always noun)
     *
     * @param numSyllables number of Syllables system wants the noun to be
     * @return a valid input
     */
    public static String inputValidation(int numSyllables) {
        boolean invalidWord = true;
        //the variable we use for the input
        String noun = "";
        while (invalidWord) {
            //0 numSylabbles is our key word for ambiguous syllable count
            if (numSyllables == 0)
                System.out.println("Give us a noun.");
            else
                System.out.println("Give us a " + numSyllables + " syllable noun.");
            noun = s.nextLine();
            //checks if they put a space in their input, and replaces with a '+' so that our API can work best
            for (int i = 0; i < noun.length(); i++) {
                if (noun.charAt(i) == ' ') {
                    noun = noun.substring(0, i) + "+" + noun.substring(i + 1, noun.length());
                }
            }
            //if the input is not the right number of sylabbles or part of speech, or just a one letter word, asdk for a different input
            try {
                //run syllable check only if the key # 0 was not inputted
                if (numSyllables != 0) {
                    if (data.hemidevisemiquaver(noun) != numSyllables || !(checkIfPartofSpeech(data.getPartsofSpeech(noun), "n")) || noun.length() == 1) {
                        System.out.println("According to the API, this is not a " + numSyllables + " syllable noun");
                    } else {
                        //if word is valid end the loop
                        invalidWord = false;
                    }
                } else {
                    if (!(checkIfPartofSpeech(data.getPartsofSpeech(noun), "n")) || noun.length() == 1) {
                        System.out.println("According to the API, this is not a noun");
                    } else {
                        //if word is valid end the loop
                        invalidWord = false;
                    }
                }
            } catch (Exception e) {
                System.out.println("According to the API, this is not a " + numSyllables + " syllable noun");
            }
        }
        return noun.toLowerCase();
    }

    /**
     * This method validates that the user input is the correct part of speech
     *
     * @param pos            string ArrayList of all the part of speeches that the word can be used as
     * @param posCheckingfor pos that we are checking for that the word should be
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
     * One 5 letter word they are trying to guess
     * They input any word
     * Printed: Different columns where the word they guessed is rated to how close they are to the word they are trying to guess based on color:
     * Ex Printed: || pOs (either gray (incorrect) or green (correct) ) || sounds like (yellow, green, gray) || is a synonym (yellow, green, gray) || is an antonym || syllable count (gray or green) || close to a rhyme?
     */

    public static void wordl() throws JSONException {
        //TODO: Remove synonyms that have the actual word in them for hints
        //TODO: Remove the hint rhymes with if that word has already been used!
        //list of words to guess
        DataMuseQueryWithParser dMuse = new DataMuseQueryWithParser();
        JSONParse parse = new JSONParse();
        Scanner s = new Scanner(System.in);
        //list of words that we pull from for the word to guess
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
        boolean gOver = false;
        //information surrounding game
        rainbowWrite("Welcome to wordl©", true);
        System.out.println();
        System.out.println("You will be prompted to enter a 5 letter guess. You are trying to guess one target word.\nEach time you guess, information about your guess in relation to the target word will be given to you. Text color of word under category indicates hot or cold. \nGreen = Correct/Perfect Match to Parameter; Yellow = Close Match to Parameter; Gray = Incorrect/Not a Match to Parameter ");
        System.out.println();
        System.out.println("Press Enter to Continue...");
        s.nextLine();
        System.out.println("Important: Try to use what you have (what is green/orange), not what is absent. If a word is not showing up as a rhyme for example it could just not be in our database, \nand still be a rhyme :).");
        System.out.println("Ready? Press Enter.");
        s.nextLine();
        //boolean gOver to see if the game is over
        while (!gOver) {
            //input validation for the 5 letter guess
            guess = wordlInputValidation();
            //checks if they have guessed the word
            if (guess.equals(word)) {
                System.out.println("Great Job! The word was " + word + ".");
                gOver = true;
                //else give them hints as to how close they are to the word
            } else {
                System.out.println("Syllable Count || Part of Speech || Rhymes With || Sounds Like || Synonym || Antonym");
                //hint about if they have the correct # of syllables
                wordlSyllableCheck(guess, word);
                //hint if their guess has a correct poS. Multiple poS could show up for the wordToGuess because it could be used two ways
                wordlPoSCheck(guess, word);
                //hint if their word rhymes with word to guess
                wordlRhymesCheck(guess, word);
                //hint if the word sounds like the word to guess
                wordlSoundsLikeCheck(guess, word);
                //hint if the word is a synonyms to the word to guess
                wordlSynonymCheck(guess, word);
                wordlAntonymCheck(guess, word);
//            if(guesses%2 == 1)
                wordlHintGenerator(guess, word);
                System.out.println();
                //  System.out.println(word);
                guesses++;
                if (guesses == 8) {
                    System.out.println("The word was " + word + "!");
                    gOver = true;
                }

            }
        }
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
        //boolean for if input has spaces
        boolean hasSpaces = false;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == ' ') {
                hasSpaces = true;
                //set guess to a non 5 character string so that it can be validated for length again as well
                guess = "nope";
            }
        }
        if (hasSpaces) {
            while (guess.length() != 5) {
                System.out.println("Please enter a guess with no spaces...");
                guess = s.nextLine();
            }
        }
        return guess.toLowerCase();
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
        //all possible part of speech of the word to guess
        ArrayList<String> wordToGuessPoS = data.getPartsofSpeech(wordToGuess);
        //all possible part of speech for the input
        ArrayList<String> inputPoS = data.getPartsofSpeech(input);
        //boolean to check if the input pos = word to guess pos
        boolean correctPoS = false;
        //print an invalid statement if either of the words does not return anything for getPartsofSpeech();
        if (wordToGuessPoS.size() == 0 || inputPoS.size() == 0)
            System.out.print(" || NA           ");
        else {
            for (int i = 0; i < inputPoS.size(); i++) {
                for (int j = 0; j < wordToGuessPoS.size(); j++) {
                    //if the PoS of the guess is the same PoS that the wordToGuess can be used as, then print the guess in green, adjusting for spacing in adj vs. n/v
                    if (inputPoS.get(i).equals(wordToGuessPoS.get(j))) {
                        if (wordToGuessPoS.get(j).equals("adj"))
                            System.out.print(ANSI_GREEN + " || " + input + "(" + wordToGuessPoS.get(j) + ")    " + ANSI_RESET);
                        else
                            System.out.print(ANSI_GREEN + " || " + input + "(" + wordToGuessPoS.get(j) + ")      " + ANSI_RESET);
                        //boolean that checks if something was returned or found
                        correctPoS = true;
                        //end loop; stop checking for more matches because it will create an uneven offset if you show more matches
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


    /**
     * gives a hint to the system greater than what they have about the word they are trying guess
     *
     * @param input       word they guessed
     * @param wordToGuess word they are trying to guess
     */
    public static void wordlHintGenerator(String input, String wordToGuess) {
        //Hint for words that often precede the word to guess
        ArrayList<String> hints = parser.parseWords(data.rCforWordl(wordToGuess), true);
        //checks if the returned list is not empty
        int hintNum = random(4);
        //TODO: When hintnum is 4 the hint sometimes just randomly disappears, mysterious
        //TODO: debug : System.out.println("Hintnum: " + hintNum);
        if (hintNum == 0) {
            //checks if the rC method returned zero data
            if (!(hints.get(0).equals("inválido"))) {
                System.out.print("  Hint: this word often follows the word " + hints.get(random(hints.size())) + ".");
            }
            else
                hintNum++;
        }
        boolean b = false;

        //

        if (hintNum == 1) {
            //Hinto for adjectives that often describe the word to guess if it is a noun
            for (int i = 0; i < data.getPartsofSpeech(wordToGuess).size(); i++) {
                if (data.getPartsofSpeech(wordToGuess).get(i).equals("n"))
                    b = true;
            }
            //if b then it is a noun, so it can apply to "rcAdj" which returns words that modify input (noun)
            if (b) {
                //set hints to list of adj that modify it
                hints = parser.parseWords(data.rcAdj(wordToGuess), true);
                if (!(hints.get(0).equals("inválido"))) {
                    System.out.print("  Hint: this word is often modified by the adj " + hints.get(random(hints.size())) + ".");
                }
            } else
                hintNum++;
        }
        if (hintNum == 2) {
            if (goAgain) {
                //Hint for adjectives that often describe the word to guess if it is a noun
                System.out.print("  Hint: this word starts with the letter " + wordToGuess.charAt(0) + ".");
                //boolean for if this particular hint has already been used, so that this hint only happens once
                goAgain = false;
            } else {
                //if this hint has already happened set hint num to hint # 4
                hintNum = 4;
            }
        }
        //hint for ryhmes, also checks for empty set
        if (hintNum == 3 && goAgainRhyme) {
            String[] hintys = parser.parseWords(data.rhymesWithforWordl(wordToGuess));
            if (!(hintys[0].equals("inválido"))) {
                System.out.println("  Hint: '" + hintys[random(hintys.length) - 1] + "' is a rhyme to the word.");
            }
            else
                hintNum++;
        } else
            hintNum++;
        //hint that indicates a synonyms of the word to guess
        if (hintNum == 4) {
            String[] hintys = parser.parseWords(data.SynonymsforWordl(wordToGuess));
            if (!(hintys[0].equals("inválido"))) {
                System.out.println("  Hint: " + hintys[random(hintys.length) - 1] + " is a synonym to the word.");
            }
            else
                System.out.println("  Hint: The word ends with " + wordToGuess.charAt(wordToGuess.length()-1));
        }

    }

    /**
     * Gives different kinds of rehtorical device examples based on one input noun
     * a lot of the code is validation that every word needed is generated: else the code asks for a new noun input
     */

    public static void rhetoricalDeviceGenerator() {
        rainbowWrite("Welcome to simile/metaphor generator!", true);
        //noun that is the topic of the sentence
        String topicNoun = inputValidation(0);
        System.out.println("Generating...");
        System.out.println();
        String compareTo = "";
        String adjective = "";
        //personification verb
        String personificationVerb = "";
        String nounforcompareTo = "";
        String verb = "";
        String otherNoun = " ";
        //String to fix wording for personification
        String personificationWording = "Anti-Personification: ";
        //booleans that confirm each of our words has a valid value, initialization to prevent errors
        boolean compareToValid = false;
        boolean adjectiveValid = false;
        boolean verbValid = false;
        boolean nounComparetoValid = false;
        boolean continueLoop = true;
        boolean noWordsValid = true;
        boolean otherNounValid = false;
        boolean personificationVerbValid = false;
        // need to work on validation  while(compareTo.equals("") || adjective.equals("") || verb.equals("") || verbforcompareTo.equals("")) {
        //noun to compare the noun to in a simile
        while (continueLoop) {
            //various booleans to check if these certain words are valid, use each to unlock the next step, else just go back to top of the loop
            //must reset at the top of the loop so that code goes back to round one.
            compareToValid = false;
            adjectiveValid = false;
            verbValid = false;
            nounComparetoValid = false;
            noWordsValid = true;
            otherNounValid = false;
            personificationVerbValid = false;
            //large boolean used to check if we should continue to ask for replacement nouns because there was a null value returned for one of the words we are
            //trying to generate

            //first word generated is a noun to compare the topic noun to in a sentence
            while (noWordsValid) {
                try {
                    //do not want more than one word returned here
                    while (otherNoun.contains(" "))
                        otherNoun = getListRandom((parser.parseWords(data.meansLikeforRhetoric(topicNoun))));
                    //if the word is our invalid key word, still throw an error so it still runs the catch statement
                    if (compareTo.equals("inválido")) {
                        System.out.println(5 / 0);
                    } else {
                        //start next loop
                        otherNounValid = true;
                        //end current loop
                        noWordsValid = false;
                    }
                } catch (Exception e) {
                    //print the kind of error
                    System.out.println("The API could not find any synonyms to compare " + topicNoun + " to. Try a different noun.");
                    System.out.println();
                    topicNoun = inputValidation(0);
                    System.out.println();
                    //get new topic noun, end current loop, go back to top
                    noWordsValid = false;
                }
            }
            //this word we generate is a synonym to the topic noun, which is compared to the topic noun which is then compared to another noun
            //like so: the 'topicnoun' is as 'compareTo' as a 'nounforCompareto'
            while (otherNounValid) {
                try {
                    //make sure that there are more than one words to draw from as synonyms to topic noun, then set the compareTo to one so that it is not equal to otherNoun
                    if (parser.parseWords(data.SynonymsforWordl(topicNoun)).length > 1) {
                        compareTo = otherNoun;
                        while (compareTo.equals(otherNoun))
                            compareTo = getListRandom((parser.parseWords(data.SynonymsforWordl(topicNoun))));
                    } else {
                        compareTo = otherNoun;
                        while (compareTo.equals(otherNoun)) {
                            compareTo = getListRandom((parser.parseWords(data.meansLikeforRhetoric(topicNoun))));
                        }
                    }
                    //if the word is our invalid key word, still throw an error so it still runs the catch statement
                    if (compareTo.equals("inválido")) {
                        System.out.println(5 / 0);
                    } else {
                        //start next loop
                        compareToValid = true;
                        //end current loop
                        otherNounValid = false;
                    }
                } catch (Exception e) {
                    //print the kind of error - same as above
                    System.out.println("The API could not find any synonyms to compare " + topicNoun + " to. Try a different noun.");
                    System.out.println();
                    topicNoun = inputValidation(0);
                    System.out.println();
                    //get new topic noun, end current loop, go back to top
                    otherNounValid = false;
                }
            }
            while (compareToValid) {
                //adjective that often modifies topicNoun
                try {
                    adjective = getListRandom(parser.parseWords(data.rcAdjforRhetoric(topicNoun)));
                    //if the word is our invalid key word, still throw an error so it still runs the catch statement
                    if (adjective.equals("inválido")) {
                        System.out.println(5 / 0);
                    } else {
                        //syntax improvement, you cant say something is as 'blank' as something else if blank = adjective ending in er ex: "the place is as deeper as a water"
                        if (adjective.endsWith("er"))
                            adjective = adjective.substring(0, adjective.length() - 2);
                        //enter next loop
                        adjectiveValid = true;
                        //end current loop
                        compareToValid = false;
                    }
                } catch (Exception e) {
                    //print the kind of error
                    System.out.println("The API could not find any adjectives that modify " + topicNoun + ". Try a different noun");
                    System.out.println();
                    topicNoun = inputValidation(0);
                    System.out.println();
                    //end current loop
                    compareToValid = false;
                }
            }
            while (adjectiveValid) {
                //verb related to topicnoun
                try {
                    verb = getRandom(parser.parsePartsofSpeech(data.wordsRelatedtoinput(topicNoun), "v", true));
                    //if the word is our invalid key word, still throw an error so it still runs the catch statement
                    if (verb.equals("inválido")) {
                        System.out.println(5 / 0);
                    } else {
                        //start next loop;
                        verbValid = true;
                        //end loop
                        adjectiveValid = false;
                    }
                } catch (Exception e) {
                    //catch errors, ask for replacement while telling the kind of error.
                    System.out.println("The API could not find any verbs related to " + topicNoun + ". Try a different noun");
                    System.out.println();
                    topicNoun = inputValidation(0);
                    System.out.println();
                    //end loop
                    adjectiveValid = false;
                }
            }
            int tryCount = 0;
            while (verbValid) {
                //program for getting noun that often is related to compareTo
                //if there are no nouns related to the compareTo word, then try to get a related noun from a different
                try {
                    //index to draw the new "compareTo" word from, so that we do not repeat words
                    int drawFromIndex = 0;
                    //Code to replace the word we are trying to find a related noun to compare with another word it could also be if the noun returns no nouns related to it
                    while (tryCount < 5) {
                        //only use the og one if this is your first rodeo
                        if (tryCount == 0)
                            nounforcompareTo = getRandom(parser.parsePartsofSpeech(data.wordsRelatedtoinput(compareTo), "n", true));
                        //if the word returns our invalid key word, is an ing noun, or ending in s (plural) noun, still throw an error so it runs the catch statement
                        if (nounforcompareTo.equals("inválido") || /*can't use .contain because only want words that end with ing */ nounforcompareTo.endsWith("ing") || nounforcompareTo.charAt(nounforcompareTo.length() - 1) == 's' || nounforcompareTo.equals(topicNoun)) {
                            tryCount++;
                            //List of words like compare to, including compare to at an index
                            String[] compareToalikes = parser.parseWords(data.SynonymsforWordl(topicNoun));
                            //only do this if the list we drew compareTo from has more options for what compare to could have been
                            if (compareToalikes.length > 1) {
                                //debugging:  System.out.println("Trying again");
                                String tempCompareTo = compareTo;
                                //make sure it does not equal the previous word we were trying to find nouns for
                                while (tempCompareTo.equals(compareTo) && drawFromIndex < compareToalikes.length) {
                                    //set a new variable with a new option that is not compare to from the same list compare to was drawn from
                                    tempCompareTo = ((parser.parseWords(data.SynonymsforWordl(topicNoun)))[drawFromIndex]);
                                    //add to the index that the new compare to noun was drawn from
                                    drawFromIndex++;
                                }
                                //debugging:  System.out.println("Comparing with " + tempCompareTo);
                                //System.out.println();
                                nounforcompareTo = getRandom(parser.parsePartsofSpeech(data.wordsRelatedtoinput(tempCompareTo), "n", true));
                            }
                        } else {
                            //start next loop
                            nounComparetoValid = true;
                            //exit loop for getting word
                            tryCount = 5;
                            //end bigger loop
                            verbValid = false;
                        }
                    }
                    //end next loop if we have left the while loop and found no nouns that are associate with compareTo or words like compareTo
                    verbValid = false;
                } catch (Exception e) {
                    System.out.println("The API could not find any nouns related to a word that is related to " + topicNoun + ". Try a different noun.");
                    System.out.println();
                    topicNoun = inputValidation(0);
                    System.out.println();
                    //end current loop
                    verbValid = false;
                }
            }
            // DOES NOT USE API get a verb unrelated to the word, so that it makes personification/wierdonification if their input is a person, will never return an error because it is hard coded
            while (nounComparetoValid) {
                //int for their answer
                int answer = 0;
                //boolean for human noun or not
                boolean topicNounisHuman = false;
                //boolean for validation
                boolean answerIsValid = false;
                //get whether their verb is human or not
                while (!answerIsValid) {
                    System.out.println("Type " + YELLOW_BRIGHT + "'1'" + ANSI_RESET + " if your noun is a human. Type anything otherwise.");
                    try {
                        topicNounisHuman = s.nextInt() == 1;
                        s.nextLine();
                        answerIsValid = true;
                    } catch (Exception e) {
                        answerIsValid = true;
                    }
                }
                //Stahl database of human verbs for personification
                String[] humanVerbs = {"laughed", "lifted weights", "coded", "high jumped", "screamed", "lectured", "served"};
                //database of nonhuman verbs for non-personification
                String[] nonhumanVerbs = {"growled", "barked", "galloped", "slithered"};
                if (topicNounisHuman) {
                    personificationVerb = getListRandom(nonhumanVerbs);
                    //wording for when we print out the sentence
                    personificationWording = "Personification: ";
                } else {
                    personificationVerb = getListRandom(humanVerbs);
                    //wording for when we print out the sentence
                    personificationWording = "Personification: ";
                }
                //start next loop
                personificationVerbValid = true;
                //end current loop
                nounComparetoValid = false;
            }
            if (personificationVerbValid) {
                //end loop because we got to the final word
                continueLoop = false;

                //syntax correction so that our use of a and an can match singular noun use, causes bad grammer for some singular words that end in s (measles, molasses, physics)
                if(otherNoun.endsWith("s"))
                    otherNoun = otherNoun.substring(0,otherNoun.length()-1);
                if(compareTo.endsWith("s"))
                    compareTo = compareTo.substring(0,compareTo.length()-1);
                if(nounforcompareTo.endsWith("s"))
                    nounforcompareTo = nounforcompareTo.substring(0,nounforcompareTo.length()-1);


                //string for whether word following starts with vowel so we can improve syntax for a vs. an
                //does not work for every word - ex: hour, urine, etc.
                String startWithVowel = "a";
                String startWithVowelforNoun = "a";
                String startWithVowelforCompareTo = "a";
                if (otherNoun.charAt(0) == 'a' || otherNoun.charAt(0) == 'o' || otherNoun.charAt(0) == 'e' || otherNoun.charAt(0) == 'u' || otherNoun.charAt(0) == 'i')
                    startWithVowel = "an";
                if (nounforcompareTo.charAt(0) == 'a' || nounforcompareTo.charAt(0) == 'o' || nounforcompareTo.charAt(0) == 'e' || nounforcompareTo.charAt(0) == 'u' || nounforcompareTo.charAt(0) == 'i')
                    startWithVowelforNoun = "an";
                if (compareTo.charAt(0) == 'a' || compareTo.charAt(0) == 'o' || compareTo.charAt(0) == 'e' || compareTo.charAt(0) == 'u' || compareTo.charAt(0) == 'i')
                    startWithVowelforCompareTo = "an";
                System.out.println();
                //more syntax correction: checking whether the grammar structure should try to fit an action verb in 'ing' (noun singing is like, noun flying is like
                // or should it just try to fit endings like in s (noun flings like, noun sings like, etc.) or 'ed' (noun originated like, noun hated like, etc.) or maybe: t (noun fought like, noun wept like) ** right now it is not included because "noun will select like, noun will fight like"
                //or is it active and need a conjunction verb to make sense (final else statement)
                if (verb.endsWith("ing"))
                    System.out.println("Simile: The " + topicNoun + " " + verb + " is like " + startWithVowel + " " + otherNoun + ".");
                else if (verb.endsWith("s") || verb.endsWith("ed") || /*verbs that do not follow these rules I noticed */ verb.equals("won"))
                    System.out.println("Simile: The " + topicNoun + " " + verb + " like " + startWithVowel + " " + otherNoun + ".");
                else
                    System.out.println("Simile: The " + topicNoun + " will " + verb + " like " + startWithVowel + " " + otherNoun + ".");

                System.out.println("-------");
                //more syntax correction
                if(adjective.endsWith("ing"))
                    System.out.println("Simile: The " + topicNoun + " is " + adjective + " as " + startWithVowelforCompareTo + " " + compareTo + " does.");
                else
                    System.out.println("Simile: The " + topicNoun + " is as " + adjective + " as " + startWithVowelforCompareTo + " " + compareTo + ".");
                System.out.println("-------");
                System.out.println("Metaphor: The " + topicNoun + " is " + startWithVowelforNoun + " " + nounforcompareTo + ".");
                System.out.println("-------");
                System.out.println(personificationWording + "The " + topicNoun + " " + personificationVerb + ".");
                System.out.println();
            }
        }

    }

    public static void alliterationAlternator() throws JSONException {
        Scanner s = new Scanner(System.in);
        JSONParse parser = new JSONParse();
        DataMuseQueryWithParser data = new DataMuseQueryWithParser();

        String noun;
        rainbowWrite("Welcome to alliteration generator", true);

        noun = inputValidation(0);

        //must initialize because of initialization inside try catch statements
        String adjective1 = "";
        String adjective2 = "";
        String verb2 = "";
        String adjective = "";
        String verb = "";
        String noun1 = "";
        String noun2 = "";
        String adv = "";
        //boolean to see if all words are generated without error
        boolean keepGoing = true;
        //boolean to see if we should continue to find words
        boolean endNotReached = true;
        //try catch for all words taken from system, in case the system returns null
        //gets a random verb that start with the same letter of the noun
        while (endNotReached) {
            keepGoing = true;
            try {
                //active verbs that end with s
                verb = getRandom(parser.parsePartsofSpeech(data.wordsStartingWithEndingWith(noun.substring(0, 1), "s"), "v", true));
            } catch (Exception e) {
                System.out.println("The noun you gave us returns null for a certain access method of the API.");
                noun = inputValidation(0);
                keepGoing = false;
            }
            if (keepGoing) {
                try {
                    //active adverbs end with y
                    adv = getRandom(parser.parsePartsofSpeech(data.wordsStartingWithEndingWith(noun.substring(0, 1), "y"), "adv", true));
                } catch (Exception e) {
                    //nothing, we do no neccesarily NEED an adverb
                }
            }
            if (keepGoing) {
                try {
                    noun1 = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "n", true));
                } catch (Exception e) {
                    System.out.println("The noun you gave us returns null for a certain access method of the API");
                    noun = inputValidation(0);
                    keepGoing = false;
                }
            }
            if (keepGoing) {
                //random adjective that starts with same letter as the noun
                try {
                    adjective = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "adj", true));
                } catch (Exception e) {
                    System.out.println("The noun you gave us returns null for a certain access method of the API");
                    noun = inputValidation(0);
                    keepGoing = false;
                }
            }
            if (keepGoing) {
                //second random verb that starts with same letter with validation that it is not the same verb as the previous one
                verb2 = verb;
                try {
                    while (verb2.equals(verb)) {
                        verb2 = getRandom(parser.parsePartsofSpeech(data.wordsStartingWithEndingWith(noun.substring(0, 1), "s"), "v", true));
                    }
                } catch (Exception e) {
                    System.out.println("The noun you gave us returns null for a certain access method of the API");
                    noun = inputValidation(0);
                    keepGoing = false;
                }
            }
            if (keepGoing) {
                //second random verb that starts with same letter with validation that it is not the same verb as the previous one
                noun2 = noun1;
                try {
                    while (noun2.equals(noun1)) {
                        noun2 = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "n", true));
                    }
                } catch (Exception e) {
                    System.out.println("The noun you gave us returns null for a certain access method of the API");
                    noun = inputValidation(0);
                    keepGoing = false;
                }
            }
            if (keepGoing) {
                //second and third random adjective with validation loop so that it is not the same as the other one
                adjective1 = adjective;
                try {
                    while (adjective.equals(adjective1)) {
                        adjective1 = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "adj", true));
                    }
                } catch (Exception e) {
                    System.out.println("The noun you gave us returns null for a certain access method of the API");
                    noun = inputValidation(0);
                    keepGoing = false;
                }
            }
            if (keepGoing) {
                adjective2 = adjective;
                try {
                    while (adjective2.equals(adjective1) || adjective2.equals(adjective)) {
                        adjective2 = getRandom(parser.parsePartsofSpeech(data.wordsStartingWith(noun.substring(0, 1)), "adj", true));
                    }
                    endNotReached = false;
                } catch (Exception e) {
                    System.out.println("The noun you gave us returns null for a certain access method of the API");
                    noun = inputValidation(0);
                }
            }
        }

        //put '+' back to space as it was
        for (int i = 0; i < noun.length(); i++) {
            if (noun.charAt(i) == '+') {
                noun = noun.substring(0, i) + " " + noun.substring(i + 1, noun.length());
            }
        }

        //print the alliteration sentence, grammar sentence should work like most of the time
        System.out.println("The " + adjective + " " + noun + " " + adv + " " + verb + " the " + adjective1 + " " + noun1 + " whilst the " + adjective2 + " " + noun2 + " " + verb2 + ".");

        System.out.println(adjective + " " + noun + " " + adjective1 + " " + verb + " " + adjective2 + " " + verb2);
    }
    //returns a list of words that can
    //go after the word given
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


    //generates a random sentence using a word given by the user
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
                if (i != 4)
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
        System.out.println();
    }


    public static void alliterationSentence() {
        System.out.println("Welcome to alliteration sentence!!");
        rainbowWrite("Give us a sentence with no punctuation...", true);
        String sentence = s.nextLine();
        System.out.println("Changes will now be made to this sentence. What letter would you like words in this sentence to start with?");
        String letter = s.nextLine();
        //arraylist to become words from the original sentence
        ArrayList<String> words = new ArrayList<String>();
        //arraylist to become replaced words from the sentence
        ArrayList<String> finalWords = new ArrayList<String>();
        int index;
        for (int i = 0; i < sentence.length(); i++) {
            index = i;
            //breaks the sentence into an arraylist of the individual words (ignoring punctuation)
            while (!(sentence.charAt(index) == ' ') && index < sentence.length() - 1) {
                index++;
            }
            if (index == sentence.length() - 1)
                index++;
            words.add(sentence.substring(i, index));
            i = index;
        }
        //replaces word with a word that starts with letter and generally can be used as a replacement
        for (int i = 0; i < words.size(); i++) {
            finalWords.add(replaceWord(words.get(i), letter));
        }
        for (String s : finalWords)
            System.out.print(s + " ");
        System.out.println();
        System.out.println();
    }

    /**
     * replaces word with similar sounding word that starts with parameter letter to start
     * chosen randomly as of now from words that work
     *
     * @param word          input
     * @param lettertoStart letter to start with
     * @return word with similar meaning but starting with lettertoStart
     */
    public static String replaceWord(String word, String lettertoStart) {
        //remove plural words because api works poorly with plurals, not neccesary when using meanslike but is for synonyms
//        if(word.charAt(word.length() - 1) == 's')
//            word = word.substring(0,word.length()-1);
        //do not replace conjunction verbs because they are central to sentence structure!!
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
        conjunctionVerbs.add("as");
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
        //conjunctionVerbs.add("you");
        conjunctionVerbs.add("some");
        conjunctionVerbs.add("every");
        conjunctionVerbs.add("over");
        conjunctionVerbs.add("so");
        conjunctionVerbs.add("not");
        conjunctionVerbs.add("do");
        //although these can also be used as nouns, but usually are used as conjunction verbs so we will still remove them
        conjunctionVerbs.add("can");
        conjunctionVerbs.add("may");
        conjunctionVerbs.add("will");
        conjunctionVerbs.add("I");
        for (int i = 0; i < conjunctionVerbs.size(); i++) {
            if (word.toLowerCase().equals(conjunctionVerbs.get(i)))
                return word;
        }
        System.out.println("Trying to find replacements for " + word + "...");
        //boolean for checking for spaces
        boolean spaces = true;
        //placeholder for value of j
        int placeHolder = 0;
        //see if we can call the API without error on their word
        try {
            parser.parseWords(data.findSimilarStartsWith(word, lettertoStart));
        } catch (Exception e) {
            return word;
        }
        String[] synonyms = parser.parseWords(data.findSimilarStartsWith(word, lettertoStart));
        //unsorted list of max 1000 synonyms for word starting with letter
        if (synonyms[0].equals("inválido")) {
            System.out.println("No words found.");
            return word;
        } else {
            //arraylist of synonyms that match correct parameters
            ArrayList<String> alliterationSynonyms = new ArrayList<String>();
            //if the synonym is multiple words skip it
            for (int c = 0; c < synonyms.length; c++) {
                for (int j = 0; j < synonyms[c].length(); j++) {
                    if (synonyms[c].charAt(j) == ' ') {
                        synonyms[c] = "hemidemisemiquaver";
                        j = synonyms[c].length();
                    }
                }
            }
            for (int i = 0; i < synonyms.length; i++) {
                //if the word can be used in the same part of speech we are in the clear
                if (!(synonyms[i].equals("hemidemisemiquaver"))) {
                    if (AlliterationSentencePoSCheck(word, synonyms[i]))
                        alliterationSynonyms.add(synonyms[i]);
                }
            }
            //returns random word in the list of alliteration synonyms
            //do this so that the words are not to random if this word has a lot of synonyms
            if (alliterationSynonyms.size() > 30) {
                System.out.println("Word found!");
                return (alliterationSynonyms.get(random(30)));
            } else if (alliterationSynonyms.size() > 0) {
                System.out.println("Word found!");
                return getRandom(alliterationSynonyms);
            } else {
                System.out.println("No word found.");
                return word;
            }
        }
    }

    /**
     * compares the two words to see if they could be used in the same part of speech
     *
     * @param input         one word
     * @param wordToCompare other word
     */

    public static boolean AlliterationSentencePoSCheck(String input, String wordToCompare) {
        //all possible part of speech for the word to Compare
        ArrayList<String> wordToGuessPoS = data.getPartsofSpeech(wordToCompare);
        //all possible part of speech for the input
        ArrayList<String> inputPoS = data.getPartsofSpeech(input);
        boolean correctPoS = false;
        if (!(wordToGuessPoS.size() == 0 || inputPoS.size() == 0)) {
            for (int i = 0; i < inputPoS.size(); i++) {
                for (int j = 0; j < wordToGuessPoS.size(); j++) {
                    if (inputPoS.get(i).equals(wordToGuessPoS.get(j))) {
                        //if any of the PoS of the guess is the same PoS that the wordToGuess can be used as, then return true
                        correctPoS = true;
                        i = inputPoS.size();
                        j = wordToGuessPoS.size();
                    }
                }
            }
            //else just return false
        }
        return correctPoS;
    }

    //*All following methods are for shortening common actions (printing lists, getting value at a random index of a list
    private static void printList(String[] list) {
        for (String word : list) {
            System.out.print(word + ", ");
        }
    }

    private static void printIntList(int [] list) {
        for(int i : list) {
            System.out.print(i + ", ");
        }
    }

    /**
     * @param array array
     * @return a value at a random index
     */
    public static String getRandom(ArrayList<String> array) {
        return array.get(random(array.size()) - 1);
    }

    public static String getListRandom(String[] array) {
        return array[random(array.length - 1)];
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
        public static int random(int max) {
        return (int) ((Math.random() * max) + 1);
    }

    /**
     * another print method that writes in rainbow
     *
     * @param chunk   the thing they want to print
     * @param printLn whether it wants to be printed with println or just print
     */

    public static void rainbowWrite(String chunk, boolean printLn) {
        //ArrayList of the colors of the rainbow, length so that it can write full chunk
        ArrayList<String> Colors = new ArrayList<String>();
        //the chunk to be returned
        String rainbowChunk = "";
        //int to ensure that even with a blank space the color won't be skipped
        int offset = 0;
        //adding colors of the rainbow to Colors
        for (int i = 0; i < (chunk.length() / 6 + 6); i++) {
            Colors.add(ANSI_RED);
            Colors.add(ANSI_YELLOW);
            Colors.add(YELLOW_BRIGHT);
            Colors.add(ANSI_GREEN);
            Colors.add(ANSI_BLUE);
            Colors.add(ANSI_PURPLE);
        }
        for (int i = 0; i < chunk.length(); i++) {
            //do not do anything if it is just a blank space baby
            if (chunk.charAt(i) == ' ') {
                rainbowChunk += chunk.charAt(i);
                offset++;
            } else
                rainbowChunk += (Colors.get(i - offset) + chunk.charAt(i) + ANSI_RESET);
            //^adding color to just the substring of one char, so that chunk changes colors each letter
        }
        //boolean for println vs. print
        if (printLn)
            System.out.println(rainbowChunk);
        else
            System.out.print(rainbowChunk);
    }

    /**
     * very similar to rainbowWrite but by changing the one **indicated** area we can implement different color patterns
     *
     * @param chunk   what they want printed
     * @param printLn whether they want it println or print
     */
    public static void christmasWrite(String chunk, boolean printLn) {
        //ArrayList of the colors of the rainbow, length so that it can write full chunk
        ArrayList<String> Colors = new ArrayList<String>();
        //the chunk to be returned
        String christmasChunk = "";
        //int to ensure that even with a blank space the color won't be skipped
        int offset = 0;
        //adding colors of the rainbow to Colors, change numbers to how often you will alternate between colors
        for (int i = 0; i < (chunk.length() / 2 + 2); i++) {
            //**indicated**
            Colors.add(ANSI_RED);
            Colors.add(ANSI_GREEN);
        }
        for (int i = 0; i < chunk.length(); i++) {
            //do not do anything if it is just a blank space baby
            if (chunk.charAt(i) == ' ') {
                christmasChunk += chunk.charAt(i);
                offset++;
            } else
                christmasChunk += (Colors.get(i - offset) + chunk.charAt(i) + ANSI_RESET);
            //^adding color to just the substring of one char, so that chunk changes colors each letter
        }
        //boolean for println vs. print
        if (printLn)
            System.out.println(christmasChunk);
        else
            System.out.print(christmasChunk);
    }

    /**
     * very similar to rainbowWrite but by changing the one **indicated** area we can implement different color patterns
     *
     * @param chunk   what they want printed
     * @param printLn whether they want it println or print
     */
    public static void patrioticWrite(String chunk, boolean printLn) {
        //ArrayList of the colors of the rainbow, length so that it can write full chunk
        ArrayList<String> Colors = new ArrayList<String>();
        //the chunk to be returned
        String patrioticChunk = "";
        //int to ensure that even with a blank space the color won't be skipped
        int offset = 0;
        //adding colors of the rainbow to Colors, change numbers to how often you will alternate between colors
        for (int i = 0; i < (chunk.length() / 2 + 2); i++) {
            //**indicated**//
            Colors.add(ANSI_RED);
            Colors.add(ANSI_WHITE);
            Colors.add(ANSI_BLUE);
        }
        for (int i = 0; i < chunk.length(); i++) {
            //do not do anything if it is just a blank space baby
            if (chunk.charAt(i) == ' ') {
                patrioticChunk += chunk.charAt(i);
                offset++;
            } else
                patrioticChunk += (Colors.get(i - offset) + chunk.charAt(i) + ANSI_RESET);
            //^adding color to just the substring of one char, so that chunk changes colors each letter
        }
        //boolean for println vs. print
        if (printLn)
            System.out.println(patrioticChunk);
        else
            System.out.print(patrioticChunk);
    }





}
