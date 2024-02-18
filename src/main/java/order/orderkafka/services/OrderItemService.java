package order.orderkafka.services;

import order.orderkafka.entities.OrderItemEntity;

public interface OrderItemService {

    OrderItemEntity save(OrderItemEntity orderItemEntity);
}
