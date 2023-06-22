package aoa.choosers;

import java.util.List;

import aoa.guessers.Guesser;
import aoa.guessers.GuesserLibrary;
import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.util.TreeMap;
import java.util.ArrayList;

public class EvilChooser implements Chooser {
    private String pattern;
    private List<String> wordPool;

    public EvilChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in this constructor.
        if (wordLength < 1) {
            throw new IllegalArgumentException("meow meow ");
        }
        wordPool = FileUtils.readWordsOfLength(dictionaryFile, wordLength);
        if (wordPool.size() == 0) {
            throw new IllegalStateException("aaaaaaa");
        }
        pattern = "-";
        for (int i = 0; i < wordLength-1; i++) {
            pattern += '-';
        }
    }

    @Override
    public int makeGuess(char letter) {
        TreeMap<String, ArrayList<String>> tm = new TreeMap<String, ArrayList<String>>();
        for (String word : wordPool) {
            String wordPattern = GuesserLibrary.generatePattern(word, letter);
            for (int i = 0; i < pattern.length(); i++) {
                if (wordPattern.charAt(i) == '-') {
                    wordPattern = wordPattern.substring(0, i)+pattern.charAt(i)+wordPattern.substring(i+1);
                }
            }
            if (tm.containsKey(wordPattern)) {
                ArrayList<String> patternWordsList = tm.get(wordPattern);
                patternWordsList.add(word);
                tm.put(wordPattern, patternWordsList);
            } else {
                ArrayList<String> patternWordsList = new ArrayList<String>();
                patternWordsList.add(word);
                tm.put(wordPattern, patternWordsList);
            }
        }
        int largestPatternFamilySize = 0;
        String largestPatternFamily = "";
        for (String generatedPattern : tm.keySet()) {
            if (tm.get(generatedPattern).size() > largestPatternFamilySize) {
                largestPatternFamily = generatedPattern;
                largestPatternFamilySize = tm.get(generatedPattern).size();
            }
        }
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '-') {
                pattern = pattern.substring(0, i)+largestPatternFamily.charAt(i)+pattern.substring(i+1);
            }
        }
        wordPool = tm.get(pattern);
        int guessOccurence = 0;
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == letter) {
                guessOccurence++;
            }
        }
        return guessOccurence;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        int randomlyChosenWordNumber = StdRandom.uniform(wordPool.size());
        return wordPool.get(randomlyChosenWordNumber);
    }
}
