package me.synapz.paintball.wager;

public class WagerManager {

    private int wagerAmount = 0;

    public void addWager(double amount) {
        wagerAmount += amount;
    }

    public boolean hasWager() {
        return wagerAmount > 0.0;
    }

    public double getAndResetWager() {
        double savedWager = wagerAmount;
        wagerAmount = 0;
        return savedWager;
    }

    public int getWager() {
        return wagerAmount;
    }
}
