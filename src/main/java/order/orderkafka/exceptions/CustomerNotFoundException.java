package order.orderkafka.exceptions;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(long message){
        super( "Customer with id : "+ message +" not found in our database.");
    }
}