package Auditoriski3.Zadacha2;

public class Addition implements Strategy{
    @Override
    public double calculate(double num1, double num2) {
        return num1 + num2;
    }
}
