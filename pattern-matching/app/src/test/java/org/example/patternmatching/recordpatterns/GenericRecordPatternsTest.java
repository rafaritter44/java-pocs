package org.example.patternmatching.recordpatterns;

import org.junit.jupiter.api.Test;

class GenericRecordPatternsTest {
    @Test void genericRecordPatternsWork() {
        GenericRecordPatterns.printBoxContents(new GenericRecordPatterns.Box<>("str"));
    }
}
