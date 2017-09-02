package ru.otus.homework_08;

import com.google.gson.*;
import testClasses.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Gson gson = new Gson();

        System.out.println("Test int");
        System.out.println(JSON.toJSON(15));
        System.out.println(gson.toJson(15) + "\n");

        System.out.println("Test char");
        System.out.println(JSON.toJSON('a'));
        System.out.println(gson.toJson('a') + "\n");

        System.out.println("Test boolean");
        System.out.println(JSON.toJSON(true));
        System.out.println(gson.toJson(true) + "\n");

        System.out.println("Test int[][]");
        int[][] array2d = new int[2][2];
        array2d[0][0] = 1;
        array2d[0][1] = 11;
        array2d[1][0] = 2;
        array2d[1][1] = 22;
        System.out.println(JSON.toJSON(array2d));
        System.out.println(gson.toJson(array2d) + "\n");

        System.out.println("Test char[]");
        char[] chars = {'a', 'b', 'b', 'a'};
        System.out.println(JSON.toJSON(chars));
        System.out.println(gson.toJson(chars) + "\n");

        System.out.println("Test String");
        System.out.println(JSON.toJSON("abc"));
        System.out.println(gson.toJson("abc") + "\n");

        System.out.println("Test Person");
        Person personA = new Person("John", "Doe", 42);
        System.out.println(JSON.toJSON(personA));
        System.out.println(gson.toJson(personA) + "\n");

        System.out.println("Test Employee");
        Employee employeeA = new Employee('A', personA, 17_000);
        System.out.println(JSON.toJSON(employeeA));
        System.out.println(gson.toJson(employeeA) + "\n");

        Person personB = new Person("Jane", "Doe", 39);
        Employee employeeB = new Employee('A', personB, 17_000);

        System.out.println("Test Map<Integer, String>");
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "a");
        map.put(2, "b");
        map.put(3, "b");
        map.put(4, "a");
        System.out.println(JSON.toJSON(map));
        System.out.println(gson.toJson(map) + "\n");

        System.out.println("Test List<Employee>");
        List<Employee> employees = new ArrayList<>();
        employees.add(employeeA);
        employees.add(employeeB);
        System.out.println(JSON.toJSON(employees));
        System.out.println(gson.toJson(employees));
    }
}
