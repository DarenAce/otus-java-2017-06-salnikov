package ru.otus.homework_06.ATM;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ATMDepartmentTest {
    public ATMDepartment atmDepartment;
    public ArrayList<ATM> atms;

    @Before
    public void before() {
        ArrayList<Cassette> cassettes = new ArrayList<>();
        cassettes.add(new Cassette(1, 10));
        cassettes.add(new Cassette(5, 10));
        atms = new ArrayList<>();
        atms.add(new ATM(cassettes));
        cassettes = new ArrayList<>();
        cassettes.add(new Cassette(5, 10));
        cassettes.add(new Cassette(10, 10));
        atms.add(new ATM(cassettes));
        atmDepartment = new ATMDepartment(atms);
    }

    @Test
    public void totalBalance() {
        Assert.assertEquals(210, atmDepartment.getTotalBalance());
    }

    @Test
    public void restoreATMStates() {
        for (ATM atm : atms) {
            atm.withdraw(atm.getBalance());
        }
        Assert.assertEquals(0,atmDepartment.getTotalBalance());
        atmDepartment.restoreATMStates();
        Assert.assertEquals(210, atmDepartment.getTotalBalance());
    }
}
