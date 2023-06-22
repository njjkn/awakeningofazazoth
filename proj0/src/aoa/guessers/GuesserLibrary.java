package aoa.guessers;

import java.util.*;

public class GuesserLibrary {
    public static Map<Character, Integer> getFrequencyMap(List<String> words) {
        Map<Character, Integer> frequencyMap = new TreeMap<Character, Integer>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (frequencyMap.containsKey(c)) {
                    frequencyMap.put(c, frequencyMap.get(c)+1);
                } else {
                    frequencyMap.put(c, 1);
                }
            }
        }
        return frequencyMap;
    }

    public static List<String> patternFilterWords(List<String> words, String pattern) {
        List<String> filteredList = new ArrayList<String>();
        for (String word : words) {
            if (matchesPattern(word, pattern)) {
                filteredList.add(word);
            }
        }
        return filteredList;
    }

    public static boolean matchesPattern(String word, String pattern) {
        if (word.length() != pattern.length()) {
            return false;
        } else {
            for (int i = 0; i < word.length(); i++) {
                if (pattern.charAt(i) != '-' && pattern.charAt(i) != word.charAt(i)) {
                    return false;
                }
            }
            return true;
        }
    }

    public static char getGuess(List<Character> guesses, Map<Character, Integer> fm) {
        if (fm.isEmpty()) {
            return '?';
        }
        Set<Character> set = fm.keySet();
        char maxFreqChar = '?';
        int maxFreq = 0;
        for (char c : set) {
            if (!guesses.contains(c)) {
                if (fm.get(c) > maxFreq) {
                    maxFreq = fm.get(c);
                    maxFreqChar = c;
                }
            }
        }
        return maxFreqChar;
    }

    public static List<String> filterInvalid(List<Character> guesses, String pattern, List<String> words) {
        Set<Character> invalidChars = new HashSet<Character>();
        List<Character> patternList = new ArrayList<Character>();
        List<String> filteredWords = new ArrayList<>();
        for (int i = 0; i < pattern.length(); i++) {
            patternList.add(pattern.charAt(i));
        }
        for (char guess : guesses) {
            if (!patternList.contains(guess)) {
                invalidChars.add(guess);
            }
        }
        for (String word : words) {
            List<Character> letterList = new ArrayList<Character>();
            for (int i = 0; i < word.length(); i++) {
                letterList.add(word.charAt(i));
            }
            if (invalidChars.size() > 0) {
                for (char c : invalidChars) {
                    if (!letterList.contains(c)) {
                        filteredWords.add(word);
                    }
                }
            } else {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    public static List<String> filterOutRepeating(List<String> words, String pattern) {
        Map<Character, Integer> frequencyMap = new TreeMap<Character, Integer>();
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (frequencyMap.containsKey(c)) {
                frequencyMap.put(c, frequencyMap.get(c)+1);
            } else {
                frequencyMap.put(c, 1);
            }
        }
        List<String> filteredWords = new ArrayList<String>(words);
        for (String word : words) {
            Map<Character, Integer> patternMap = new TreeMap<Character, Integer>();
            patternMap.putAll(frequencyMap);
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (patternMap.containsKey(c)) {
                    if (patternMap.get(c) > 0) {
                        patternMap.put(c, patternMap.get(c)-1);
                    } else {
                        filteredWords.remove(word);
                        break;
                    }
                }
            }
        }
        return filteredWords;
    }

    public static String generatePattern(String word, char guess) {
        String pattern = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (c == guess) {
                pattern += c;
            } else {
                pattern += '-';
            }
        }
        return pattern;
    }
}
