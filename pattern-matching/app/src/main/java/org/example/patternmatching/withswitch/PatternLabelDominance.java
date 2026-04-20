package org.example.patternmatching.withswitch;

class PatternLabelDominance {
    static void testInteger(final Integer value) {
        switch(value) {
            case 1, 2 -> IO.println("Value is 1 or 2");
            case -1 -> IO.println("Value is -1");
            case final Integer i when i > 0 -> IO.println("Positive integer");
            case final Integer _ -> IO.println("An integer");
        }
    }
}
