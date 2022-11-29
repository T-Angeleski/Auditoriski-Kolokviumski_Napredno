package Auditoriski3.Zadacha1;

public class PlatinumCheckingAccount extends InterestCheckingAccount{
    public PlatinumCheckingAccount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    //nemora getAccountType oti nasleduva od interest acc

    @Override
    public void addInterest() {
        addAmount(getCurrentAmount() * INTEREST_RATE * 2);
    }
}
