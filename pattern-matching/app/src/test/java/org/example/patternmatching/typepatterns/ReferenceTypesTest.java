package org.example.patternmatching.typepatterns;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class ReferenceTypesTest {
    @Test void typePatternsWithReferenceTypesWork() {
        Stream.of(
            null,
            "str",
            ReferenceTypes.Color.BLUE,
            new ReferenceTypes.Point(1, 2),
            new int[] {1, 2, 3},
            123.456
        ).forEach(ReferenceTypes::typeTester);
    }
}
