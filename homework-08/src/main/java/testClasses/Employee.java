package testClasses;

public class Employee {
    private static long count = 0;
    long id;
    char grade;
    Person person;
    int salary;

    public Employee(char grade, Person person, int salary) {
        this.id = ++count;
        this.grade = grade;
        this.person = person;
        this.salary = salary;
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
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) grade;
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + salary;
        return result;
    }
}
