
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
 
 
        if (clueLength == size && clue > 0 && isTernary(clue, size)) {
            return true;
        } else
        return false;
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

    public static void printClue(int clue, String guess, String word){
        clue = clueForGuessAndWord(guess, word);
        String s = String.valueOf(clue);
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i ++) {
            if (s.charAt(i) == '1') {
                str.append(StringColouring.toColoredString(String.valueOf(guess.charAt(i )), StringColouring.BLACK));
            } else if (s.charAt(i) == '2') {
                str.append(StringColouring.toColoredString(String.valueOf(guess.charAt(i)), StringColouring.YELLOW));
            } else {
                str.append(StringColouring.toColoredString(String.valueOf(guess.charAt(i)), StringColouring.GREEN));
            }
        }
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
        DictionaryIP dict2;
        dict2 = new DictionaryIP(guess.length()); 
        checkThrees(dict2, guess, clue);
        checkTwos(dict2, guess, clue);
        checkOnes(dict2, guess, clue);
        return dict2.lenght();
    }

    public static void checkOnes(DictionaryIP dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='1') {
                for (int l = 0; l < dict.lenght(); l++) {
                    for (int h = 0; h < guess.length(); h++) {
                        if (checkThisLetter(guess.charAt(i), dict.getWord(l), h)) {
                            dict.selectForRemove(l);
                        }
                    }
                }
            }
        }
        dict.removeSelected();
    }
    public static void playGuess(DictionaryIP dict, String guess) {

    }

    public static void checkTwos(DictionaryIP dict, String guess, int clue) {
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
                    if (hasLetter==false) {
                        dict.selectForRemove(j);
                    }
                }
                dict.removeSelected();
            }  
        }
    }

    public static void checkThrees(DictionaryIP dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='3') {
                for (int j = 0; j < dict.lenght(); j++) {
                    if (checkThisLetter(guess.charAt(i), dict.getWord(j), i)==false) {
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
    public static boolean checkThisLetter(char c, String wordToCheck, int pos) {
       boolean letterInWord;
       if (wordToCheck.charAt(pos)==c) {
           letterInWord = true;
       } else {
           letterInWord = false;
       }
       return letterInWord;
   }

    // public static int betterntClueForGuess(DictionaryIP dict, String guess) {
    //     int clueStart = minClue(guess.length());
    //     int max = howManyWordsWithClue(dict, clueStart, guess);
    //     int clueMax = clueStart;
    //     int num1;
    //     int num2;
    //     for (int i = 1; i < dict.lenght(); i+=2) {
    //         num1 = howManyWordsWithClue(dict, , guess);
    //         num2 = howManyWordsWithClue(dict, , guess);
    //         if (num1 > num2 && num1 > max) {
    //             max = num1;
    //             clueMax = clueForGuessAndWord(guess, dict.getWord(i));
    //         } else if (num2 > num1 && num2 > max)  {
    //             max = num2;
    //             clueMax = clueForGuessAndWord(guess, dict.getWord(i-1));
    //         }
    //     }   
    //     return clueMax;
    // }

    public static int betterClueForGuess(DictionaryIP dict, String guess) {
        int clueStart = minClue(guess.length())+1;
        int max = howManyWordsWithClue(dict, clueStart, guess);
        int clueMax = clueStart;
        int num1;
        int num2;
        while (isMaxClue(clueStart, guess.length())==false) {
            num1 = howManyWordsWithClue(dict, clueStart, guess);
            num2 = howManyWordsWithClue(dict, clueStart-1, guess);
            if (num1 > num2 && num1 > max) {
                max = num1;
                clueMax = clueStart;
            } else if (num2 > num1 && num2 > max)  {
                max = num2;
                clueMax = clueStart-1;
            }
            clueStart = nextClue(clueStart+1, guess.length());
        }   
        return clueMax;
    }

//     public static int playGuess(DictionaryIP dict, String guess){
//         int clueForGuess = betterClueForGuess(dict, guess);
//         for(int i = 0; i<dict.lenght(); i++){
//             if(clueForGuessAndWord(dict.getWord(i), guess) != clueForGuess){
//                 dict.selectForRemove(i);
//             }
//         }
//         dict.removeSelected();
//         return clueForGuess;
//    }

//     public static int playGuess(DictionaryIP dict, String guess) {
//         howManyWordsWithClue(dict, betterClueForGuess(dict, guess), guess);
//         return betterClueForGuess(dict, guess);
//     }
}