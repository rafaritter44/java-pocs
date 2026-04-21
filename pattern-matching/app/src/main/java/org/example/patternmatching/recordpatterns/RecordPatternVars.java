package org.example.patternmatching.recordpatterns;

class RecordPatternVars {
    static record Point(double x, double y) {}

    static void printAngleFromXAxis(final Object obj) {
        if (obj instanceof Point(final var x, final var y)) {
            IO.println(Math.toDegrees(Math.atan2(y, x)));
        }
    }
}
