package ma.therightman.orderkafka;

import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import ma.therightman.orderkafka.constants.KafkaConstants;
import ma.therightman.orderkafka.dtos.OrderDTO;
import ma.therightman.orderkafka.services.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class KafkaListeners {

    public static final String TOPIC_STRING = "Topic ";
    public static final String RESULT_STRING = " result : ";
    public static final String IS_NULL_STRING = " is null";
    public static final String FACTORY_STRING = "factory";

    private final OrderService orderService;

    KafkaListeners(OrderService orderService){
        this.orderService = orderService;
    }

    @KafkaListener(topics= KafkaConstants.KAFKA_TOPIC_ORDERS_PROCESSED, groupId = KafkaConstants.KAFKA_GROUP_ID_DEMO, containerFactory = FACTORY_STRING)
    void listenerInventory(OrderDTO data) throws IOException, MessagingException, DocumentException {
        if(!Objects.isNull(data)){
            log.warn(TOPIC_STRING + KafkaConstants.KAFKA_TOPIC_ORDERS_PROCESSED + RESULT_STRING + data.toString());
            this.orderService.processOrderOk(data);

        } else {
            log.error(TOPIC_STRING + KafkaConstants.KAFKA_TOPIC_ORDERS_PROCESSED + IS_NULL_STRING);
        }
    }

    @KafkaListener(topics= KafkaConstants.KAFKA_TOPIC_ORDERS_NOT_PROCESSED, groupId = KafkaConstants.KAFKA_GROUP_ID_DEMO, containerFactory = FACTORY_STRING)
    void listenerInventoryError(OrderDTO data) throws IOException, MessagingException, DocumentException {
        if(!Objects.isNull(data)){
            log.warn(TOPIC_STRING + KafkaConstants.KAFKA_TOPIC_ORDERS_NOT_PROCESSED + RESULT_STRING + data.toString());
            this.orderService.processOrderNotOk(data);

        } else {
            log.error(TOPIC_STRING + KafkaConstants.KAFKA_TOPIC_ORDERS_NOT_PROCESSED + IS_NULL_STRING);
        }
    }
}
