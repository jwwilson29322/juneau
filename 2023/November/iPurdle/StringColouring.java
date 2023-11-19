//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

public class StringColouring {
    public static final String BLACK = "\u001b[0;30m";
    public static final String RED = "\u001b[0;31m";
    public static final String GREEN = "\u001b[0;32m";
    public static final String YELLOW = "\u001b[0;33m";
    public static final String BLUE = "\u001b[0;34m";
    private static final String RESET = "\u001b[0m";

    private StringColouring() {
    }

    public static String toColoredString(String var0, String var1) {
        return var1 + var0 + "\u001b[0m";
    }
}
