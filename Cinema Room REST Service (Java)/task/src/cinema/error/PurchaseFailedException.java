package cinema.error;

public class PurchaseFailedException extends RuntimeException{

    public PurchaseFailedException(String message) {
        super(message);
    }
}
