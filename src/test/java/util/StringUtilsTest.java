package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static util.StringUtils.stringToLower;
@Nested
@DisplayName("String utilities testing")
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