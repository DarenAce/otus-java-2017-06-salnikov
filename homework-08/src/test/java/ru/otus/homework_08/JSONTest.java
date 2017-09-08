package ru.otus.homework_08;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import testClasses.Employee;
import testClasses.Person;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JSONTest {
    Gson gson = new Gson();

    @Test
    public void primitiveTypesToJSON() {
        Assert.assertEquals(gson.toJson(15), JSON.toJSON(15));
        Assert.assertEquals(gson.toJson('c'), JSON.toJSON('c'));
        Assert.assertEquals(gson.toJson(true), JSON.toJSON(true));
    }

    @Test
    public void stringToJSON() {
        Assert.assertEquals(gson.toJson("abc"), JSON.toJSON("abc"));
    }

    @Test
    public void arrayToJSON() {
        int[] ints = {7, 8, 45, -1};
        Assert.assertEquals(gson.toJson(ints), JSON.toJSON(ints));
        char[] chars = {'a', 'b', 'b', 'a'};
        Assert.assertEquals(gson.toJson(chars), JSON.toJSON(chars));
        int[][] doubleInts = {{1, 11}, {2, 22}};
        Assert.assertEquals(gson.toJson(doubleInts), JSON.toJSON(doubleInts));
        Person[] persons = {new Person("John", "Doe", 42),
                new Person("Jane", "Doe", 39)};
        Assert.assertEquals(gson.toJson(persons), JSON.toJSON(persons));
    }

    @Test
    public void listToJSON() {
        List<Integer> integers = new ArrayList<>();
        integers.add(3);
        integers.add(17);
        integers.add(42);
        Assert.assertEquals(gson.toJson(integers), JSON.toJSON(integers));
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", 42));
        persons.add(new Person("Jane", "Doe", 39));
        Assert.assertEquals(gson.toJson(persons), JSON.toJSON(persons));
    }

    @Test
    public void mapToJSON() {
        Map<Integer, String> intToString = new LinkedHashMap<>();
        intToString.put(2, "two");
        intToString.put(11, "eleven");
        intToString.put(42, "forty two");
        Assert.assertEquals(gson.toJson(intToString), JSON.toJSON(intToString));
        Map<Employee, Person> employeeToPerson = new LinkedHashMap<>();
        Person person1 = new Person("John", "Doe", 42);
        Person person2 = new Person("Jane", "Doe", 39);
        employeeToPerson.put(new Employee('A', person1, 17000),
                person1);
        employeeToPerson.put(new Employee('C', person2, 42000), person2);
        Assert.assertEquals(gson.toJson(employeeToPerson), JSON.toJSON(employeeToPerson));
    }

    @Test
    public void personToJSON() {
        Person person = new Person("John", "Doe", 42);
        Assert.assertEquals(gson.fromJson(JSON.toJSON(person), Person.class), person);
    }

    @Test
    public void employeeToJSON() {
        Person person = new Person("John", "Doe", 42);
        Employee employee = new Employee('A', person, 17_000);
        Assert.assertEquals(gson.fromJson(JSON.toJSON(employee), Employee.class), employee);
    }
}
