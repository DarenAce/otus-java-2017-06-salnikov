package ru.otus.homework_08;

import testClasses.Employee;
import testClasses.Person;

public class Main {
    public static void main(String[] args) {
        Person person = new Person("John", "Doe", 42);
        System.out.println(JSON.toJSON(person));
        int[] tests = {1, 2, 3};
        String[] phones = {"111", "222", "333"};
        Employee employee = new Employee('A', person, 17_000, tests, phones);
        System.out.println(JSON.toJSON(employee));
    }
}
