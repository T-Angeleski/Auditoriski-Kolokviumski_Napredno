package Auditoriska3.Zadacha1;

public class NonInterestCheckingAccount extends Account{
    public NonInterestCheckingAccount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.NON_INTEREST;
    }
}
