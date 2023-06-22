package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in/change this constructor.
        if (wordLength < 1) {
            throw new IllegalArgumentException("meow meow ");
        }
        List<String> words = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (words.size() == 0) {
            throw new IllegalStateException("aaaaaaa");
        }
        int randomlyChosenWordNumber = StdRandom.uniform(words.size());
        chosenWord = words.get(randomlyChosenWordNumber);
        pattern = "-";
        for (int i = 0; i < wordLength - 1; i++) {
            pattern += '-';
        }
    }

    @Override
    public int makeGuess(char letter) {
        int occurrences = 0;
        for (int i = 0; i < chosenWord.length(); i++) {
            char c = chosenWord.charAt(i);
            if (c == letter) {
                occurrences++;
            }
            if (pattern.charAt(i) == '-' && c == letter) {
                pattern = pattern.substring(0, i)+letter+pattern.substring(i+1);
            }
        }
        return occurrences;
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return pattern;
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return chosenWord;
    }
}
