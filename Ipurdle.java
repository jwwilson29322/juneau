
public class Ipurdle {
    public static void main(String[] args) {
        //MAIN GOES HERE
    }

    /**
     * Checks if {@code clue} is valid.
     * 
     * @param clue numerical representation of characters correct, incorrect, or in wrong position in word.
     * @param size length of words chosen for this game.
     * @return true if {@code clue} is a valid representation for corresponding word length.
     */
    public static boolean validClue(int clue, int size) {
        int clueLength = String.valueOf(clue).length();
        return clueLength == size && clue > 0 && isTernary(clue, size);
    }
 
    /**
     * Checks if {@code clue} is a valid ternary number.
     *  
     * @param clue decimal integer representation of possible ternary number.
     * @param size length of words chosen for this game.
     * @return true if {@code clue} is a valid base three number.
     * @requires {@code clue} is a number of {@code size} digits.
     */
    public static boolean isTernary(int clue, int size) {
        clue -= minClue(size);
        for (int pos = 1; pos <= size; pos++) {
            if ((clue % Math.pow(10, pos)) >= (3 * Math.pow(10, pos-1))) {
                return false;
            }
        }
        return true;
    }

    public static int minClue(int size){
        int clue = 0;
        for(int i= 0; i < size; i ++){
           clue = (clue * 10) + 1;
        }
        return clue;
    }

    public static boolean isMaxClue(int clue, int size){
        boolean maxClue = true;
        int tst = 0;
        for(int i = 0; i < size; i++){
            tst = clue % 10;
            clue /= 10;
            if(tst != 3){
                maxClue = false;
            }
        }return maxClue;
    }

    /**
     * Calculates smallest {@code clue} larger than current {@code clue}; adds one to {@code clue}.
     * 
     * @param clue numerical representation of characters correct, incorrect, or in wrong position in word.
     * @param size length of words chosen for this game.
     * @return numerical representation of {@code clue} plus one.
     * @requires {@code clue} must be a valid clue for word length chosen.
     * @ensures result is a valid clue for chosen word length.
     */
    public static int nextClue(int clue, int size) {
        boolean carry = false;
        clue++;
        clue -= minClue(size);
        for (int pos = 1; pos <= size; pos++) {
            carry = (clue % Math.pow(10, pos)) >= (3 * Math.pow(10, pos-1));
            if (carry) {
                clue += (9 * Math.pow(10, pos-1));
                clue -= (clue % Math.pow(10, pos));
            }
        }
        clue += minClue(size);
        return clue;
    }


    /**
     * Creates a numerical clue that corresponds to the guess and word input
     * 
     * @param guess word input by player in this round of game.
     * @param word correct word that player is trying to guess.
     * @requires guess and word have same number of characters, guess and word all uppercase.
     * @return the integer clue that represents the correct {@code clue} for {@code guess} and {@code word}
     */
    public static int clueForGuessAndWord(String guess, String word) {
        StringBuilder notSamePos = new StringBuilder();
        StringBuilder clue = new StringBuilder();
        clue.append(Integer.toString(minClue(word.length())));
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess.charAt(i)) {
                clue.deleteCharAt(i);
                clue.insert(i, 3);
            } else {
                notSamePos.append(word.charAt(i));
            }
        }
        for(int j = 0; j < word.length(); j++) {
            for (int k = 0; k < notSamePos.length(); k++) {
                if (guess.charAt(j) == notSamePos.charAt(k)) {
                    clue.deleteCharAt(j);
                    clue.insert(j, 2);
                    notSamePos.deleteCharAt(k);
                }
            }
        }
        return Integer.parseInt(clue.toString());
    }

    private static DictionaryIP cloneDictionary(DictionaryIP dict1){
        String[] dict2Words = new String[dict1.lenght()];
        for (int x = 0; x<dict1.lenght(); x++){
            dict2Words[x] = dict1.getWord(x);
        }
        return new DictionaryIP(dict1.getWordSize(), dict2Words);
    }

    /**
     * Calculates number of valid possible words with {@code clue} and {@code guess}.
     * 
     * @param dict dictonary of all valid words.
     * @param clue numerical representation of characters correct, incorrect, or in wrong position in word.
     * @param guess word input by player in this round of game.
     * @return number of possible valid words.
     * @requires {@code clue} be a valid clue for {@code guess}.
     */
    public static int howManyWordsWithClue(DictionaryIP dict, int clue, String guess) {
        DictionaryIP dict2 = cloneDictionary(dict);
        checkThrees(dict2, guess, clue);
        checkTwos(dict2, guess, clue);
        checkOnes(dict2, guess, clue);
        return dict2.lenght();
    }

    private static void checkOnes(DictionaryIP dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='1') {
                for (int l = 0; l < dict.lenght(); l++) {
                    for (int h = 0; h < guess.length(); h++) {
                        if (checkThisLetter(guess.charAt(i), dict.getWord(l), h)) {
                            dict.selectForRemove(l);
                        }
                    }
                }
                dict.removeSelected();
            }
        }
    }

    private static void checkTwos(DictionaryIP dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='2') {
                for (int k = 0; k < dict.lenght(); k++) {
                    if (guess.charAt(i)==(dict.getWord(k)).charAt(i)) {
                        dict.selectForRemove(k);
                    }
                }
                dict.removeSelected();
                for (int j = 0; j < dict.lenght(); j++) {
                    boolean hasLetter = false;
                    for (int p = 0; p < guess.length(); p++) {
                        if (checkThisLetter(guess.charAt(i), dict.getWord(j), p)) {
                            hasLetter = true;
                        }
                    }
                    if (!hasLetter) {
                        dict.selectForRemove(j);
                    }
                }
                dict.removeSelected();
            }  
        }
    }

    private static void checkThrees(DictionaryIP dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='3') {
                for (int j = 0; j < dict.lenght(); j++) {
                    if (!checkThisLetter(guess.charAt(i), dict.getWord(j), i)) {
                        dict.selectForRemove(j);
                    }
                }
                dict.removeSelected();
            }
        }
    }

    /**
     * Checks if a certain character input is in a chosen string in a specific position.
     * 
     * @param c character being checked against word.
     * @param wordToCheck word being checked for presence of {@code c}.
     * @param pos position in which {@code c} must be in in the word.
     * @return true if {@code c} is in {@code wordToCheck} in {@code pos}.
     * @requires {@code pos} must be less than length of {@code wordToCheck}.
     */
    private static boolean checkThisLetter(char c, String wordToCheck, int pos) {
       return wordToCheck.charAt(pos)==c;
   }

    public static int betterClueForGuess(DictionaryIP dict, String guess) {
        int clueStart = minClue(guess.length());
        int currentMaxWords = howManyWordsWithClue(dict, clueStart, guess);
        int clueMax = clueStart;
        while (!isMaxClue(clueStart, guess.length())) {
            int secondClue = nextClue(clueStart, guess.length());
            int num1 = howManyWordsWithClue(dict, clueStart, guess);
            int num2 = howManyWordsWithClue(dict, secondClue, guess);
            if (num1 > num2 && num1 > currentMaxWords) {
                currentMaxWords = num1;
                clueMax = clueStart;
            } else if (num2 > num1 && num2 > currentMaxWords)  {
                currentMaxWords = num2;
                clueMax = secondClue;
            }
            clueStart = nextClue(clueStart, guess.length());
        }   
        return clueMax;
    }

     public static int playGuess(DictionaryIP dict, String guess){
         int clueForGuess = betterClueForGuess(dict, guess);
         for(int i = 0; i<dict.lenght(); i++){
             if(clueForGuessAndWord(guess, dict.getWord(i)) != clueForGuess){
                 dict.selectForRemove(i);
             }
         }
         dict.removeSelected();
         return clueForGuess;
    }

}