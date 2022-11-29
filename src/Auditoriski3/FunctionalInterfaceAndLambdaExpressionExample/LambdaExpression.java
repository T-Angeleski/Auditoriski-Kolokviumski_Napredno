package Auditoriski3.FunctionalInterfaceAndLambdaExpressionExample;

public class LambdaExpression {
    public static void main(String[] args) {
        //Lambda expression with function body
        SomeFunctionalInterface add = (x, y) -> {
            System.out.println("some code");
            return x + y;
        };
        //Lambda expression with simple output
        SomeFunctionalInterface multiply = (x, y) -> x * y;

        System.out.println(add.calculate(5,10));
    }
}
