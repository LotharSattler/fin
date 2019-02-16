import org.junit.Test;

import static org.junit.Assert.*;

public class FINTest {

    @Test
    public void finHasNoChars() {
        assertTrue(FIN.hasUpTo17Chars(""));
    }

    @Test
    public void finHas17Chars() {
        assertTrue(FIN.hasUpTo17Chars("12345678901234567"));
    }

    @Test
    public void finHas17CharsWithWhiteSpace() {
        assertTrue(FIN.hasUpTo17Chars("  12345678901234567 "));
    }

    @Test
    public void finHas18Chars() {
        assertFalse(FIN.hasUpTo17Chars("123456789012345678"));
    }

    @Test
    public void finHasNoIllegalChars() {
        assertTrue(FIN.hasNoIllegalChars("ab345678901234567"));
    }

    @Test
    public void finHasIllegalChars() {
        assertFalse(FIN.hasNoIllegalChars("%b345678901234567"));
    }

    @Test
    public void finHasUmlauts() {
        assertEquals("AOU", FIN.convertUmlauts("ÄÖÜ"));
    }

    @Test(expected = RuntimeException.class)
    public void finHas18CharsThrowsException() {
        FIN.validateFin("123456789012345678");
    }

    @Test(expected = RuntimeException.class)
    public void finHasIllegalCharsThrowsException() {
        FIN.validateFin("%2345678901234567");
    }

    @Test
    public void checkSumIsCalculated() {
        assertEquals('7', FIN.calculateCheckSum("TMBBH25JOA3009215"));
    }

    @Test
    public void checkSumIsValidated() {
        assertTrue(FIN.validateCheckSum("TMBBH25JOA3009215", "7"));
        assertTrue(FIN.validateCheckSum("WVWZZZ1JZ3W386755", "X"));
        assertFalse(FIN.validateCheckSum("TMBBH25JOA3009215", "8"));
    }

    @Test(expected = RuntimeException.class)
    public void checkSumIsEmptyThrowsException() {
        FIN.validateCheckSum("TMBBH25JOA3009215", "");
    }

    @Test(expected = RuntimeException.class)
    public void checkSumHasTwoCharsThrowsException() {
        FIN.validateCheckSum("TMBBH25JOA3009215", "10");
    }
}