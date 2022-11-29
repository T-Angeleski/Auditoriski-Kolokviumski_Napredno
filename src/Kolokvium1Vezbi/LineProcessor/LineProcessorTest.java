package Kolokvium1Vezbi.LineProcessor;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

//class Line implements Comparable<Line> {
//    String line;
//    char c;
//
//    public Line(String line, char c) {
//        this.line = line;
//        this.c = c;
//    }
//
//    private int countOcc() {
//        int counter = 0;
//        for (char character : line.toLowerCase().toCharArray()) {
//            if (character == this.c) {
//                ++counter;
//            }
//        }
//        return counter;
//    }
//
//    @Override
//    public int compareTo(Line o) {
//        return Integer.compare(this.countOcc(), o.countOcc());
//    }
//
//    @Override
//    public String toString() {
//        return line;
//    }
//}

class LineProcessor {

    List<String> lines;

    public LineProcessor() {
        lines = new ArrayList<>();
    }

    private int countOcc(String line, char c) {
        int counter = 0;
        for(char character : line.toLowerCase().toCharArray()) {
            if(character == c) {
                ++counter;
            }
        }
        return counter;
    }

    public void readLines(InputStream in, OutputStream out, char c) {
        //Scanner sc = new Scanner(in);
        PrintWriter pw = new PrintWriter(out);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        lines = br.lines().collect(Collectors.toList());
        String max = lines.stream()
                .max((o1, o2) -> Integer.compare(countOcc(o1, c), countOcc(o2, c)))
                .orElse("");
//        //Reading
//        while (sc.hasNextLine()) {
//            String str = sc.nextLine();
//            Line line = new Line(str, c);
//            lines.add(line);
//        }
//        sc.close();
//
//        //Find max line
//        Line max = lines.get(0);
//        for (Line line : lines) {
//            if (line.compareTo(max) >= 0) { // >, 1
//                max = line;
//            }
//        }
//
//        pw.println(max);
//        pw.flush();           o

        pw.println(max);
        pw.flush();
    }
}


public class LineProcessorTest {
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();


        lineProcessor.readLines(System.in, System.out, 'a');

    }
}
