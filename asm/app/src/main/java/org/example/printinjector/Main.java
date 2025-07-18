package org.example.printinjector;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        PrintInjector.injectPrint();

        URL classDir = Path.of("build", "asm-out").toUri().toURL();
        try (URLClassLoader loader = new URLClassLoader(new URL[]{classDir}, null)) {
            Class<?> greeterClass = loader.loadClass("org.example.printinjector.Greeter");

            Object greeter = greeterClass.getDeclaredConstructor().newInstance();
            Method greet = greeterClass.getMethod("greet");
            greet.invoke(greeter);
        }
    }
}