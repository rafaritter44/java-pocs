package org.example.patternmatching.withinstanceof;

public class Circle implements Shape {
    private final double radius;

    public Circle(final double radius) {
        this.radius = radius;
    }

    public double radius() {
        return radius;
    }
}
