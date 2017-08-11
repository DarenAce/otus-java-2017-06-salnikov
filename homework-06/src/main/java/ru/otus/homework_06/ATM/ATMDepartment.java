package ru.otus.homework_06.ATM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ATMDepartment {
    private ArrayList<ATM> atms;
    private HashMap<ATM, ATM.ATMMemento> atmToATMStates;

    public ATMDepartment(ArrayList<ATM> atms) {
        this.atms = new ArrayList<>(atms);
        atmToATMStates = new HashMap<>();
        for (ATM atm : atms) {
            atmToATMStates.put(atm, atm.save());
        }
    }

    public int getTotalBalance() {
        int balance = 0;
        for (ATM atm : atms) {
            balance += atm.getBalance();
        }
        return balance;
    }

    public void restoreATMStates() {
        for (ATM atm : atms) {
            atm.restore(atmToATMStates.get(atm));
        }
    }
}
