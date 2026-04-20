package org.example.patternmatching.withswitch;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class NullCaseLabelTest {
    @Test void nullCaseLabelWorks() {
        Stream.of("str", 123, null).forEach(NullCaseLabel::test);
    }
    @Test void nullCaseLabelCanBeCombinedWithDefaultCaseLabel() {
        Stream.of("str", 123, null).forEach(NullCaseLabel::testStringOrNull);
    }
    @Test void npeIsThrownWhenNullCaseLabelIsMissing() {
        NullCaseLabel.testNullCaseLabelMissing("str");
        assertThrows(NullPointerException.class, () -> NullCaseLabel.testNullCaseLabelMissing(null));
    }
}
