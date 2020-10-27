package com.ibm.fp.interfaces.lambda.rules;

@FunctionalInterface
interface Welcome {
    void sayWelcome();
}

//function with parameters and args
@FunctionalInterface
interface Name {
    //name is arg. and its type is string
    void setName(String name);
}

@FunctionalInterface
interface Adder {
    void add(int a, int b);
}

@FunctionalInterface
interface Multipler {
    int multiply(int a, int b);
}

@FunctionalInterface
interface Tester {
    boolean isValid(int number);
}

public class LambdaCodeRefactoring {
    public static void main(String[] args) {
        Welcome welcome = null;
        //way 1;
        welcome = () -> {
            System.out.println("say Welcome");
        };
        welcome.sayWelcome();
        //way 2; if function body has one line, remove {}
        welcome = () -> System.out.println("say Welcome");
        welcome.sayWelcome();
        //////////////////////////////////////////////////////////////////////////////////////
        Name myname = null;
        myname = (String name) -> System.out.println(name);
        //"subramanian" is parameter
        myname.setName("Subramanian");
        //code refactoring for parameters : type inference; type of variable is understood based on interface type.
        myname = (name) -> System.out.println(name);
        //"subramanian" is parameter
        myname.setName("Subramanian");
        //if arg is single variable you can remove ()
        myname = name -> System.out.println(name);
        //"subramanian" is parameter
        myname.setName("Subramanian");
        ////////////////////////////////////////////////////////////////////////
        Adder adder = null;
        adder = (int a, int b) -> {
            int c = a + b;
            System.out.println("The result is " + c);
        };
        adder.add(10, 10);
        adder = (x, y) -> {
            int c = x + y;
            System.out.println("The result is " + c);
        };
        adder.add(10, 10);
        //return value
        Multipler multipler = null;
        multipler = (a, b) -> {
            return a * b;
        };
        System.out.println(multipler.multiply(10, 10));
        //code refactoring for return statement. if only return statement; remove return and {}
        multipler = (a, b) -> a * b;
        System.out.println(multipler.multiply(10, 10));
        /////////////////////////////////////////////////////////////////////////////////////
        Tester tester = a -> a > 10;
        System.out.println(tester.isValid(1));
        System.out.println(tester.isValid(100));


    }
}
