package org.example.patternmatching.withswitch;

class WhenClause {
    static void test(final Object obj) {
        switch (obj) {
            case String s when s.length() == 1 -> IO.println("Short: " + s);
            case String s                      -> IO.println(s);
            default                            -> IO.println("Not a string");
        }
    }
}
