package Auditoriska5.WordCountZ1;

import java.io.*;
import java.util.Scanner;

public class WordCountTest {


    public static void readDataWithScanner(InputStream inputStream) {
        int lines = 0, words = 0, chars = 0;
        Scanner scanner = new Scanner(inputStream);

        //next - do posleden space ili endl
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ++lines;

            words += line.split("\\s+").length;
            chars += line.length();
        }
        System.out.printf("Lines: %d Words: %d Chars: %d",
                lines, words, chars);
    }

    public static void readDataWithBufferedReader(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));// character oriented input stream
        int lines = 0, words = 0, chars = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            ++lines;

            words += line.split("\\s+").length;
            chars += line.length();
        }
        System.out.printf("Lines: %d Words: %d Chars: %d",
                lines, words, chars);
    }

    //kako citas linija po linija, da cuvas rezultat vo neoja si klasa
    // posle so funkcija da obrabotime vrednosti
    public static void readDataWithBufferedReaderAndMapReduce(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));// character oriented input stream

        LineCounter result = reader.lines()// vrakja Stream od Stringovi
                .map(line -> new LineCounter(line)) // za sekoja linija, pretvori ja vo objekt od tip linecounter
                .reduce(new LineCounter(0, 0, 0), // init state na reducer, od tamu trgame,
                        (left, right) -> left.sum(right)); // od mnogu, napravi edno, od site LineCounter vo eden
                //left prethoden state, right sleden sho ide
                // 0 + lines/words/chars od sledna linija
                //left sega e od prva linija e, right e sledna linija
        System.out.println(result);
    }

    public static void readDataWithBufferedReaderAndConsume(InputStream inputStream) {
        //so consumer, site linii gi prima i pravi nesto so niv
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));// character oriented input stream
        LineConsumer lineConsumer = new LineConsumer();
        reader.lines().forEach(lineConsumer); // samo eden method ima
        System.out.println(lineConsumer);
    }


    public static void main(String[] args) {
        File file = new File("D:\\School\\Vtora Godina\\Prv semester\\Napredno Programiranje\\Auditoriski\\AuditoriskiNP\\src\\Auditoriska5\\Files\\text");


        //Ne se stava throws vo main method
        try {
            readDataWithScanner(new FileInputStream(file));
            System.out.println();
            readDataWithBufferedReader(new FileInputStream(file));
            System.out.println();
            readDataWithBufferedReaderAndMapReduce(new FileInputStream(file));
            System.out.println();
            readDataWithBufferedReaderAndConsume(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
