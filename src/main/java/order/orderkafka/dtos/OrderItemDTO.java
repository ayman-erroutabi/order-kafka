package ma.therightman.orderkafka.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemDTO {

    private long id;

    private ProductDTO productEntity;

    private int quantity;
}
