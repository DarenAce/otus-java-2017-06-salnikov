package ru.otus.homework_05;

import ru.otus.test_framework.TestFramework;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Run test passing array of test classes:");
        TestFramework.testClasses(MainTest.class);
        System.out.println("\nRun test passing package name:");
        TestFramework.testPackage("ru.otus.homework_05");
    }
}
