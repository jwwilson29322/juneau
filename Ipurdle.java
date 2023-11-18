import java.util.ArrayList;

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
        
        checkThrees(dict, guess, clue);
        checkOnes(dict, guess, clue);
        checkTwos(dict, guess, clue);
        
          
        return dict.lenght();
    }

    private static String[] cloneDictionary(DictionaryIP dict){
        String[] dictList = new String[dict.lenght()];
        for (int x = 0; x<dict.lenght(); x++){
            dictList[x] = dict.getWord(x);
        }
        return dictList;
    }

    public static void checkOnes(DictionaryIP dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='1') {
                for (int l = 0; l < dict.lenght(); l++) {
                    for (int h = 0; h < guess.length(); h++) {
                        if (checkThisLetter(guess.charAt(i), dict.getWord(l), h)) {
                            dict.selectForRemove(l);
                            System.out.println("removed1");
                        }
                    }
                }
            }
        }
        dict.removeSelected();
    }

    public static void checkTwos(DictionaryIP dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='2') {
                for (int k = 0; k < dict.lenght(); k++) {
                    if (checkThisLetter(guess.charAt(i), dict.getWord(k), i)) {
                        dict.selectForRemove(k);
                        System.out.println("removed2");
                        dict.removeSelected();
                    }
                    int counter = 0;
                    for (int m = 1; m <= guess.length(); m++) {
                        counter = 0;
                        if (checkThisLetter(guess.charAt(i), dict.getWord(0), m-1)) {
                            counter++;
                        }
                        if (m==guess.length() && counter==0) {
                            dict.selectForRemove(k);
                            System.out.println("removed2");
                            dict.removeSelected();
                        }
                    }  
                }       
            }   
        }
    }

    public static void checkThrees(DictionaryIP dict, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='3') {
                int j = 0; 
                while (j < dict.lenght()) {
                    if (checkThisLetter(guess.charAt(i), dict.getWord(j), i)==false) {
                        dict.selectForRemove(j);
                        System.out.println("removed3");
                        dict.removeSelected();
                    }
                    j++;
                }
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
}