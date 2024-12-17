package ba.unsa.etf.ts.Therapy.exceptions;

public class InvalidFormatException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidFormatException(String message) {
        super(message);
    }
}
