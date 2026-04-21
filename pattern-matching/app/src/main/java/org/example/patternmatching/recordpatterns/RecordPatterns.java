package org.example.patternmatching.recordpatterns;

class RecordPatterns {
    static record Point(double x, double y) {}

    static void printAngleFromXAxis(final Object obj) {
        if (obj instanceof Point(double x, double y)) {
            IO.println(Math.toDegrees(Math.atan2(y, x)));
        }
    }
}
