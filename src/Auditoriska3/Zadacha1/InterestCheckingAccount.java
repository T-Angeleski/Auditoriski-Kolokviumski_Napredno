package Auditoriska3.Zadacha1;

public class InterestCheckingAccount extends Account implements  InterestBearingAccount{
    public static final double INTEREST_RATE = 0.03;

    public InterestCheckingAccount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.INTEREST;
    }

    @Override
    public void addInterest() {
        addAmount(getCurrentAmount() * INTEREST_RATE);
    }
}
