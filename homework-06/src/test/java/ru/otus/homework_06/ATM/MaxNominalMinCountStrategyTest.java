package ru.otus.homework_06.ATM;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MaxNominalMinCountStrategyTest {
    ArrayList<Cassette> cassettes;
    MaxNominalMinCountStrategy strategy;

    @Before
    public void before() {
        cassettes = new ArrayList<>();
        strategy = new MaxNominalMinCountStrategy();
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
        cassettes.add(cassette1);
        cassettes.add(cassette5);
        Assert.assertTrue(strategy.withdraw(cassettes, 7));
        Assert.assertEquals(9, cassette5.getCount());
        Assert.assertEquals(8, cassette1.getCount());
    }

    @Test
    public void withdrawTwoCassettesNotEnough() {
        cassettes.add(new Cassette(1, 10));
        cassettes.add(new Cassette(1, 10));
        Assert.assertFalse(strategy.withdraw(cassettes, 100));
    }

    @Test
    public void withdrawTwoCassettesEmpty() {
        cassettes.add(new Cassette(5, 10,0));
        cassettes.add(new Cassette(1, 10,0));
        Assert.assertFalse(strategy.withdraw(cassettes, 1));
    }

    @Test
    public void withdrawTwoCassettesWrongNominal() {
        cassettes.add(new Cassette(5, 10));
        cassettes.add(new Cassette(10, 10));
        Assert.assertFalse(strategy.withdraw(cassettes, 41));
    }
}
