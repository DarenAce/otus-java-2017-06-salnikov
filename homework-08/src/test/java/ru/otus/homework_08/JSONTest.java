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
import java.util.logging.Level;

public class JSONTest {
    Gson gson = new Gson();

    @Test
    public void primitiveTypesToJSON() {
        Assert.assertEquals(JSON.toJSON(15), gson.toJson(15));
        Assert.assertEquals(JSON.toJSON('c'), gson.toJson('c'));
        Assert.assertEquals(JSON.toJSON(true), gson.toJson(true));
    }

    @Test
    public void stringToJSON() {
        Assert.assertEquals(JSON.toJSON("abc"), gson.toJson("abc"));
    }

    @Test
    public void arrayToJSON() {
        int[] ints = {7, 8, 45, -1};
        Assert.assertEquals(JSON.toJSON(ints), gson.toJson(ints));
        char[] chars = {'a', 'b', 'b', 'a'};
        Assert.assertEquals(JSON.toJSON(chars), gson.toJson(chars));
        int[][] doubleInts = {{1, 11}, {2, 22}};
        Assert.assertEquals(JSON.toJSON(doubleInts), gson.toJson(doubleInts));
        Person[] persons = {new Person("John", "Doe", 42),
                new Person("Jane", "Doe", 39)};
        Assert.assertEquals(JSON.toJSON(persons), gson.toJson(persons));
    }

    @Test
    public void listToJSON() {
        List<Integer> integers = new ArrayList<>();
        integers.add(3);
        integers.add(17);
        integers.add(42);
        Assert.assertEquals(JSON.toJSON(integers), gson.toJson(integers));
        List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", 42));
        persons.add(new Person("Jane", "Doe", 39));
        Assert.assertEquals(JSON.toJSON(persons), gson.toJson(persons));
    }

    @Test
    public void mapToJSON() {
        Map<Integer, String> intToString = new LinkedHashMap<>();
        intToString.put(2, "two");
        intToString.put(11, "eleven");
        intToString.put(42, "forty two");
        Assert.assertEquals(JSON.toJSON(intToString), gson.toJson(intToString));
        Map<Employee, Person> employeeToPerson = new LinkedHashMap<>();
        Person person1 = new Person("John", "Doe", 42);
        Person person2 = new Person("Jane", "Doe", 39);
        employeeToPerson.put(new Employee('A', person1, 17000),
                person1);
        employeeToPerson.put(new Employee('C', person2, 42000), person2);
        Assert.assertEquals(JSON.toJSON(employeeToPerson), gson.toJson(employeeToPerson));
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
