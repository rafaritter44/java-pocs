package org.example.patternmatching.recordpatterns;

class GenericRecordPatterns {
    static record Box<T>(T t) {}

    static void printBoxContents(final Box<String> b) {
        if (b instanceof Box<String>(final String s)) {
            IO.println("Box contains: " + s);
        }
    }
}
