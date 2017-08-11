package ru.otus.homework_06.ATM;

import java.util.Iterator;
import java.util.List;

public class MinNominalMinCountStrategy implements WithdrawStrategy {
    @Override
    public boolean withdraw(List<Cassette> cassettes, int requestedSum) {
        cassettes.sort((c1, c2) -> {
            int result = Integer.compare(c1.getNominal(), c2.getNominal());
            if (result == 0) {
                return Integer.compare(c1.getCount(), c2.getCount());
            }
            return result;
        });
        if (isWithdrawalPossible(cassettes, requestedSum)) {
            int remaining = requestedSum;
            Iterator<Cassette> iterator = cassettes.iterator();
            while (iterator.hasNext() && remaining != 0) {
                remaining = iterator.next().withdraw(remaining);
            }
            return true;
        }
        return false;
    }
}
