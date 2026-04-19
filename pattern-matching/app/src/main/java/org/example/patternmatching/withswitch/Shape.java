package org.example.patternmatching.withswitch;

interface Shape {
    static double getPerimeter(Shape s) throws IllegalArgumentException {
        return switch (s) {
            case Rectangle r -> 2 * r.length() + 2 * r.width();
            case Circle c -> 2 * c.radius() * Math.PI;
            default -> throw new IllegalArgumentException("Unrecognized shape");
        };
    }
}