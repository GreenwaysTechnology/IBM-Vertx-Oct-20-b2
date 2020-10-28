package com.ibm.fp.lab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

interface CustomerRepository {
    public void findAll(Consumer<List<Customer>> handler);
}

class Customer {
    private int id;
    private String name;

    public Customer() {

    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

class CustomerRepositoryImpl implements CustomerRepository {
    List<Customer> customerList;

    public CustomerRepositoryImpl() {
        customerList = new ArrayList<>();
        customerList.add(new Customer(1, "a"));
        customerList.add(new Customer(2, "b"));
    }

    //apis findAll,findById...
    public void findAll(Consumer<List<Customer>> handler) {
        handler.accept(customerList);
    }

}

public class FunctionalApp {

    public static void printAllCustomers(List<Customer> customers) {
        System.out.println(customers);
    }

    public static void main(String[] args) {
        CustomerRepository repository = new CustomerRepositoryImpl();
        //function as a parameter to caputure result;
        repository.findAll(response -> System.out.println(response));
        repository.findAll(System.out::println);
        repository.findAll(FunctionalApp::printAllCustomers);

    }
}
