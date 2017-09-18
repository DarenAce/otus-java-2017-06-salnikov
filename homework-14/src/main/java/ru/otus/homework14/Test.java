package ru.otus.homework14;

public class Test {
    public static void main(String[] args) {
//        task1();
//        task2();
        task4();
    }

    private static void task1() {
        int count = 0;
        int bound = 1013;
        for (int i = 1; i <= bound; i++) {
            boolean hasThree = false;
            int number = i;
            while (number != 0 && !hasThree) {
                if (number % 10 == 3) {
                    hasThree = true;
                }
                number /= 10;
            }
            if (hasThree) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static void task2() {
        String in = "гтёц т еоён рспдсбннйтуб";

        for (int i = 1; i < 33; i++) {
            StringBuilder out = new StringBuilder();
            for (char c : in.toCharArray()) {
                if (getLetterNumber(c) == -1) {
                    out.append(c);
                } else {
                    out.append(getLetterByNumber((getLetterNumber(c) + i - 1) % 33 + 1));
                }
            }
            System.out.println(out.toString());
        }
    }

    private static void task4(){
        System.out.println((!(true | false | true) ^ (true & false)));
    }

    private static int getLetterNumber(char c) {
        final int base = 1071;
        if (c >= 'а' && c <= 'е') {
            return c - base;
        }
        if (c >= 'ж' && c <= 'я') {
            return c - base + 1;
        }
        if (c == 'ё') {
            return 7;
        }
            return -1;
    }

    private static char getLetterByNumber(int n) {
        if (n < 1 || n > 33) {
            throw new IllegalArgumentException(n + " is out of borders");
        }
        char base = 1071;
        if (n <= 6) {
            return (char) (base + n);
        }
        if (n == 7) {
            return 'ё';
        }
        return (char) (base + n - 1);
    }
}
