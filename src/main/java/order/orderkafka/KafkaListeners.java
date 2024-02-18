package order.orderkafka;

import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import order.orderkafka.constants.KafkaConstants;
import order.orderkafka.constants.LoggingConstants;
import order.orderkafka.dtos.OrderDTO;
import order.orderkafka.services.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class KafkaListeners {

    private final OrderService orderService;

    KafkaListeners(OrderService orderService){
        this.orderService = orderService;
    }

    @KafkaListener(topics= KafkaConstants.KAFKA_TOPIC_ORDERS_PROCESSED, groupId = KafkaConstants.KAFKA_GROUP_ID_DEMO, containerFactory = LoggingConstants.FACTORY_STRING)
    void listenerInventory(OrderDTO data) throws IOException, MessagingException, DocumentException {
        if(!Objects.isNull(data)){
            log.warn(LoggingConstants.TOPIC_STRING + KafkaConstants.KAFKA_TOPIC_ORDERS_PROCESSED + LoggingConstants.RESULT_STRING + data.toString());
            this.orderService.processOrderOk(data);

        } else {
            log.error(LoggingConstants.TOPIC_STRING + KafkaConstants.KAFKA_TOPIC_ORDERS_PROCESSED + LoggingConstants.IS_NULL_STRING);
        }
    }

    @KafkaListener(topics= KafkaConstants.KAFKA_TOPIC_ORDERS_NOT_PROCESSED, groupId = KafkaConstants.KAFKA_GROUP_ID_DEMO, containerFactory = LoggingConstants.FACTORY_STRING)
    void listenerInventoryError(OrderDTO data) {
        if(!Objects.isNull(data)){
            log.warn(LoggingConstants.TOPIC_STRING + KafkaConstants.KAFKA_TOPIC_ORDERS_NOT_PROCESSED + LoggingConstants.RESULT_STRING + data.toString());
            this.orderService.processOrderNotOk(data);

        } else {
            log.error(LoggingConstants.TOPIC_STRING + KafkaConstants.KAFKA_TOPIC_ORDERS_NOT_PROCESSED + LoggingConstants.IS_NULL_STRING);
        }
    }
}
