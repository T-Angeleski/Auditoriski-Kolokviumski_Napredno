package Auditoriska2;

public class StringPrefix {
    // return true ako str1 e prefix na str2

    public static boolean isPrefix(String first, String second) {
        if(first.length() > second.length()) return false;

        // test
        // tes f lon   do kraj na prv, duri ne najdeme razlicen

        for (int i = 0 ; i < first.length(); i++)
            if(first.charAt(i) != second.charAt(i)) return false;

        return true;
    }

    public static void main(String[] args) {
        System.out.println(isPrefix("test", "testing"));
        System.out.println(isPrefix("test", "tes"));
        System.out.println(isPrefix("test", "adsdw"));
    }
}
