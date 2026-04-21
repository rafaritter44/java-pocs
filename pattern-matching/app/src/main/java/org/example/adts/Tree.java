package org.example.adts;

sealed interface Tree {
    record Empty() implements Tree {}
    record Node(int value, Tree left, Tree right) implements Tree {}

    static int sum(final Tree root) {
        return switch (root) {
            case Empty _ -> 0;
            case Node(var v, var l, var r) -> v + sum(l) + sum(r);
        };
    }
}
