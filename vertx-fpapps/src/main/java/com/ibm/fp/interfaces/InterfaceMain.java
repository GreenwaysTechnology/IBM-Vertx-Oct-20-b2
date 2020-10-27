package com.ibm.fp.interfaces;
//general interface uses

interface Greeter {
    //abstract methods
    void sayGreet();
}

//class implements interface
class GreeterImplV1 implements Greeter {
    @Override
    public void sayGreet() {
        System.out.println("Greet from Greeter Impl-V1");
    }
}
class GreeterImplV2 implements Greeter {
    @Override
    public void sayGreet() {
        System.out.println("Greet from Greeter Impl-V2");
    }
}
public class InterfaceMain {
    public static void main(String[] args) {
        Greeter greeter = null;
        greeter = new GreeterImplV1();
        greeter.sayGreet();
        greeter = new GreeterImplV2();
        greeter.sayGreet();
        //anonmous inner class.
        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("Greet from Greeter Impl-V3");
            }
        };
        greeter.sayGreet();
        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("Greet from Greeter Impl-V4");
            }
        };
        greeter.sayGreet();
        greeter = new Greeter() {
            @Override
            public void sayGreet() {
                System.out.println("Greet from Greeter Impl-V5");
            }
        };
        greeter.sayGreet();


    }
}
