package org.example.patternmatching.withinstanceof;

public class Rectangle implements Shape {
    private final double length;
    private final double width;

    public Rectangle(final double length, final double width) {
        this.length = length;
        this.width = width;
    }

    public double length() {
        return length;
    }
    public double width() {
        return width;
    }
}
