package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static util.StringUtils.stringToLower;

class StringUtilsTest {
    @Test
    public void toLowerStringTest() {
        String word = "COUNTRY";
        String lowerWord = stringToLower(word);
        assertEquals(word.toLowerCase(), lowerWord);
    }

    @Test
    public void toLowerStringWithNullInputTest(){
        String word = null;
        String lowerWord = stringToLower(word);
        assertEquals(null,lowerWord);
    }
}