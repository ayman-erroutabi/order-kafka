package ma.therightman.orderkafka.exceptions;

public class ProductOutOfStockException extends RuntimeException{
    public ProductOutOfStockException(String message){
        super("Product : "+ message +" is currently out of stock.");
    }
}