package order.orderkafka.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerEntity {

    @Id
    @Column(name = "customer_id")
    private long id;

    @Column(name = "name")
    private String customerName;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;
}
