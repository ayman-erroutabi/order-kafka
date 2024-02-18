package ma.therightman.orderkafka.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {

    private long id;

    private String customerName;

    private String email;

    private String address;

}
