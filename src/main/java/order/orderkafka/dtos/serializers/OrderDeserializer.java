package order.orderkafka.dtos.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import order.orderkafka.dtos.OrderDTO;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

@Slf4j
public class OrderDeserializer implements Deserializer<OrderDTO> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public OrderDTO deserialize(String topic, byte[] data) {
        try {
            if (data == null){
                log.error("Null received at deserializing");
                return null;
            }
            log.info("Deserializing..." );
            return objectMapper.readValue(new String(data, StandardCharsets.UTF_8), OrderDTO.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }
}