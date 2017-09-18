package ru.otus.homework14;

import java.util.*;

public class ArraySortingThread extends Thread {
    private int[] array;

    public ArraySortingThread(int[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        Arrays.sort(array);
    }
}
