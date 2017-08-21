package testClasses;

import java.util.Arrays;

public class Employee {
    private static long count = 0;
    long id;
    char grade;
    Person person;
    int salary;
    int[] tests;
    String[] phones;

    public Employee(char grade, Person person, int salary, int[] tests, String[] phones) {
        this.id  = ++count;
        this.grade = grade;
        this.person = person;
        this.salary = salary;
        this.tests = tests;
        this.phones = phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (id != employee.id) return false;
        if (grade != employee.grade) return false;
        if (salary != employee.salary) return false;
        if (person != null ? !person.equals(employee.person) : employee.person != null) return false;
        if (!Arrays.equals(tests, employee.tests)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(phones, employee.phones);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) grade;
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + salary;
        result = 31 * result + Arrays.hashCode(tests);
        result = 31 * result + Arrays.hashCode(phones);
        return result;
    }
}
