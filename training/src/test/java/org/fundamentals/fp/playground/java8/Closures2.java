package org.fundamentals.fp.playground.java8;

import java.util.function.Consumer;

public class Closures2 {

    static class myInt {
        int i = 0;
    }

    public static class AreLambdasClosures2 {
        public Consumer<Integer> make_fun2() {
            myInt n = new myInt();
            return arg -> {
                System.out.println(n.i);
                n.i += arg;
                System.out.println(n.i);
            };
        }
    }

    public static void main(String[] args) {
        new AreLambdasClosures2().make_fun2().accept(2);
    }

}
