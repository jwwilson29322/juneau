import java.util.Scanner;
public class Ipurdle {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        int wordSize = 5;
        int maxAttempts = 6;
        if (args!=null && args.length>0 && args[0] != null) {
            wordSize = Integer.parseInt(args[0]);
        }
        if (args!=null && args.length>0 && args[1] != null) {
            maxAttempts = Integer.parseInt(args[1]);
        }
        welcomeMessage(wordSize, maxAttempts);
        String guess = "";
        DictionaryIP gameWordsDictionary = new DictionaryIP(wordSize);
        DictionaryIP puzzlesDictionary = new DictionaryIP(wordSize);
        do{
            guess = askForGuess(gameWordsDictionary, scr);

            System.out.print("Palavra com a pista > ");
            printClue(playGuess(puzzlesDictionary, guess), guess);
            System.out.println("");
            if (isMaxClue(playGuess(puzzlesDictionary, guess), wordSize)) {
                System.out.println("");
                System.out.println("");
                System.out.println("Parabens, encontraste a palavra secreta!!!");
                end(scr);
                //END
            } else {
                maxAttempts -=1;
            }
        } while (maxAttempts > 0);
        if (!isMaxClue(playGuess(puzzlesDictionary, guess), wordSize)) {
            System.out.println("");
            System.out.println("");
            System.out.println("YOU LOST");
            end(scr);
        };
    }

    private static void askToPlayAgain(){
        System.out.println("");
        System.out.println("");
        System.out.println("Would you like to play again?  Y/N ");
        System.out.println("");
        System.out.println("");
    }

    public static void end(Scanner scr){
        askToPlayAgain();
        String input = scr.nextLine();
        if (input.equals("Y") || input.equals("y")){
            main(null);
        }else {
            System.exit(0);
        }
    }

    public static String askForGuess(DictionaryIP gameWordsDictionary, Scanner scr) {
        boolean validWord = false;
        String guess = "";
        do {
            System.out.print("Palavra a jogar? ");
            guess = scr.nextLine();
            guess = guess.toUpperCase();

            int size = guess.length();
            int wordSize = gameWordsDictionary.getWordSize();
            if (guess.equals("QUIT")) {
                //END
                end(scr);
            } else if (!gameWordsDictionary.isValid(guess)) {
                System.out.println("Palavra invalida, nao existe no dicionario");
            } else if (size != wordSize) {
                System.out.println("Palavra invalida, tamanho errado");
            } else {
                validWord = true;
            }
        } while (!validWord);
        return guess;
    }


    public static void welcomeMessage(int wordSize, int maxAttempts) {
        System.out.println("");
        System.out.println("");
        System.out.println("Bem vindo ao jogo Ipurdle de Juneau Wilson e Dienis Garkavenko!");
        System.out.println("Neste jogo as palavras tem tamanho " + wordSize + ". O dicionario tem apenas palavras em ingles relacionadas com IP.");
        System.out.println("Tens " + maxAttempts + " tentativas para adivinhar a palavra. Boa sorte!");
        System.out.println("");
        System.out.println("");
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

    /**
     * Creates a numerical clue that corresponds to the minimum clue possible. 
     * 
     * @param size length of words chosen for this game.
     * @requires {@code size} is a number larger than 0.
     * @return numerical representation of the minimum clue possible.
     */
    public static int minClue(int size){
        int clue = 0;
        for(int i= 0; i < size; i ++){
           clue = (clue * 10) + 1;
        }
        return clue;
    }

    /**
     * Checks if the maximum clue with {@code clue} and {@code size} is valid.
     * 
     * @param clue numerical representation of characters correct, incorrect, or in wrong position in word.
     * @param size length of words chosen for this game.
     * @requires {@code size} is number larger than 0 and {@code clue} is a valid representation for corresponding word length.
     * @return true if numerical representation of correct characters is valid given a {@code clue} and {@code size}.
     */
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
     * Creates a visual colored representation of {@code guess} given the correct numerical representation for that word, being green the correct letter and position, 
     * being yellow the correct letter and wrong position and being black the wrong position and letter.
     * 
     * @param clue numerical representation of characters correct, incorrect, or in wrong position in word.
     * @param guess word input by player in this round of game.
     * @requires {@code guess} being different than null and {@code clue} being the valid numerical representation of {@code guess}.
     * @return the colored version of {@code guess} in accordance of {@code clue}.
     */
    public static void printClue(int clue, String guess) {
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
        System.out.print(str);
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

    public static DictionaryIP cloneDictionary(DictionaryIP dict1) {
        String[] dict2Words = new String[dict1.lenght()];
        for (int x = 0; x < dict1.lenght(); x++){
            dict2Words[x] = dict1.getWord(x);
        }
        return new DictionaryIP(dict1.getWordSize(), dict2Words);
    }

    public static int howManyWordsWithClue(DictionaryIP dict, int clue, String guess) {
        DictionaryIP dict2 = cloneDictionary(dict);
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
                dict.removeSelected();
            }
        }
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
                    if (!hasLetter) {
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
    public static boolean checkThisLetter(char c, String wordToCheck, int pos) {
        boolean letterInWord;
        if (wordToCheck.charAt(pos)==c) {
            letterInWord = true;
        } else {
            letterInWord = false;
        }
        return letterInWord;
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
    
    /**
    * Calculates the best clue for the {@code guess} using a previous method, betterClueForGuess, and removes all the other words that don´t result in this clue.
    *
    * @param dict dictonary of all valid words.
    * @param guess word input by player in this round of game.
    * @requires {@code guess} and {@code dict} to be different than null.
    * @return the best clue for {@code guess} facing the {@code dict}.
    */
    public static int playGuess(DictionaryIP dict, String guess) {
        int clueForGuess = betterClueForGuess(dict, guess);
        for (int i = 0; i < dict.lenght(); i++) {
            if (clueForGuessAndWord(guess, dict.getWord(i)) != clueForGuess) {
                dict.selectForRemove(i);
            }
        }
        dict.removeSelected();
        return clueForGuess;
    }
}