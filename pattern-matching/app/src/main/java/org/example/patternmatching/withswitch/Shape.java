package org.example.patternmatching.withswitch;

interface Shape {
    static double getPerimeterUsingExpression(final Shape s) throws IllegalArgumentException {
        return switch (s) {
            case final Rectangle r -> 2 * r.length() + 2 * r.width();
            case final Circle c -> 2 * c.radius() * Math.PI;
            default -> throw new IllegalArgumentException("Unrecognized shape");
        };
    }
    static double getPerimeterUsingStatement(Shape s) throws IllegalArgumentException {
        switch (s) {
            case final Rectangle r: return 2 * r.length() + 2 * r.width();
            case final Circle c: return 2 * c.radius() * Math.PI;
            default: throw new IllegalArgumentException("Unrecognized shape");
        }
    }
}