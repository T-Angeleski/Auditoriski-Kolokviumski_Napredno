package Auditoriska5.Grades;

import java.io.*;

public class FacultyTest {
    public static void main(String[] args) {
        Course course = new Course();
        File input = new File("D:\\School\\Vtora Godina\\Prv semester\\Napredno Programiranje\\Auditoriski\\AuditoriskiNP\\src\\Auditoriska5\\Files\\students");
        File output = new File("D:\\School\\Vtora Godina\\Prv semester\\Napredno Programiranje\\Auditoriski\\AuditoriskiNP\\src\\Auditoriska5\\Files\\result_students");
        try {
            course.readData(new FileInputStream(input));
            System.out.println("--- Printing distribution ---");
            course.printDistribution(System.out);

            System.out.println("--- Printing sorted ---");
            course.printSortedData(System.out);

            System.out.println("--- Printing details ---");
            course.printDetailedData(new FileOutputStream(output));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //Ako nekoj del ni javuva exception, da stavime komentari kaj sto ne raboti
        // "Ovoj del ne raboti poradi x pricina, komentar za da run program"
    }
}
