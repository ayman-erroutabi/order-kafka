package ma.therightman.orderkafka.dtos.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ma.therightman.orderkafka.dtos.OrderDTO;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;


@Slf4j
public class OrderSerializer implements Serializer<OrderDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, OrderDTO data) {
        try {
            if (data == null){
                log.error("Null received at serializing");
                throw new SerializationException("Error when serializing OrderDTO, value is null");
            }
            log.info("Serializing..." + data.toString());
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error when serializing OrderDTO to byte[]");
        }
    }
}