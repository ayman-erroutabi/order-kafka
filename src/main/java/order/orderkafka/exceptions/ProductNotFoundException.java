package ma.therightman.orderkafka.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message){
        super( "Product : "+ message +" not found in our store.");
    }
}