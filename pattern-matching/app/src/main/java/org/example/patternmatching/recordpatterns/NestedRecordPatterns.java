package org.example.patternmatching.recordpatterns;

class NestedRecordPatterns {
    static enum Color { RED, GREEN, BLUE }
    static record Point(double x, double y) {}
    static record ColoredPoint(Point p, Color c) {}
    static record ColoredRectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) {}

    static void printXCoordOfUpperLeftPointWithPatterns(final ColoredRectangle r) {
        if (r instanceof ColoredRectangle(
            ColoredPoint(Point(var x, var y), var upperLeftColor),
                         var lowerRightCorner)) {
            IO.println("Upper-left corner: " + x);
        }
    }
}
