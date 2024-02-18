package ma.therightman.orderkafka.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_item")
@SequenceGenerator(name = "SEQ_ORDER_ITEM_GENERATOR", sequenceName = "SEQ_ORDER_ITEM", allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderItemEntity {

    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_ORDER_ITEM_GENERATOR")
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    @Column(name = "quantity")
    private int quantity;

    public OrderItemEntity(OrderEntity orderEntity, ProductEntity product, int orderQuantity) {
        this.orderEntity = orderEntity;
        this.productEntity = product;
        this.quantity = orderQuantity;
    }
}
