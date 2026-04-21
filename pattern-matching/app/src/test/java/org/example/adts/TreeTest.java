package org.example.adts;

import org.junit.jupiter.api.Test;

class TreeTest {
    @Test void algebraicDataTypesWork() {
        var tree = new Tree.Node(
            10,
            new Tree.Node(20, new Tree.Empty(), new Tree.Empty()),
            new Tree.Empty()
        );
        var sum = Tree.sum(tree);
        IO.println("Sum: " + sum);
    }
}
