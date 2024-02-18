package order.orderkafka.constants;

public final class PdfConstants {

    private PdfConstants() {
        throw new IllegalStateException(UtilityClass.UTILITY_CLASS_STRING);
    }

    public static final String ORDER_INVOICE_TEXT = "ORDER INVOICE";
    public static final String INVOICES_PATH = "./order-kafka/invoices/";
    public static final String PDF_EXTENSION = ".pdf";
    public static final String ORDER_FIRST_PARAGRAPH_TEXT = "Dear %s, your order has been processed successfully!";
    public static final String ORDER_SECOND_PARAGRAPH_TEXT = "Please, find below the details of your order.";
    public static final String PRICE_PER_UNIT_COLUMN = "Price per Unit";
    public static final String QUANTITY_COLUMN = "Quantity";
    public static final String AMOUNT_COLUMN = "Amount";
}
