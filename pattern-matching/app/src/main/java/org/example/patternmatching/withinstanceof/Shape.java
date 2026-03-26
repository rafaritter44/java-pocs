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
    public static boolean bigEnoughRect(final Shape s) {
        if (!(s instanceof final Rectangle r)) {
            // You cannot use the pattern variable r here because
            // the predicate s instanceof Rectangle is false.
            return false;
        }
        // You can use r here.
        return r.length() > 5; 
    }
    public static boolean nonEmptyRect(final Shape s) {
        if (s instanceof final Rectangle r && r.length() > 5) {
            return true;
        } else {
            return false;
        }
    }
}
