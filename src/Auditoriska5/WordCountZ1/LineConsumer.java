package Auditoriska5.WordCountZ1;

import java.util.function.Consumer;

public class LineConsumer implements Consumer<String> {
    private int lines = 0;
    private int words = 0;
    private int chars = 0;

    @Override
    public void accept(String s) {
        ++this.lines;
        words += s.split("\\s+").length;
        chars += s.length();
    }

    @Override
    public String toString() {
        return String.format("Lines: %d Words: %d Chars: %d",
                lines, words, chars);
    }
}
