

public class testdictionary {
    public static void main(String[] args) {
        String[] words = 									    // clue
			{"CIGAR", "DWARF", "MAJOR", "RUDDY",				// 11211
					"REBUT", "REACT", "RETCH", 			 		// 23211
					"SISSY", "BLUSH", "BASIC",			 		// 11112
					"HUMPH","FOCAL","CLUCK","CLOCK","CLICK",	// 11111
			"TERNS"};							 		// 33333

		DictionaryIP dictOriginal = new DictionaryIP(5, words);
      

        String guess = "TERNS";

        int clue = 11211;
        howManyWordsWithClue(dictOriginal, clue, guess, words);
        clue = 23211;
        howManyWordsWithClue(dictOriginal, clue, guess, words);
        clue = 11112;
        howManyWordsWithClue(dictOriginal, clue, guess, words);
        clue = 11111;
        howManyWordsWithClue(dictOriginal, clue, guess, words);
        clue = 33333;
        howManyWordsWithClue(dictOriginal, clue, guess, words);
    


    }

    public static int dictionaryOnlyWordsWithClue(DictionaryIP dict, int clue, String guess) {   
        checkThrees(dict, guess, clue);
        checkTwos(dict, guess, clue);
        checkOnes(dict, guess, clue);
        return dict.lenght();
    }

    public static int howManyWordsWithClue(DictionaryIP dict, int clue, String guess, String[] words) {  
        DictionaryIP dict2 = new DictionaryIP(dict.getWordSize(), words); 
        checkThrees(dict2, guess, clue);
        checkTwos(dict2, guess, clue);
        checkOnes(dict2, guess, clue);
        System.out.println(dict2);
        return dict2.lenght();
    }



    public static void checkOnes(DictionaryIP dictCheck, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='1') {
                for (int l = 0; l < dictCheck.lenght(); l++) {
                    for (int h = 0; h < guess.length(); h++) {
                        if (checkThisLetter(guess.charAt(i), dictCheck.getWord(l), h)) {
                            dictCheck.selectForRemove(l);
                        }
                    }
                }
            }
        }
        dictCheck.removeSelected();
    }

    public static void checkTwos(DictionaryIP dictCheck, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='2') {
                for (int k = 0; k < dictCheck.lenght(); k++) {
                    if (guess.charAt(i)==(dictCheck.getWord(k)).charAt(i)) {
                        dictCheck.selectForRemove(k);
                    }
                }
                dictCheck.removeSelected();
                for (int j = 0; j < dictCheck.lenght(); j++) {
                    boolean hasLetter = false;
                    for (int p = 0; p < guess.length(); p++) {
                        if (checkThisLetter(guess.charAt(i), dictCheck.getWord(j), p)) {
                            hasLetter = true;
                        }
                    }
                    if (hasLetter==false) {
                        dictCheck.selectForRemove(j);
                    }
                }
                dictCheck.removeSelected();
            }  
        }
    }

    public static void checkThrees(DictionaryIP dictCheck, String guess, int clue) {
        for (int i = 0; i < guess.length(); i++) {
            if ((Integer.toString(clue).charAt(i))=='3') {
                for (int j = 0; j < dictCheck.lenght(); j++) {
                    if (checkThisLetter(guess.charAt(i), dictCheck.getWord(j), i)==false) {
                        dictCheck.selectForRemove(j);
                    }
                }
                dictCheck.removeSelected();
            }
        }
    }
    
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
