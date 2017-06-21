package ru.otus.homework_03;

import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        MyArrayList<Integer> myArrayList1 = new MyArrayList<>();
        myArrayList1.add(1);
        myArrayList1.add(3);
        myArrayList1.add(1,2);

        System.out.print("myArrayList1: { ");
        myArrayList1.forEach(item -> System.out.print(item + " "));
        System.out.println("}");

        Collections.addAll(myArrayList1, 11, 10, 9, 8, 7, 6, 5, 4);
        System.out.print("myArrayList1: { ");
        myArrayList1.forEach(item -> System.out.print(item + " "));
        System.out.println("}");

        MyArrayList<Integer> myArrayList2 = new MyArrayList<>();
        for (int i = 0; i < 11; i++)
        {
            myArrayList2.add(0);
        }
        Collections.copy(myArrayList2, myArrayList1);
        System.out.print("myArrayList2: { ");
        myArrayList2.forEach(item -> System.out.print(item + " "));
        System.out.println("}");

        Collections.sort(myArrayList2);
        System.out.print("myArrayList2: { ");
        myArrayList2.forEach(item -> System.out.print(item + " "));
        System.out.println("}");

        Collections.sort(myArrayList2, (a, b) -> {
            if (a.intValue() < b.intValue()) {
                return 1;
            }
            else if (a.intValue() > b.intValue()) {
                return -1;
            }
            else {
                return 0;
            }
        });
        System.out.print("myArrayList2: { ");
        myArrayList2.forEach(item -> System.out.print(item + " "));
        System.out.println("}");
    }
}
