package ru.otus.homework_05;

import ru.otus.test_framework.annotations.*;

public class MainTest {
    @Before(name = "Redefined name")
    public void runBeforeAllTests() {
        System.out.println("This is the method marked with @Before annotation.");
    }

    @Test
    public void runTestA() {
        System.out.println("This is testA() method marked with @Test.");
    }

    @Test
    public void runTestB() {
        System.out.println("This is testB() method marked with @Test.");
    }

    @After
    public void runAfterAllTests() {
        System.out.println("This is the method marked with @After annotation.");
    }
}
