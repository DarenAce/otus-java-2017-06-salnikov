package ru.otus.homework_02;

public class Main {
    public static void main(String[] args) {
        System.out.println(TextColors.ANSI_BLUE + "Defining size of Object." + TextColors.ANSI_RESET);
        MemoryMeasure.getObjectSize(Object::new);
        System.out.println(TextColors.ANSI_BLUE + "Defining size of empty String (using string pool)."
                + TextColors.ANSI_RESET);
        MemoryMeasure.getObjectSize(String::new);
        System.out.println(TextColors.ANSI_BLUE + "Defining size of empty String (without using string pool)."
                + TextColors.ANSI_RESET);
        MemoryMeasure.getObjectSize(() -> new String(new char[0]));
        System.out.println(TextColors.ANSI_BLUE + "Defining size of array of ints containing 0 elements."
                + TextColors.ANSI_RESET);
        MemoryMeasure.getObjectSize(() -> new int[0]);
        System.out.println(TextColors.ANSI_BLUE + "Defining size of array of ints containing 100 elements."
                + TextColors.ANSI_RESET);
        MemoryMeasure.getObjectSize(() -> new int[100]);
        System.out.println(TextColors.ANSI_BLUE + "Defining size of array of ints containing 10 000 elements."
                + TextColors.ANSI_RESET);
        MemoryMeasure.getObjectSize(() -> new int[10_000]);
    }
}
