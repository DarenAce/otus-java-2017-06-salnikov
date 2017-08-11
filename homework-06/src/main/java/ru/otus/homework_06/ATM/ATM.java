package ru.otus.homework_06.ATM;

import java.util.ArrayList;

public class ATM {
    class ATMMemento {
        private ArrayList<Cassette> cassetteStates;

        private ATMMemento(ArrayList<Cassette> cassettes) {
            cassetteStates = new ArrayList<>();
            cassettes.forEach(cassette -> cassetteStates.add(new Cassette(cassette)));
        }

        private ArrayList<Cassette> getState() {
            return this.cassetteStates;
        }
    }

    private ArrayList<Cassette> cassettes;
    private WithdrawStrategy strategy;

    public ATM(ArrayList<Cassette> cassettes) {
        this(cassettes, new MaxNominalMinCountStrategy());
    }

    public ATM(ArrayList<Cassette> cassettes, WithdrawStrategy strategy) {
        this.cassettes = cassettes;
        this.strategy = strategy;
    }

    public boolean withdraw(int requestedSum) {
        return strategy.withdraw(cassettes, requestedSum);
    }

    public int getBalance() {
        int balance = 0;
        for (Cassette c : cassettes) {
            balance += c.getBalance();
        }
        return balance;
    }

    public void setStrategy(WithdrawStrategy strategy) {
        this.strategy = strategy;
    }

    public ATMMemento save() {
        return new ATMMemento(new ArrayList<>(this.cassettes));
    }

    public void restore(ATMMemento atmMemento) {
        this.cassettes = atmMemento.getState();
    }
}
