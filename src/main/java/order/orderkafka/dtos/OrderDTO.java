package ma.therightman.orderkafka.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDTO {

    private long id;

    private CustomerDTO customer;

    private List<OrderItemDTO> orderItemEntities;

    private Date dateCreated;

    private String errorMessage;
}

