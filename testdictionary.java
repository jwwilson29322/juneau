

public class testdictionary {
    public static void main(String[] args) {
        String[] words = 									    // clue
			{"CIGAR", "DWARF", "MAJOR", "RUDDY",				// 11211
					"REBUT", "REACT", "RETCH", 			 		// 23211
					"SISSY", "BLUSH", "BASIC",			 		// 11112
					"HUMPH","FOCAL","CLUCK","CLOCK","CLICK",	// 11111
			"TERNS"};							 		// 33333
		DictionaryIP dict = new DictionaryIP (5, words);
        String guess = "TERNS";
        int clue = 11211;
        System.out.println(dict.lenght());
        int length = 0;
        howManyWordsWithClue(dict, clue, guess);
    }
    public static int howManyWordsWithClue(DictionaryIP dict, int clue, String guess) {
        
        checkThrees(dict, guess, clue);
        checkTwos(dict, guess, clue);
        checkOnes(dict, guess, clue);
          
        return dict.lenght();
    }

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
}
