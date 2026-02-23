package org.example.sealed.classes.singlefile;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class FigureTest {
    @Test void sealedClassesWork() {
        final Figure f = new Figure();
        final Figure c = new Circle();
        final Figure sq = new Square();
        final Figure r = new Rectangle();
        final Figure fr = new FilledRectangle();

        Stream.of(f, c, sq, r, fr).map(Figure::getClass).forEach(System.out::println);
    }
}
