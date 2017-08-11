package ru.otus.homework_06.ATM;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ATMTest {
    ATM atm;
    ArrayList<Cassette> cassettes;

    @Before
    public void before() {
        cassettes = new ArrayList<>();
        cassettes.add(new Cassette(5,10));
        cassettes.add(new Cassette(10,10));
        atm = new ATM(cassettes);
    }

    @Test
    public void getBalance() {
        Assert.assertEquals(150, atm.getBalance());
    }

    @Test
    public void withdraw() {
        Assert.assertTrue(atm.withdraw(150));
        Assert.assertEquals(0, atm.getBalance());
    }

    @Test
    public void withdrawNotEnough() {
        Assert.assertFalse(atm.withdraw(200));
    }

    @Test
    public void withdrawEmpty() {
        Assert.assertTrue(atm.withdraw(atm.getBalance()));
        Assert.assertFalse(atm.withdraw(1));
    }

    @Test
    public void withdrawWrongNominal() {
        Assert.assertFalse(atm.withdraw(1));
    }

    @Test
    public void setStrategy() {
        Assert.assertTrue(atm.withdraw(10));
        Assert.assertEquals(9, cassettes.get(0).getCount());
        atm.setStrategy(new MinNominalMinCountStrategy());
        Assert.assertTrue(atm.withdraw(25));
        Assert.assertEquals(5, cassettes.get(0).getCount());
    }
}
