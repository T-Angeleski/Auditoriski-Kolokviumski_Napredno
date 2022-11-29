package Auditoriska2;

public class Matrix {
    public static double sum(double[][] a) {
        double sum = 0;
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[0].length; j++)
                sum += a[i][j];
        return sum;
    }

    // Losha implementacija
//    public static double average(double[][] a) {
//        double avg = 0;
//        for (int i = 0; i < a.length; i++)
//            for (int j = 0; j < a[0].length; j++)
//                avg += a[i][j];
//        return avg / (a.length * a[0].length);
//    }
    //Podobra implementacija
    public static double average(double[][] a) {
        return sum(a) / (a.length * a[0].length); // broj redici * broj koloni
    }

    public static void main(String[] args) {
        double[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}};
        System.out.println(sum(matrix));
        System.out.println(average(matrix));
    }
}
