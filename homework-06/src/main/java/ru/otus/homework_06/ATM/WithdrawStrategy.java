package ru.otus.homework_06.ATM;

import java.util.Iterator;
import java.util.List;

@FunctionalInterface
public interface WithdrawStrategy {
    boolean withdraw(List<Cassette> cassettes, int requestedSum);
    default boolean isWithdrawalPossible(List<Cassette> cassettes, int requestedSum) {
        int remaining = requestedSum;
        Iterator<Cassette> iterator = cassettes.iterator();
        while (iterator.hasNext() && remaining != 0) {
            Cassette cassette = iterator.next();
            int count = Math.min(remaining / cassette.getNominal(), cassette.getCount());
            remaining -= count * cassette.getNominal();
        }
        return remaining == 0;
    }
}
