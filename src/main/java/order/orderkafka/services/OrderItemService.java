package ma.therightman.orderkafka.services;

import ma.therightman.orderkafka.entities.OrderItemEntity;

public interface OrderItemService {

    OrderItemEntity save(OrderItemEntity orderItemEntity);
}
