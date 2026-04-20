package org.example.patternmatching.withswitch;

class NullCaseLabel {
    static void test(final Object obj) {
        switch (obj) {
            case String _ -> IO.println("String");
            case null     -> IO.println("null!");
            default       -> IO.println("Something else");
        }
    }
    static void testStringOrNull(final Object obj) {
        switch (obj) {
            // error: invalid case label combination
            // case null, String s -> IO.println("String" + s);
            case String s      -> IO.println("String: " + s);
            case null, default -> IO.println("null or not a string");
        }
    }
    static void testNullCaseLabelMissing(final String s) {
        switch (s) {
            case Object _ -> IO.println("This doesn't match null");
            // No null label; NullPointerException is thrown if s is null
        }
    }
}
