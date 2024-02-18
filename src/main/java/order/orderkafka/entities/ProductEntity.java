package order.orderkafka.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductEntity {

    @Id
    @Column(name = "product_id")
    private long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private float price;

    @Column(name = "available_qty")
    private int availableQuantity;
}
