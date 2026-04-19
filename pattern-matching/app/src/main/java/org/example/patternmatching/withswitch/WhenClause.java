package org.example.patternmatching.withswitch;

class WhenClause {
    static void test(final Object obj) {
        switch (obj) {
            case final String s when s.length() == 1 -> IO.println("Short: " + s);
            case final String s                      -> IO.println(s);
            default                                  -> IO.println("Not a string");
        }
    }

    record R(int x) {}
    static void testSwitch(final R r) {
        switch(r) {
            case R(int x) when x >= 5 -> IO.println(x + " => 5");
            default -> IO.println(r.x + " < 5");
        }
    }
}
