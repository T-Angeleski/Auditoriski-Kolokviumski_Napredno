package Auditoriska3.Zadacha1;

public abstract class Account {
    private String accountOwner;
    private int id; //sequential number, na momentalna smetka
    private static int idSeed = 1000; //cuvame na nivo na klasa
    private double currentAmount;
    private AccountType accountType;

    public Account(String accountOwner, double currentAmount) {
        this.accountOwner = accountOwner;
        this.currentAmount = currentAmount;
        this.id = idSeed++; // za sekoj nov account da se zgolemi
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void addAmount(double amount) {
        this.currentAmount += amount;
    }

    public void withdrawAmount(double amount) throws CanNotWithdrawMoney {
        if (currentAmount < amount)
            throw new CanNotWithdrawMoney(currentAmount, amount);
        this.currentAmount -= amount;
    }

    public abstract AccountType getAccountType(); //ne implementirame ovde, tuku vo decata

    @Override
    public String toString() {
        return String.format("%d: %.2f", id, currentAmount);
    }
}
