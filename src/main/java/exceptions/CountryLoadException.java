package exceptions;

public class CountryLoadException extends RuntimeException {
    public CountryLoadException(String message) {
        super(message);
    }
}
