package net.mready.dicejava.model;

public class History {
    // dice left and dice right
    private int dice1, dice2;
    // sum of dice
    private int diceSum;
    private boolean isDouble;

    public History() {
        this.dice1 = 0;
        this.dice2 = 0;
        this.diceSum = 0;
        this.isDouble = false;
    }

    public History(int dice1, int dice2, int diceSum, boolean isDouble) {
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.diceSum = diceSum;
        this.isDouble = isDouble;
    }

    public int getDice1() {
        return dice1;
    }

    public void setDice1(int dice1) {
        this.dice1 = dice1;
    }

    public int getDice2() {
        return dice2;
    }

    public void setDice2(int dice2) {
        this.dice2 = dice2;
    }

    public int getDiceSum() {
        return diceSum;
    }

    public void setDiceSum(int diceSum) {
        this.diceSum = diceSum;
    }

    public boolean isDouble() {
        return isDouble;
    }

    public void setDouble(boolean aDouble) {
        isDouble = aDouble;
    }

    @Override
    public String toString() {
        return "History{" +
                "dice1=" + dice1 +
                ", dice2=" + dice2 +
                ", diceSum=" + diceSum +
                ", isDouble=" + isDouble +
                '}';
    }
}
