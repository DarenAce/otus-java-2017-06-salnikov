package ru.otus.homework_08;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import testClasses.Employee;
import testClasses.Person;

public class JSONTest {
    @Test
    public void toJSONPerson() {
        Person person = new Person("John", "Doe", 42);
        Gson gson = new Gson();
        Assert.assertEquals(gson.fromJson(JSON.toJSON(person), Employee.class), person);
    }

    @Test
    public void toJSONEmployee() {
        Person person = new Person("John", "Doe", 42);
        int[] tests = {1, 2, 3};
        String[] phones = {"111", "222", "333"};
        Employee employee = new Employee('A', person, 17_000, tests, phones);
        Gson gson = new Gson();
        Assert.assertEquals(gson.fromJson(JSON.toJSON(employee), Employee.class), employee);
    }
}
