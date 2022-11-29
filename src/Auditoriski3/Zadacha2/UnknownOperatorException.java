package Auditoriski3.Zadacha2;

public class UnknownOperatorException extends Exception{
    public UnknownOperatorException(char operator) {
        super(String.format("This operator %c is not valid",operator));
    }
}
