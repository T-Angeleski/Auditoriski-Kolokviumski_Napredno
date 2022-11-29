package Auditoriska5.WordCountZ1;

import javax.sound.sampled.Line;

public class LineCounter {

    private int lines;
    private int words;
    private int chars;

    public LineCounter(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }

    public LineCounter(String line) {
        this.lines = 1;
        words += line.split("\\s+").length;
        chars += line.length();
    }

    @Override
    public String toString() {
        return String.format("Lines: %d Words: %d Chars: %d",
                lines, words, chars);
    }

    public LineCounter sum(LineCounter right) {
        return new LineCounter(this.lines + right.lines,
                this.words + right.words,
                this.chars + right.chars);

    }
}
