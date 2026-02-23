package org.example.sealed.classes.multifile;

public sealed class Rectangle extends Shape permits FilledRectangle {
    public double length, width;
}
