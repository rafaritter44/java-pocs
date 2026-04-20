package org.example.patternmatching.typepatterns;

import java.util.Arrays;

class ReferenceTypes {
    static record Point(int x, int y) {}
    static enum Color { RED, GREEN, BLUE; }

    static void typeTester(final Object obj) {
        switch (obj) {
            case null     -> IO.println("null");
            case String s -> IO.println("String: %s".formatted(s));
            case Color c  -> IO.println("Color: %s".formatted(c));
            case Point p  -> IO.println("Point: %s".formatted(p));
            case int[] ia -> IO.println("Int array: %s".formatted(Arrays.toString(ia)));
            default       -> IO.println("Something else");
        }
    }
}
