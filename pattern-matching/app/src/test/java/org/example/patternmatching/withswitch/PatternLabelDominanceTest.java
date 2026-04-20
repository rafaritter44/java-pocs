package org.example.patternmatching.withswitch;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class PatternLabelDominanceTest {
    @Test void patternLabelsCanBeMixedWithConstantLabels() {
        IntStream.of(1, 2, 3, -1, 0).forEach(PatternLabelDominance::testInteger);
    }
}
