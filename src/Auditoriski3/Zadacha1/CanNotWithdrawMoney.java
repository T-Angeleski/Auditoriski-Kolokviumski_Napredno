package Auditoriski3.Zadacha1;

public class CanNotWithdrawMoney extends Exception {
    public CanNotWithdrawMoney(double currentAmount, double amount) {
        super(String.format("Your current amount is: %.2f, and you cannot withdraw this amount: %.2f",
                currentAmount, amount));
    }
}
