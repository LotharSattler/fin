public class FIN {

    private FIN() {
    }

    public static boolean hasUpTo17Chars(String fin) {
        return fin.trim().length() <= 17;
    }

    public static boolean hasNoIllegalChars(String fin) {
        return fin.trim().toUpperCase().matches("[A-Z0-9]+");
    }

    public static String convertUmlauts(String fin) {
        return fin.trim().toUpperCase().replace('Ä', 'A').replace('Ö', 'O').replace('Ü', 'U');
    }

    public static void validateFin(String fin) {
        if (!hasUpTo17Chars(fin))
            throw new IllegalArgumentException("FIN must not have more than 17 characters: " + fin);
        if (!hasNoIllegalChars(convertUmlauts(fin)))
            throw new IllegalArgumentException("Illegal characters in FIN: " + fin);
    }

    private static String transliterateFin(String fin) {
        String leadingZeros = ("00000000000000000" + fin.trim().toUpperCase());
        String toTransliterate = leadingZeros.substring(leadingZeros.length() - 17);
        StringBuilder transliterated = new StringBuilder();

        for (char c : toTransliterate.toCharArray()) {
            transliterated.append(transliterateChar(c));
        }

        return transliterated.toString();
    }

    private static char transliterateChar(char c) {
        char t;

        switch (c) {
            case 'A':
            case 'Ä':
            case 'J':
                t = '1';
                break;
            case 'B':
            case 'K':
            case 'S':
                t = '2';
                break;
            case 'C':
            case 'L':
            case 'T':
                t = '3';
                break;
            case 'D':
            case 'M':
            case 'U':
            case 'Ü':
                t = '4';
                break;
            case 'E':
            case 'N':
            case 'V':
                t = '5';
                break;
            case 'F':
            case 'W':
                t = '6';
                break;
            case 'G':
            case 'P':
            case 'X':
                t = '7';
                break;
            case 'H':
            case 'Q':
            case 'Y':
                t = '8';
                break;
            case 'I':
            case 'R':
            case 'Z':
                t = '9';
                break;
            case 'O':
            case 'Ö':
                t = '0';
                break;
            default:
                t = c;
        }

        return t;
    }

    public static char calculateCheckSum(String fin) {
        char checkSum;
        String transliterated = transliterateFin(fin);
        int[] weights = {9, 8, 7, 6, 5, 4, 3, 2, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;

        for (int i = 0; i < 17; i++) {
            int value = transliterated.charAt(i) - '0';
            sum += value * weights[i];
        }

        sum %= 11;

        if (sum == 10) checkSum = 'X';
        else checkSum = (char) (sum + '0');

        return checkSum;
    }

    public static boolean validateCheckSum(String fin, String checkSum) {

        if (checkSum.trim().length() == 0 || checkSum.trim().length() > 1) {
            throw new IllegalArgumentException("check sum must not have more or less than 1 character: " + checkSum);
        }

        return checkSum.trim().charAt(0) == calculateCheckSum(fin);
    }
}