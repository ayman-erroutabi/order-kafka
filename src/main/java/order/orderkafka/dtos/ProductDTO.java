package ma.therightman.orderkafka.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {

    private long id;

    private String productName;

    private float price;

    private int availableQuantity;
}
