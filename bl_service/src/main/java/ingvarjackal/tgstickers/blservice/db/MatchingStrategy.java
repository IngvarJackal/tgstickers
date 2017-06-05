package ingvarjackal.tgstickers.blservice.db;

import info.debatty.java.stringsimilarity.CharacterSubstitutionInterface;
import info.debatty.java.stringsimilarity.WeightedLevenshtein;

import java.util.Locale;

public class MatchingStrategy {
    private static final double NEARBY_LETTERS = 0.5;
    private static final double LEVENSTEIN_THRESHOLD = 0.4; // errors per letter in original tag

    private final static WeightedLevenshtein distance = new WeightedLevenshtein(
                new CharacterSubstitutionInterface() {
        private double internalCost(char c1, char c2) {
            if (c1 == 'q' && c2 == 'w') return NEARBY_LETTERS;
            if (c1 == 'q' && c2 == 'a') return NEARBY_LETTERS;
            if (c1 == 'w' && c2 == 'e') return NEARBY_LETTERS;
            if (c1 == 'w' && c2 == 's') return NEARBY_LETTERS;
            if (c1 == 'w' && c2 == 'a') return NEARBY_LETTERS;
            if (c1 == 'e' && c2 == 'r') return NEARBY_LETTERS;
            if (c1 == 'e' && c2 == 'd') return NEARBY_LETTERS;
            if (c1 == 'e' && c2 == 's') return NEARBY_LETTERS;
            if (c1 == 'r' && c2 == 't') return NEARBY_LETTERS;
            if (c1 == 'r' && c2 == 'f') return NEARBY_LETTERS;
            if (c1 == 'r' && c2 == 'd') return NEARBY_LETTERS;
            if (c1 == 't' && c2 == 'y') return NEARBY_LETTERS;
            if (c1 == 't' && c2 == 'g') return NEARBY_LETTERS;
            if (c1 == 't' && c2 == 'f') return NEARBY_LETTERS;
            if (c1 == 'y' && c2 == 'u') return NEARBY_LETTERS;
            if (c1 == 'y' && c2 == 'h') return NEARBY_LETTERS;
            if (c1 == 'y' && c2 == 'g') return NEARBY_LETTERS;
            if (c1 == 'u' && c2 == 'i') return NEARBY_LETTERS;
            if (c1 == 'u' && c2 == 'j') return NEARBY_LETTERS;
            if (c1 == 'u' && c2 == 'h') return NEARBY_LETTERS;
            if (c1 == 'i' && c2 == 'o') return NEARBY_LETTERS;
            if (c1 == 'i' && c2 == 'k') return NEARBY_LETTERS;
            if (c1 == 'i' && c2 == 'j') return NEARBY_LETTERS;
            if (c1 == 'o' && c2 == 'p') return NEARBY_LETTERS;
            if (c1 == 'o' && c2 == 'l') return NEARBY_LETTERS;
            if (c1 == 'o' && c2 == 'k') return NEARBY_LETTERS;
            if (c1 == 'p' && c2 == 'l') return NEARBY_LETTERS;
            if (c1 == 'a' && c2 == 's') return NEARBY_LETTERS;
            if (c1 == 'a' && c2 == 'z') return NEARBY_LETTERS;
            if (c1 == 's' && c2 == 'd') return NEARBY_LETTERS;
            if (c1 == 's' && c2 == 'x') return NEARBY_LETTERS;
            if (c1 == 's' && c2 == 'z') return NEARBY_LETTERS;
            if (c1 == 'd' && c2 == 'f') return NEARBY_LETTERS;
            if (c1 == 'd' && c2 == 'c') return NEARBY_LETTERS;
            if (c1 == 'd' && c2 == 'x') return NEARBY_LETTERS;
            if (c1 == 'f' && c2 == 'g') return NEARBY_LETTERS;
            if (c1 == 'f' && c2 == 'v') return NEARBY_LETTERS;
            if (c1 == 'f' && c2 == 'c') return NEARBY_LETTERS;
            if (c1 == 'g' && c2 == 'h') return NEARBY_LETTERS;
            if (c1 == 'g' && c2 == 'b') return NEARBY_LETTERS;
            if (c1 == 'g' && c2 == 'v') return NEARBY_LETTERS;
            if (c1 == 'h' && c2 == 'j') return NEARBY_LETTERS;
            if (c1 == 'h' && c2 == 'n') return NEARBY_LETTERS;
            if (c1 == 'h' && c2 == 'b') return NEARBY_LETTERS;
            if (c1 == 'j' && c2 == 'k') return NEARBY_LETTERS;
            if (c1 == 'j' && c2 == 'm') return NEARBY_LETTERS;
            if (c1 == 'j' && c2 == 'n') return NEARBY_LETTERS;
            if (c1 == 'k' && c2 == 'l') return NEARBY_LETTERS;
            if (c1 == 'k' && c2 == 'm') return NEARBY_LETTERS;
            if (c1 == 'z' && c2 == 'x') return NEARBY_LETTERS;
            if (c1 == 'x' && c2 == 'c') return NEARBY_LETTERS;
            if (c1 == 'c' && c2 == 'v') return NEARBY_LETTERS;
            if (c1 == 'v' && c2 == 'b') return NEARBY_LETTERS;
            if (c1 == 'b' && c2 == 'n') return NEARBY_LETTERS;
            if (c1 == 'n' && c2 == 'm') return NEARBY_LETTERS;
            return 1.5;
        }
        @Override
        public double cost(char c1, char c2) {
            return Math.min(internalCost(c1, c2), internalCost(c2, c1));
        }
    });


    public static boolean matches(String searchTag, String origTag) {
        if (searchTag == null || origTag == null) {
            return false;
        }

        origTag = origTag.substring(0, Math.max(0, Math.min(origTag.length(), searchTag.length())-1));

        if (searchTag.equalsIgnoreCase(origTag)) {
            return true;
        }

        if (distance.distance(searchTag.toLowerCase(Locale.ENGLISH), origTag.toLowerCase(Locale.ENGLISH)) <= LEVENSTEIN_THRESHOLD*searchTag.length()) {
            return true;
        }

        return false;
    }
}
