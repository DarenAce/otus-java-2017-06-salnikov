package ru.otus.test_framework;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import ru.otus.test_framework.annotations.After;
import ru.otus.test_framework.annotations.Before;
import ru.otus.test_framework.annotations.Test;

import java.lang.reflect.Method;
import java.util.*;

public final class TestFramework {
    private TestFramework() {
    }

    public static void testClasses(Class<?>... clazzes) {
        for (Class<?> clazz : clazzes) {
            HashSet<Method> beforeMethods = new HashSet<>();
            HashSet<Method> testMethods = new HashSet<>();
            HashSet<Method> afterMethods = new HashSet<>();
            for (Method method : clazz.getDeclaredMethods()) {
                Before before = method.getAnnotation(Before.class);
                if (before != null) {
                    beforeMethods.add(method);
                }
                Test test = method.getAnnotation(Test.class);
                if (test != null) {
                    testMethods.add(method);
                }
                After after = method.getAnnotation(After.class);
                if (after != null) {
                    afterMethods.add(method);
                }
            }

            Object testClassObject = ReflectionHelper.instantiate(clazz);

            beforeMethods.forEach(method -> {
                ReflectionHelper.callMethod(testClassObject, method.getName());
            });

            testMethods.forEach(method -> {
                ReflectionHelper.callMethod(testClassObject, method.getName());
            });

            afterMethods.forEach(method -> {
                ReflectionHelper.callMethod(testClassObject, method.getName());
            });
        }
    }

    public static void testPackage(String packageName) {
        ArrayList<Class<?>> packageClasses = getClassesForPackage(packageName);
        testClasses(packageClasses.toArray(new Class<?>[0]));
    }

    private static ArrayList<Class<?>> getClassesForPackage(String packageName) {
        List<ClassLoader> classLoadersList = new LinkedList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));
        Set<Class<?>> packageClasses = reflections.getSubTypesOf(Object.class);
        return new ArrayList<>(packageClasses);
    }
}
