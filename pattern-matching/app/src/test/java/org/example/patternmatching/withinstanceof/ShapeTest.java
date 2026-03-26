package org.example.patternmatching.withinstanceof;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class ShapeTest {
    @Test void patternMatchingWithInstanceOfWorks() {
        final Shape r = new Rectangle(10.0, 5.0);
        final Shape c = new Circle(2.0);
        Stream.of(r, c).map(Shape::getPerimeter).forEach(IO::println);
        Stream.of(r, c).map(Shape::bigEnoughRect).forEach(IO::println);
    }
}
