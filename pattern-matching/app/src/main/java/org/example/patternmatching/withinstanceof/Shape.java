package org.example.patternmatching.withinstanceof;

public interface Shape {
    public static double getPerimeter(final Shape s) throws IllegalArgumentException {
        if (s instanceof final Rectangle r) {
            return 2 * r.length() + 2 * r.width();
        } else if (s instanceof final Circle c) {
            return 2 * c.radius() * Math.PI;
        } else {
            throw new IllegalArgumentException("Unrecognized shape");
        }
    }
}
