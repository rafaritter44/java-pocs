package org.example.patternmatching.recordpatterns;

import org.junit.jupiter.api.Test;

import static org.example.patternmatching.recordpatterns.NestedRecordPatterns.*;

class NestedRecordPatternsTest {
    @Test void nestedRecordPatternsWork() {
        var upperLeft = new ColoredPoint(new Point(1.0, 4.0), Color.RED);
        var lowerRight = new ColoredPoint(new Point(3.0, 1.0), Color.GREEN);
        var r = new ColoredRectangle(upperLeft, lowerRight);
        printXCoordOfUpperLeftPointWithPatterns(r);
    }
    @Test void nestedParameterizedRecordPatternsWork() {
        var b = new Box<>(new Box<>("str"));
        nestedBox(b);
    }
}
