// Source code is decompiled from a .class file using FernFlower decompiler.
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DictionaryIP {
   private int wordSize;
   private List<String> words;
   private Collection<String> selectedWords;
   private static final String[] ALL_WORDS = new String[]{"JAVA", "LOOP", "EXIT", "TRUE", "LONG", "THIS", "BREAK", "WHILE", "GRADE", "PUPIL", "FIELD", "BASIC", "ABORT", "ABOVE", "FALSE", "FLOAT", "SHORT", "CLASS", "FINAL", "STATIC", "METHOD", "STRING", "RETURN", "RANDOM", "EQUALS", "OBJECT", "FUNCTION", "VARIABLE", "INTEGER", "SCANNER"};

   public DictionaryIP(int var1) {
      this.wordSize = var1;
      this.words = new ArrayList();
      String[] var2 = ALL_WORDS;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String var5 = var2[var4];
         if (var5.length() == var1) {
            this.words.add(var5);
         }
      }

      this.selectedWords = new ArrayList();
   }

   DictionaryIP(int var1, String[] var2) {
      this.wordSize = var1;
      this.words = new ArrayList();
      String[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         if (var6.length() == var1) {
            this.words.add(var6);
         }
      }

      this.selectedWords = new ArrayList();
   }

   public int lenght() {
      return this.words.size();
   }

   public int getWordSize() {
      return this.wordSize;
   }

   public boolean isValid(String var1) {
      return this.words.contains(var1);
   }

   public String getWord(int var1) {
      return (String)this.words.get(var1);
   }

   public void remove(String var1) {
      this.words.remove(var1);
   }

   public void selectForRemove(int var1) {
      this.selectedWords.add(this.words.get(var1));
   }

   public void removeSelected() {
      this.words.removeAll(this.selectedWords);
      this.selectedWords = new ArrayList();
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.wordSize, this.words});
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (this.getClass() != var1.getClass()) {
         return false;
      } else {
         DictionaryIP var2 = (DictionaryIP)var1;
         return this.wordSize == var2.wordSize && Objects.equals(this.words, var2.words);
      }
   }

   public String toString() {
      return this.words.toString();
   }
}
