package order.orderkafka.constants;

public final class KafkaConstants {

    private KafkaConstants() {
        throw new IllegalStateException(UtilityClass.UTILITY_CLASS_STRING);
    }
    public static final String KAFKA_TOPIC_ORDERS = "orders";
    public static final String KAFKA_GROUP_ID_DEMO = "demo";
    public static final String KAFKA_TOPIC_ORDERS_PROCESSED = "orders-processed";
    public static final String KAFKA_TOPIC_ORDERS_NOT_PROCESSED = "orders-not-processed";
}
