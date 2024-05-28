package info.jab.fp.concepts;

import java.util.function.Supplier;

public class Closures3 {

    void fn() {
        final int myVar = 42;
        Supplier<Integer> lambdaFun = () -> myVar; // error
        //myVar++;
        System.out.println(lambdaFun.get());
    }

    public static void main(String[] args) {

        new Closures3().fn();
    }

}
