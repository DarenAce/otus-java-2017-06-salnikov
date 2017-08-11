package ru.otus.homework_06.ATM;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MinNominalMinCountStrategyTest {
    ArrayList<Cassette> cassettes;
    MinNominalMinCountStrategy strategy;

    @Before
    public void before() {
        cassettes = new ArrayList<>();
        strategy = new MinNominalMinCountStrategy();
    }

    @Test
    public void withdrawOneCassette() {
        cassettes.add(new Cassette(1, 10));
        Assert.assertTrue(strategy.withdraw(cassettes, 6));
    }

    @Test
    public void withdrawOneCassetteNotEnough() {
        cassettes.add(new Cassette(1, 10, 5));
        Assert.assertFalse(strategy.withdraw(cassettes, 6));
    }

    @Test
    public void withdrawOneCassetteEmpty() {
        cassettes.add(new Cassette(1, 10, 0));
        Assert.assertFalse(strategy.withdraw(cassettes, 6));
    }

    @Test
    public void withdrawOneCassetteWrongNominal() {
        cassettes.add(new Cassette(5, 10));
        Assert.assertFalse(strategy.withdraw(cassettes, 4));
    }

    @Test
    public void withdrawTwoCassettes() {
        Cassette cassette1 = new Cassette(1, 10);
        Cassette cassette5 = new Cassette(5, 10);
        cassettes.add(cassette5);
        cassettes.add(cassette1);
        Assert.assertTrue(strategy.withdraw(cassettes, 7));
        Assert.assertEquals(3, cassette1.getCount());
        Assert.assertEquals(10, cassette5.getCount());
    }

    @Test
    public void withdrawTwoCassettesNotEnough() {
        cassettes.add(new Cassette(1, 10));
        cassettes.add(new Cassette(1, 10));
        Assert.assertFalse(strategy.withdraw(cassettes, 100));
    }

    @Test
    public void withdrawTwoCassettesEmpty() {
        cassettes.add(new Cassette(1, 10, 0));
        cassettes.add(new Cassette(5, 10, 0));
        Assert.assertFalse(strategy.withdraw(cassettes, 1));
    }

    @Test
    public void withdrawTwoCassettesWrongNominal() {
        cassettes.add(new Cassette(10, 10));
        cassettes.add(new Cassette(5, 10));
        Assert.assertFalse(strategy.withdraw(cassettes, 41));
    }
}
