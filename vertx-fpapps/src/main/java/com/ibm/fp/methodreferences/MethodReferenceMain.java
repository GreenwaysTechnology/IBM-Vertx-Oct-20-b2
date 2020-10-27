package com.ibm.fp.methodreferences;

@FunctionalInterface
interface Printer {
    void print(String message);
}

@FunctionalInterface
interface UpperCase {
    String convertToUpper(String message);
}

class MicroTask {
    public static void startstaticMicrotask() {
        System.out.println(Thread.currentThread().getName());
    }

    public void startMicrotask() {
        System.out.println(Thread.currentThread().getName());
    }
}

class Task {
    private void startMicrotask() {
        System.out.println(Thread.currentThread().getName());
    }

    public void startTask() {
        Thread thread = null;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        });
        thread.start();
        //using lambda
        Runnable runnable = null;
        runnable = () -> System.out.println(Thread.currentThread().getName());
        thread = new Thread(runnable);
        thread.start();
        thread = new Thread(() -> System.out.println(Thread.currentThread().getName()));
        thread.start();
        //isloate thread logic in a separate method.
        thread = new Thread(() -> this.startMicrotask());
        thread.start();
        //remove lambda syntax; : method reference syntax.
        thread = new Thread(this::startMicrotask);
        thread.start();
        MicroTask microTask = new MicroTask();
        thread = new Thread(() -> microTask.startMicrotask());
        thread.start();
        thread = new Thread(() -> new MicroTask().startMicrotask());
        thread.start();
        thread = new Thread(microTask::startMicrotask);
        thread.start();
        thread = new Thread(new MicroTask()::startMicrotask);
        thread.start();
        thread = new Thread(MicroTask::startstaticMicrotask);
        thread.start();
    }
}


public class MethodReferenceMain {
    public static void main(String[] args) {
        Task task = new Task();
        task.startTask();

        Printer printer = null;
        printer = name -> System.out.println(name);
        printer.print("Subramanian");
        //method references:
        printer = System.out::println;
        printer.print("Subramanian");
        UpperCase upperCase = null;
        upperCase = name -> name.toUpperCase();
        System.out.println(upperCase.convertToUpper("hello"));
        upperCase = String::toUpperCase;
        System.out.println(upperCase.convertToUpper("hello"));
    }
}
