package cinema.error;

public class InvalidSeatException extends RuntimeException{

    public InvalidSeatException(String message) {
        super(message);
    }
}
