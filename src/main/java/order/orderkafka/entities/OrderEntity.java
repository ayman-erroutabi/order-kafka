package order.orderkafka.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@SequenceGenerator(name = "SEQ_ORDER_GENERATOR", sequenceName = "SEQ_ORDER", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderEntity {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_ORDER_GENERATOR")
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.ALL)
    private List<OrderItemEntity> orderItemEntities;

    @Column(name = "date_created")
    private Date dateCreated;

}
