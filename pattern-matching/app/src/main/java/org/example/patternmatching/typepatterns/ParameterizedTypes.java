package org.example.patternmatching.typepatterns;

class ParameterizedTypes {
    static class Box<T> {
        private final T t;
        Box(final T t) { this.t = t; }
        T get() { return t; }
    }
    static class Shoebox<T> extends Box<T> {
        Shoebox(final T t) { super(t); }
    }

    static void test() {
        final Shoebox<String> sb = new Shoebox<>("a pair of new shoes");
        if (sb instanceof Box<String> s) {
            IO.println("Box<String> contains: " + s.get());
        }
        if (sb instanceof Shoebox<String> s) {
            IO.println("Shoebox<String> contains: " + s.get());
        }
        if (sb instanceof Shoebox<?> s) {
            IO.println("Shoebox<?> contains: " + s.get());
        }
        // if (sb instanceof Shoebox<Object> s) {
            // error: incompatible types: Shoebox<String> cannot be converted to Shoebox<Object>
            // IO.println("Shoebox<Object> contains: " + sb.get());
        // }
    }
}
