package ru.otus.homework_06.ATM;

public class Cassette {
    private final int capacity;
    private int nominal;
    private int count;

    public Cassette(int nominal, int capacity) {
        this(nominal, capacity, capacity);
    }

    public Cassette(Cassette cassette) {
        this(cassette.nominal, cassette.capacity, cassette.count);
    }

    public Cassette(int nominal, int capacity, int count) {
        if (nominal <= 0) {
            throw new IllegalArgumentException("Nominal must be more than 0: " + nominal);
        }
        this.nominal = nominal;
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be more than 0: " + capacity);
        }
        this.capacity = capacity;
        if (count > capacity) {
            throw new IllegalArgumentException("Number of bills must be less or equal than cassette capacity: " + count);
        }
        this.count = count;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNominal() {
        return nominal;
    }

    public int getCount() {
        return count;
    }

    public int getBalance() {
        return nominal * count;
    }

    public int withdraw(int requestedSum) {
        int withdrawCount = Math.min(requestedSum / nominal, count);
        count -= withdrawCount;
        return requestedSum - withdrawCount * nominal;
    }
}
