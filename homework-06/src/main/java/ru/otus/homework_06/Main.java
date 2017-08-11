package ru.otus.homework_06;

import ru.otus.homework_06.ATM.ATM;
import ru.otus.homework_06.ATM.Cassette;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int requestedSum;
        ArrayList<Cassette> cassettes = new ArrayList<>();
        cassettes.add(new Cassette(100, 100));
        cassettes.add(new Cassette(100, 100));
        cassettes.add(new Cassette(500, 100));
        cassettes.add(new Cassette(1_000, 100));
        cassettes.add(new Cassette(5_000, 100));
        ATM atm = new ATM(cassettes);
        System.out.printf("Initial balance: %,d.\n", atm.getBalance());
        requestedSum = 50_000;
        System.out.printf("Trying to withdraw %1$,d: %2$b.\n", requestedSum, atm.withdraw(requestedSum));
        System.out.printf("Changed balance: %,d.\n", atm.getBalance());
        requestedSum = 1_950;
        System.out.printf("Trying to withdraw %1$,d: %2$b.\n", requestedSum, atm.withdraw(requestedSum));
        System.out.printf("Changed balance: %,d.\n", atm.getBalance());
    }
}
