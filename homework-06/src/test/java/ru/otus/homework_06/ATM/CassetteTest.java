package ru.otus.homework_06.ATM;

import org.junit.Assert;
import org.junit.Test;

public class CassetteTest {
    @Test(expected = IllegalArgumentException.class)
    public void wrongNominal() throws IllegalArgumentException {
        Cassette cassette = new Cassette(-1, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongCapacity() throws IllegalArgumentException {
        Cassette cassette = new Cassette(1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void wrongCount() throws IllegalArgumentException {
        Cassette cassette = new Cassette(1, 5, 10);
    }

    @Test
    public void withdraw() {
        Cassette cassette = new Cassette(1, 10);
        Assert.assertEquals(10, cassette.getCapacity());
        Assert.assertEquals(1, cassette.getNominal());
        Assert.assertEquals(0, cassette.withdraw(9));
        Assert.assertEquals(1, cassette.getCount());
        Assert.assertEquals(1, cassette.getBalance());
    }

    @Test
    public void withdrawNotEnough() {
        Cassette cassette = new Cassette(1, 10, 9);
        Assert.assertEquals(1, cassette.withdraw(10));
    }

    @Test
    public void withdrawEmpty() {
        Cassette cassette = new Cassette(1, 10, 0);
        Assert.assertEquals(10, cassette.withdraw(10));
    }

    @Test
    public void withdrawWrongNominal() {
        Cassette cassette = new Cassette(10, 100);
        Assert.assertEquals(5, cassette.withdraw(5));
    }
}
