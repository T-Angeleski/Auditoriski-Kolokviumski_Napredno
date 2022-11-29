package Auditoriska5.Grades;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Course {
    private List<Student> students;

    public Course() {
        students = new ArrayList<>();
    }

    public void readData(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        students = reader.lines()
                .map(Student::create) // kreiraj student od sekoja linija
                .collect(Collectors.toList());// od mnogu -> mnogu
    }

    public void printSortedData(OutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        //ako koristis writer, na kraj obavezno flush
        students.stream()
                .sorted() // bez argument, se povikuva interen comparator ( toj sto go imame vo Student)
                .forEach(writer::println);
        writer.flush(); // !!!!! Important
    }

    public void printDetailedData(OutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        students.stream()
                .forEach(s -> writer.println(s.fullFormatPrint()));
        writer.flush(); // !!!!! Important
    }

    public void printDistribution(OutputStream outputStream) {
        PrintWriter writer = new PrintWriter(outputStream);
        int[] gradeDistribution = new int[6];

        for (Student s : students)
            gradeDistribution[s.getGrade() - 'A']++;

        for (int i = 0; i < 6; i++) {
            writer.printf("%c -> %d\n", i+ 'A', gradeDistribution[i]);
        }
        writer.flush();
    }
}
