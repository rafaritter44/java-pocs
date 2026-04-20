package org.example.patternmatching.withswitch;

class NullCaseLabel {
    static void test(final Object obj) {
        switch (obj) {
            case String _ -> IO.println("String");
            case null     -> IO.println("null!");
            default       -> IO.println("Something else");
        }
    }
}
