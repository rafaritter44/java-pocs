package org.example.sealed.classes.multifile;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class ShapeTest {
    @Test void sealedClassesWork() {
        final Shape s = new Shape();
        final Shape c = new Circle();
        final Shape sq = new Square();
        final Shape r = new Rectangle();
        final Shape fr = new FilledRectangle();

        Stream.of(s, c, sq, r, fr).map(Shape::getClass).forEach(System.out::println);
    }
}
