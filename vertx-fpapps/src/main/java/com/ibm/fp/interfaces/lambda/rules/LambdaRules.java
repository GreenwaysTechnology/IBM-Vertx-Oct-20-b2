package com.ibm.fp.interfaces.lambda.rules;

@FunctionalInterface
interface Greeter {
    static void doSomething() {
        System.out.println("Do something");
    }

    //only one abstract
    void sayGreet();

    // void sayHello();
    default void sayHai() {
        System.out.println("Hai");
    }
}

public class LambdaRules {
    public static void main(String[] args) {
        Greeter greeter = null;
        //lambda expression
        greeter = () -> {
            System.out.println("Lambda  class version");
        };
        greeter.sayGreet();
        greeter.sayHai();
        Greeter.doSomething();

    }
}
