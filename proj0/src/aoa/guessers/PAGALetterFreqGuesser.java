package aoa.guessers;

import aoa.utils.FileUtils;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class PAGALetterFreqGuesser implements Guesser {
    private final List<String> words;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        // check if the guesses are within the pattern

        List<String> filteredWords = GuesserLibrary.filterInvalid(guesses, pattern, words);
        filteredWords = GuesserLibrary.patternFilterWords(filteredWords, pattern);
        System.out.println("pattern filtered words "+filteredWords);
        filteredWords = GuesserLibrary.filterOutRepeating(filteredWords, pattern);
        System.out.println("repeating pattern filtered words "+filteredWords);
        Map<Character, Integer> fm = GuesserLibrary.getFrequencyMap(filteredWords);
        return GuesserLibrary.getGuess(guesses, fm);
    }

    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
