package order.orderkafka.services.impl;

import order.orderkafka.entities.OrderItemEntity;
import order.orderkafka.repositories.OrderItemRepository;
import order.orderkafka.services.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    OrderItemServiceImpl(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItemEntity save(OrderItemEntity orderItemEntity ){
        return this.orderItemRepository.save(orderItemEntity);
    }

}
