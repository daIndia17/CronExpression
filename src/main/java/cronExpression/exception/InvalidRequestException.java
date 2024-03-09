package cronExpression.exception;

/**
 * @author : Arjun Das
 * @since : 08-03-2024
 */
public class InvalidRequestException extends RuntimeException{

    /**
     * @param message
     */
    public InvalidRequestException(String message){
        super(message);
    }
}
