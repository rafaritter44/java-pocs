package org.example.patternmatching.withswitch;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class NullCaseLabelTest {
    @Test void nullCaseLabelWorks() {
        Stream.of("str", 123, null).forEach(NullCaseLabel::test);
    }
    @Test void nullCaseLabelCanBeCombinedWithDefaultCaseLabel() {
        Stream.of("str", 123, null).forEach(NullCaseLabel::testStringOrNull);
    }
}
